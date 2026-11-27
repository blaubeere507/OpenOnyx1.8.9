# Dot-source helper: Find-Jdk returns the folder of a JDK 21+ (one that has javac), or $null.
# Order: JAVA_HOME, javac on PATH, then the usual install folders (Adoptium, Oracle, Microsoft, Zulu, Gradle toolchains).
function Find-Jdk {
    $candidates = @()
    if ($env:JAVA_HOME) { $candidates += $env:JAVA_HOME }
    $javac = Get-Command javac.exe -ErrorAction SilentlyContinue
    if ($javac) { $candidates += (Split-Path -Parent (Split-Path -Parent $javac.Source)) }
    foreach ($root in "$env:ProgramFiles\Eclipse Adoptium", "$env:ProgramFiles\Java", "$env:ProgramFiles\Microsoft",
                      "$env:ProgramFiles\Zulu", "$env:USERPROFILE\.gradle\jdks", "$env:USERPROFILE\.jdks") {
        if (Test-Path $root) { $candidates += (Get-ChildItem $root -Directory | Sort-Object Name -Descending | ForEach-Object FullName) }
    }
    foreach ($c in $candidates) {
        if (-not (Test-Path (Join-Path $c 'bin\javac.exe'))) { continue }
        $release = Join-Path $c 'release'
        if (Test-Path $release) {
            $m = Select-String -Path $release -Pattern 'JAVA_VERSION="(\d+)' | Select-Object -First 1
            if ($m -and [int]$m.Matches[0].Groups[1].Value -lt 21) { continue }
        }
        return $c
    }
    return $null
}
