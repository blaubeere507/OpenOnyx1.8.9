# onyx-deobf

Deobfuscated, buildable source of the Onyx client for Minecraft 1.8.9, plus the tools and mappings that produced it.

This repository contains **no Minecraft code and no game files**. The patched Minecraft jar the client needs
(`libs/mc-named.jar`) is generated on your machine from your own Onyx install.

## Getting started

You need:

* an Onyx game folder (the one with `onyx.jar`, `run.bat`, `jre\`, `rt\libs\`, `rt\natives\`, `assets\`)
* a JDK 21 or newer (found through `JAVA_HOME`, `javac` on `PATH`, or the usual install folders)
* Windows (the launcher and scripts are `.bat`/PowerShell)

Clone this repository **into** the game folder, so the layout is `<game folder>\onyx-deobf\`:

```powershell
git clone <this repo> onyx-deobf
```

```powershell
cd onyx-deobf
```

Generate `libs\mc-named.jar` from your game's `rt\libs\mc.jar` (the first run also downloads the pipeline tools through Gradle):

```powershell
powershell -ExecutionPolicy Bypass -File tools\deobf.ps1 -NoSource
```

Build the client (this also refreshes `libs\onyx-deobf.jar`):

```powershell
.\gradlew.bat build
```

Start the game from the build. Edit `MC_USERNAME` at the top of `run.bat` first if you like; `run.bat console` shows the log:

```powershell
.\run.bat
```

`.\gradlew.bat run` does the same from IntelliJ. To work on the code, open the `onyx-deobf` folder in IntelliJ as a Gradle project.

## What the obfuscator did (and what undoes it)

| Layer | Example | Undone by |
|---|---|---|
| Renaming | `client/onyx/r/t/d/Xa`, methods overloaded by return type only | `tools/Mapper.java` + `mappings/` |
| String encryption | 46 per-class XOR decryptors `static String L(String)` | `Clean.java`: verified-pure decryptors run in isolation, data-flow traced to every call (3,141/3,141) |
| Number obfuscation | `5 >> 1 ^ --3` | `Clean.java` constant folding |
| Junk calls, opaque predicates | `lqzxvptmnk.qpwoeirzn(-1394078338)`, `iconst_0; ifne` | removed / folded, dead code dropped |
| Stack shuffling | operands carried across loop back-edges, local-slot swaps | `Clean.java`: stack values spilled to locals at merge points |
| Moved `super()` | field stores before `super(...)` | `Clean.java` moves the call back to the start |
| Fake `synthetic` flags | helpers and enum constructors hidden from decompilers | `Mapper.java` keeps the flag only where javac would emit it |
| Stripped/shuffled metadata | `Record` attribute, bridge flags, inner-class names, field and enum order | `Mapper.java` rebuilds them; fields are declared in original initialisation order |

No control-flow flattening, class encryption or runtime class loading was found.

## Layout

```
src/main/java/client/onyx/...   decompiled, renamed source (432 files)
mappings/
  classes.txt                   hand-curated class names (edit this)
  members.txt                   hand-curated field/method names (edit this)
  onyx.tiny                     generated Tiny v2 mappings, obf -> named
  classes.csv                   generated obf,named class table
fixes/                          hand fixes for decompiler mistakes, re-applied on every regeneration
docs/hooks.txt                  every place the patched Minecraft calls into the client
tools/                          the pipeline (single-file Java programs + PowerShell)
libs/                           generated jars (not committed, see libs/README.md)
run.bat                         starts the game from the build
```

Packages: `module/<category>/` (modules), `setting/` (fluent setting API), `event/` (Orbit events posted from the patched Minecraft classes),
`rotation/` (silent rotations, aim models, move fix), `placement/` and `module/player/scaffold/` (block placement), `render/` (shaders, effects, fonts, HUD),
`ui/` (ClickGUI, account manager, Material-colour theme), `account/` (offline / token / Microsoft accounts), `proxy/`, `config/`, `command/`, `util/`.

## Status

* `gradlew build` compiles all 629 classes. The recompiled bytecode passes ASM's verifier, all 430 Minecraft→client hooks resolve (`tools/HookCheck.java`),
  and field initialisation order matches the original in every class (`tools/InitOrderCheck.java`). The game starts from the build.
* 37 files carry hand fixes, stored in `fixes/`. They are local: restored generic types, untangled local-slot reuse by the obfuscator, declarations moved into lambdas.
* Every class has a name; 146 are low-confidence guesses, marked `?` in `classes.txt`. Fields are named after setting labels
  (`new NumberSetting("Angle smooth", ...)` gives `angleSmooth`), enum constants or accessors, otherwise by type (`floatValue2`).
  About 2,700 methods still have placeholder `method_<n>` names.

## Improving names

1. Add a line to `mappings/members.txt`: `<obf owner> <obf name> <obf descriptor> <new name>` (look the obf names up in `mappings/onyx.tiny`).
   A method name applies to every override in the hierarchy.
2. Rename or move classes in `mappings/classes.txt`.
3. Regenerate. If `src/main/java` exists, the fresh output goes to `work/named-src` instead (hand fixes applied); delete `src/main/java` first to regenerate in place.

```powershell
powershell -ExecutionPolicy Bypass -File tools\deobf.ps1
```

Pipeline: `Clean.java` → `Mapper.java` → `Hooks.java` → Vineflower 1.11.1 → `FixSource.java` → `DeclareMissing.java` → `fixes.ps1 apply`.

**Hand fixes.** `fixes/` stores each fixed file with the decompiler output it was made against (`.orig`).
On regeneration a fix is copied when the output is unchanged, replayed as a diff (`MergeFix.java`) when only nearby code changed, and otherwise listed so you can redo it.
After fixing more files by hand, record them:

```powershell
powershell -ExecutionPolicy Bypass -File tools\fixes.ps1 save
```

## Notes

* The patched Minecraft classes call the client by name (`docs/hooks.txt`), which is why `libs/mc-named.jar` replaces `rt/libs/mc.jar`. The original game install is never modified.
* A scan of the client code found only official Microsoft/Xbox/Mojang endpoints, localhost and a Discord invite link: no webhooks or uploads.
  The only external processes are the now-playing helpers (a PowerShell script reading Windows media info; AppleScript on macOS).
