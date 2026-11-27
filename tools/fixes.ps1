# Hand-fix overlay for decompiled sources.
#
#   fixes.ps1 save   - record every file in src\main\java that differs from the fresh decompiler output in
#                      work\named-src-pristine (run deobf.ps1 first so it is current)
#   fixes.ps1 apply  - copy recorded fixes into a fresh decompile (deobf.ps1 does this automatically)
#
# Each fix is stored twice under fixes\: <file>.orig (what the decompiler produced) and <file> (the fixed
# version). A fix is applied only if the fresh output still equals .orig, so a fix never lands on code that
# changed underneath it (e.g. after renaming in mappings\); those files are reported for a manual look.
param([Parameter(Mandatory)][ValidateSet('save', 'apply')][string]$Mode, [string]$Target)
$ErrorActionPreference = 'Stop'
$proj = Split-Path -Parent $PSScriptRoot
$fresh = Join-Path $proj 'work\named-src-pristine'   # decompiler output before any hand fix
$src = Join-Path $proj 'src\main\java'
$store = Join-Path $proj 'fixes'
$utf8 = New-Object System.Text.UTF8Encoding($false)

if ($Mode -eq 'save') {
    if (Test-Path $store) { Remove-Item -Recurse -Force $store }
    $n = 0
    Get-ChildItem $src -Recurse -Filter *.java | ForEach-Object {
        $rel = $_.FullName.Substring($src.Length + 1)
        $orig = Join-Path $fresh $rel
        $mine = [IO.File]::ReadAllText($_.FullName)
        $theirs = if (Test-Path $orig) { [IO.File]::ReadAllText($orig) } else { $null }
        if ($mine -ne $theirs) {
            $dst = Join-Path $store $rel
            New-Item -ItemType Directory -Force (Split-Path $dst) | Out-Null
            [IO.File]::WriteAllText($dst, $mine, $utf8)
            if ($null -ne $theirs) { [IO.File]::WriteAllText("$dst.orig", $theirs, $utf8) }
            $n++
        }
    }
    Write-Host "Saved $n hand-fixed files to $store"
} else {
    if (-not $Target) { $Target = Join-Path $proj 'work\named-src' }
    if (-not (Test-Path $store)) { return }
    . (Join-Path $PSScriptRoot 'find-jdk.ps1')
    $jdk = Find-Jdk
    if (-not $jdk) { throw "Need a JDK 21+ (with javac): set JAVA_HOME" }
    $java = Join-Path $jdk 'bin\java.exe'
    $ok = 0; $merged = 0; $stale = @()
    Get-ChildItem $store -Recurse -Filter *.java | ForEach-Object {
        $rel = $_.FullName.Substring($store.Length + 1)
        $out = Join-Path $Target $rel
        $origFile = "$($_.FullName).orig"
        $current = if (Test-Path $out) { [IO.File]::ReadAllText($out) } else { $null }
        $orig = if (Test-Path $origFile) { [IO.File]::ReadAllText($origFile) } else { $null }
        $fixedText = [IO.File]::ReadAllText($_.FullName)
        if ($current -eq $fixedText) { $ok++ }   # already applied
        elseif ($current -eq $orig) { [IO.File]::WriteAllText($out, $fixedText, $utf8); $ok++ }
        elseif ($null -ne $current -and $null -ne $orig) {
            # the output changed around the fix: replay the fix as a diff
            $tmp = "$out.merged"
            & $java (Join-Path $PSScriptRoot 'MergeFix.java') $origFile $_.FullName $out $tmp | Out-Null
            if ($LASTEXITCODE -eq 0) { Move-Item -Force $tmp $out; $merged++ } else { $stale += $rel }
        }
        else { $stale += $rel }
    }
    Write-Host "Applied $ok hand fixes as-is, merged $merged into changed output"
    if ($stale) { Write-Host "Not applied (decompiler output changed, re-fix by hand; old fix kept in fixes\):"; $stale | ForEach-Object { Write-Host "  $_" } }
}
