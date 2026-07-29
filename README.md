# DiscordSRV-LinkChannel

DiscordSRV account linking addon for Paper servers.

## Features
- Multiple Discord guild/channel pairs for account linking codes
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
- `target/discordsrv-linkchannel-1.1.0.jar`

## Config
Edit `plugins/DiscordSRV-LinkChannel/config.yml` after first startup.

Example (multiple guild/channel pairs):
```yml
LinkingTargets:
	- GuildId: "111111111111111111"
		ChannelId: "222222222222222222"
	- GuildId: "333333333333333333"
		ChannelId: "444444444444444444"
```

---

Paperサーバー向けの、DiscordSRVアカウント連携用アドオンです。

## 機能
- 複数のDiscordサーバー/チャンネルの組み合わせで連携コード受付
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
- `target/discordsrv-linkchannel-1.1.0.jar`

## 設定
初回起動後に以下を編集してください。
- `plugins/DiscordSRV-LinkChannel/config.yml`

設定例（複数サーバー/チャンネル）:
```yml
LinkingTargets:
	- GuildId: "111111111111111111"
		ChannelId: "222222222222222222"
	- GuildId: "333333333333333333"
		ChannelId: "444444444444444444"
```
