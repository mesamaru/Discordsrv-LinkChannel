package dev.neonm.linkchannel;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordGuildMessageReceivedEvent;
import github.scarsz.discordsrv.objects.managers.AccountLinkManager;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public final class LinkChannelListener {

    private final DiscordSRVLinkChannelPlugin plugin;

    public LinkChannelListener(DiscordSRVLinkChannelPlugin plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void onDiscordGuildMessageReceived(DiscordGuildMessageReceivedEvent event) {
        if (!isAllowedLinkChannel(event.getGuild().getId(), event.getChannel().getId())) {
            return;
        }

        if (event.getAuthor().isBot()) {
            return;
        }

        String content = event.getMessage().getContentRaw();
        if (content == null) {
            return;
        }

        String normalized = content.trim();
        if (normalized.isEmpty()) {
            return;
        }

        if (!isLinkCode(normalized)) {
            return;
        }

        AccountLinkManager accountLinkManager = DiscordSRV.getPlugin().getAccountLinkManager();
        if (accountLinkManager == null) {
            plugin.getLogger().warning("DiscordSRV AccountLinkManager is null. Skipping link request.");
            return;
        }

        try {
            String reply = accountLinkManager.process(normalized, event.getAuthor().getId());
            if (reply != null && !reply.isBlank()) {
                event.getChannel().sendMessage(reply).queue(sent -> {
                    if (plugin.getConfig().getBoolean("RemoveMessages", true)) {
                        deleteMessageLater(sent, plugin.getConfig().getLong("DeleteDelaySeconds", 10L));
                    }
                });
            }

            if (plugin.getConfig().getBoolean("RemoveMessages", true)) {
                deleteMessageLater(event.getMessage(), plugin.getConfig().getLong("DeleteDelaySeconds", 10L));
            }
        } catch (Throwable t) {
            plugin.getLogger().warning("Failed to process Discord link request: " + t.getMessage());
        }
    }

    private boolean isAllowedLinkChannel(String guildId, String channelId) {
        List<?> targets = plugin.getConfig().getList("LinkingTargets");
        if (targets != null && !targets.isEmpty()) {
            for (Object target : targets) {
                if (!(target instanceof Map<?, ?> map)) {
                    continue;
                }

                Object guildValue = map.get("GuildId");
                Object channelValue = map.get("ChannelId");
                String configuredGuildId = guildValue == null ? "" : String.valueOf(guildValue).trim();
                String configuredChannelId = channelValue == null ? "" : String.valueOf(channelValue).trim();
                if (configuredGuildId.isEmpty() || configuredChannelId.isEmpty()) {
                    continue;
                }

                if (configuredGuildId.equals(guildId) && configuredChannelId.equals(channelId)) {
                    return true;
                }
            }
            return false;
        }

        // Backward compatibility: allow legacy single-channel config.
        String legacyChannelId = plugin.getConfig().getString("LinkingDiscordChannel", "").trim();
        return !legacyChannelId.isEmpty() && legacyChannelId.equals(channelId);
    }

    private boolean isLinkCode(String value) {
        String regex = plugin.getConfig().getString("NumericCodeRegex", "^[0-9]+$");
        try {
            return Pattern.compile(regex).matcher(value).matches();
        } catch (PatternSyntaxException ex) {
            plugin.getLogger().warning("Invalid NumericCodeRegex in config, using fallback ^[0-9]+$");
            return value.matches("^[0-9]+$");
        }
    }

    private void deleteMessageLater(Object message, long delaySeconds) {
        long safeDelay = Math.max(0L, delaySeconds);
        try {
            Object deleteAction = call(message, "delete");
            call(deleteAction, "queueAfter", safeDelay, TimeUnit.SECONDS);
        } catch (ReflectiveOperationException ignored) {
            // Ignore failures such as already deleted, missing permissions, or API differences.
        }
    }

    private Object call(Object target, String methodName, Object... args) throws ReflectiveOperationException {
        Method[] methods = target.getClass().getMethods();
        for (Method method : methods) {
            if (!method.getName().equals(methodName)) {
                continue;
            }
            if (method.getParameterCount() != args.length) {
                continue;
            }
            if (isCompatible(method.getParameterTypes(), args)) {
                return method.invoke(target, args);
            }
        }
        throw new NoSuchMethodException(target.getClass().getName() + "." + methodName);
    }

    private boolean isCompatible(Class<?>[] paramTypes, Object[] args) {
        for (int i = 0; i < paramTypes.length; i++) {
            Object arg = args[i];
            Class<?> type = wrapPrimitive(paramTypes[i]);

            if (arg == null) {
                if (type.isPrimitive()) {
                    return false;
                }
                continue;
            }

            if (!type.isAssignableFrom(wrapPrimitive(arg.getClass()))) {
                return false;
            }
        }
        return true;
    }

    private Class<?> wrapPrimitive(Class<?> type) {
        if (!type.isPrimitive()) {
            return type;
        }
        if (type == int.class) return Integer.class;
        if (type == long.class) return Long.class;
        if (type == boolean.class) return Boolean.class;
        if (type == double.class) return Double.class;
        if (type == float.class) return Float.class;
        if (type == short.class) return Short.class;
        if (type == byte.class) return Byte.class;
        if (type == char.class) return Character.class;
        return type;
    }
}
