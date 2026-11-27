# libs

Nothing in here is committed; both jars are made on your machine:

| File | Made by | What it is |
|---|---|---|
| `mc-named.jar` | `powershell -ExecutionPolicy Bypass -File tools\deobf.ps1 -NoSource` | your game folder's `rt\libs\mc.jar` (Minecraft 1.8.9 + Onyx hooks) with its hook calls retargeted to the deobfuscated names |
| `onyx-deobf.jar` | `gradlew.bat build` | the client compiled from `src/main/java`; `run.bat` starts it |
