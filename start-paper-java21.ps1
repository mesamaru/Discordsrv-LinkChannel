$Java21 = "C:\Program Files\Java\jdk-21.0.12\bin\java.exe"
$ServerJar = "paper-1.26.2.jar"
$MinMem = "2G"
$MaxMem = "4G"

if (-not (Test-Path $Java21)) {
    Write-Error "Java 21 not found: $Java21"
    exit 1
}

if (-not (Test-Path $ServerJar)) {
    Write-Error "Server jar not found: $ServerJar"
    Write-Host "Place this script in your server folder, then set `$ServerJar to your actual jar file name."
    exit 1
}

while ($true) {
    & $Java21 "-Xms$MinMem" "-Xmx$MaxMem" -jar $ServerJar nogui
    Write-Host ""
    Write-Host "Server stopped. Restarting in 5 seconds... Press Ctrl+C to abort."
    Start-Sleep -Seconds 5
}
