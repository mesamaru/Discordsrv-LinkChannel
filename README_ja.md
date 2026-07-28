# DiscordSRV-LinkChannel-Latest

English version: [README.md](README.md)

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
