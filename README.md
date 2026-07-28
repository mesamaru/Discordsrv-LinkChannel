# DiscordSRV-LinkChannel-Latest

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

---

Paperサーバー向けの、DiscordSRVアカウント連携用アドオンです。

## 機能
- アカウント連携コード専用のDiscordチャンネルを利用
- 数字コードのみ受け付けるフィルタ
- メッセージ自動削除（任意）
- リロードコマンド: `lcreload`

## 互換性
- Java 21
- DiscordSRV 1.30.5
- Paper API 1.21.x をビルド対象

## ビルド
```bash
mvn -DskipTests clean package
```

出力Jar:
- `target/discordsrv-linkchannel-latest-1.0.0.jar`

## 設定
初回起動後に以下を編集してください。
- `plugins/DiscordSRV-LinkChannel-Latest/config.yml`
