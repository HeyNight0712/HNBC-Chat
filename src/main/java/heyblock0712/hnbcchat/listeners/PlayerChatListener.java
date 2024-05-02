package heyblock0712.hnbcchat.listeners;

import heyblock0712.hnbcchat.HNBC_Chat;
import heyblock0712.hnbcchat.cord.ConfigManager;
import heyblock0712.hnbcchat.cord.DiscordManager;
import heyblock0712.hnbcchat.utils.MessageManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerChatListener implements Listener {
    private ProxiedPlayer player;
    private String serverName;
    private String playerMessage;

    @EventHandler
    public void onPlayerChat(ChatEvent event) {
        if (event.getSender() instanceof ProxiedPlayer) {
            player = (ProxiedPlayer) event.getSender();
            serverName = player.getServer().getInfo().getName();
            playerMessage = event.getMessage();

            if (playerMessage.startsWith("/")) return; // 排除指令
            TextComponent formatMessage;
            formatMessage = formatMessage(ConfigManager.getConfig().getString("PlayerChat.Message"));

            // MC
            if (!ConfigManager.getConfig().getBoolean("PlayerChat.Enable", true)) {
                MessageManager.channelMessage(serverName, formatMessage);
                HNBC_Chat.getIntention().getLogger().info(formatMessage.getText());
            }

            // Discord
            if (!ConfigManager.getConfig().getBoolean("Discord.PlayerChat.Enable", true)) {
                String channelID = ConfigManager.getConfig().getString("Discord.Channels.Global");
                String discordMessage = ConfigManager.getConfig().getString("Discord.MinecraftToDiscord");

                formatMessage = formatMessage(discordMessage);
                DiscordManager.sendMessage(channelID, formatMessage.getText());
            }
        }
    }

    private TextComponent formatMessage(String configMessage) {
        configMessage = configMessage
                .replace("%server%", serverName)
                .replace("%player_name%", player.getName())
                .replace("%player_message%", playerMessage);
        configMessage = ChatColor.translateAlternateColorCodes('&', configMessage);
        return new TextComponent(configMessage);
    }
}
