# DiscordSRV-LinkChannel-Latest

日本語版: [README_ja.md](README_ja.md)

DiscordSRV account linking addon for Paper servers.

## Features
- Dedicated Discord channel for account linking codes
- Numeric-code filtering
- Optional automatic message cleanup
- Reload command: `lcreload`

## Compatibility
- Java 21
- DiscordSRV 1.30.5
- Paper API 1.21.x build target

## Build
```bash
mvn -DskipTests clean package
```

Output jar:
- `target/discordsrv-linkchannel-latest-1.0.0.jar`

## Config
Edit `plugins/DiscordSRV-LinkChannel-Latest/config.yml` after first startup.
