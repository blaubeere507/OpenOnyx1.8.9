# Onyx deobfuscation pipeline. Re-run after editing mappings/classes.txt or mappings/members.txt.
#
#   powershell -ExecutionPolicy Bypass -File tools\deobf.ps1            (from the onyx-deobf folder)
#   powershell -ExecutionPolicy Bypass -File tools\deobf.ps1 -NoSource  (mappings + jars only)
#
# Steps: Clean (decrypt strings, fold constants, strip junk) -> Mapper (rename + remap onyx.jar and
# mc.jar) -> Hooks (list Minecraft->client hook sites) -> Vineflower (decompile into src/main/java).
param(
    [string]$Jdk,            # JDK 21+ folder; default: JAVA_HOME, then javac on PATH, then common install folders
    [switch]$NoSource
)
$ErrorActionPreference = 'Stop'
$proj = Split-Path -Parent $PSScriptRoot          # onyx-deobf
$game = Split-Path -Parent $proj                  # folder holding onyx.jar, rt\libs
. (Join-Path $PSScriptRoot 'find-jdk.ps1')
if (-not $Jdk) { $Jdk = Find-Jdk }
if (-not $Jdk -or -not (Test-Path (Join-Path $Jdk 'bin\javac.exe'))) { throw "Need a full JDK 21+ (with javac): set JAVA_HOME or pass -Jdk <path>" }
$java = Join-Path $Jdk 'bin\java.exe'
foreach ($need in 'onyx.jar', 'rt\libs\mc.jar') {
    if (-not (Test-Path (Join-Path $game $need))) { throw "$need not found in $game - this folder has to sit inside the Onyx game folder" }
}

# ASM + Vineflower come from the Gradle cache; the first run lets Gradle download them
$cache = Join-Path $env:USERPROFILE '.gradle\caches\modules-2\files-2.1'
$script:resolved = $false
function Find-Jar([string]$group, [string]$name, [string]$version) {
    $j = Get-ChildItem (Join-Path $cache "$group\$name\$version") -Recurse -Filter "$name-$version.jar" -ErrorAction SilentlyContinue | Select-Object -First 1
    if (-not $j -and -not $script:resolved) {
        Write-Host "Downloading pipeline tools (ASM, Vineflower) through Gradle..."
        $env:JAVA_HOME = $Jdk
        Push-Location $proj; & .\gradlew.bat resolveDeobfTools -q; Pop-Location
        $script:resolved = $true
        $j = Get-ChildItem (Join-Path $cache "$group\$name\$version") -Recurse -Filter "$name-$version.jar" -ErrorAction SilentlyContinue | Select-Object -First 1
    }
    if (-not $j) { throw "Missing $group`:$name`:$version in the Gradle cache" }
    $j.FullName
}
$asm = @('asm', 'asm-tree', 'asm-commons', 'asm-analysis', 'asm-util') | ForEach-Object { Find-Jar 'org.ow2.asm' $_ '9.9' }
$asmCp = $asm -join ';'
$vineflower = Find-Jar 'org.vineflower' 'vineflower' '1.11.1'

$work = Join-Path $proj 'work'
$mapped = Join-Path $work 'mapped'
New-Item -ItemType Directory -Force $work, $mapped, (Join-Path $proj 'libs') | Out-Null

Write-Host '== 1/4 Clean'
& $java -cp $asmCp (Join-Path $PSScriptRoot 'Clean.java') (Join-Path $game 'onyx.jar') (Join-Path $work 'onyx-clean.jar') (Join-Path $work 'dump.txt') (Join-Path $game 'rt\libs')
if ($LASTEXITCODE) { throw 'Clean failed' }

Write-Host '== 2/4 Mapper'
& $java -cp $asmCp (Join-Path $PSScriptRoot 'Mapper.java') (Join-Path $work 'onyx-clean.jar') (Join-Path $game 'rt\libs') (Join-Path $game 'rt\libs\mc.jar') `
    (Join-Path $proj 'mappings\classes.txt') (Join-Path $proj 'mappings\members.txt') $mapped
if ($LASTEXITCODE) { throw 'Mapper failed' }
Copy-Item (Join-Path $mapped 'onyx.tiny'), (Join-Path $mapped 'classes.csv') (Join-Path $proj 'mappings') -Force
Copy-Item (Join-Path $mapped 'mc-named.jar') (Join-Path $proj 'libs\mc-named.jar') -Force

Write-Host '== 3/4 Hooks'
& $java -cp $asmCp (Join-Path $PSScriptRoot 'Hooks.java') (Join-Path $mapped 'mc-named.jar') 'client/onyx/' (Join-Path $proj 'docs\hooks.txt')
if ($LASTEXITCODE) { throw 'Hooks failed' }

if ($NoSource) { return }
Write-Host '== 4/4 Vineflower'
$out = Join-Path $work 'named-src'
if (Test-Path $out) { Remove-Item -Recurse -Force $out }
New-Item -ItemType Directory $out | Out-Null
$libs = @(Get-ChildItem (Join-Path $game 'rt\libs\*.jar') | Where-Object Name -ne 'mc.jar' | ForEach-Object { "-e=$($_.FullName)" }) + "-e=$(Join-Path $mapped 'mc-named.jar')"
# (no --variable-renaming: Vineflower's type-based renamers emit undefined names such as "stringx" on this input)
& $java -jar $vineflower --indent-string='    ' --log-level=warn `
    --decompile-generics=true --remove-synthetic=true --remove-bridge=true --ternary-constant-simplification=true @libs (Join-Path $mapped 'onyx-named.jar') $out
if ($LASTEXITCODE) { throw 'Vineflower failed' }
& $java (Join-Path $PSScriptRoot 'FixSource.java') $out
if ($LASTEXITCODE) { throw 'FixSource failed' }
# declare variables Vineflower assigns but never declares (types come from javac); two rounds catch follow-ups
$compileCp = (@(Get-ChildItem (Join-Path $game 'rt\libs\*.jar') | Where-Object Name -ne 'mc.jar' | ForEach-Object FullName) + (Join-Path $mapped 'mc-named.jar')) -join ';'
foreach ($round in 1..2) {
    & $java '-Duser.language=en' (Join-Path $PSScriptRoot 'DeclareMissing.java') $out $compileCp
    if ($LASTEXITCODE) { throw 'DeclareMissing failed' }
}

# keep a pristine copy for "fixes.ps1 save", then apply the recorded hand fixes to the output
$pristine = Join-Path $work 'named-src-pristine'
if (Test-Path $pristine) { Remove-Item -Recurse -Force $pristine }
Copy-Item $out $pristine -Recurse
& (Join-Path $PSScriptRoot 'fixes.ps1') apply -Target $out

$src = Join-Path $proj 'src\main\java'
if (Test-Path $src) {
    Write-Host "src\main\java exists - fresh output left in $out (copy over manually if you have no local edits)"
} else {
    New-Item -ItemType Directory -Force $src | Out-Null
    Copy-Item (Join-Path $out '*') $src -Recurse
    Write-Host "Decompiled sources written to $src"
}
