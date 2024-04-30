package heyblock0712.hnbcchat.listeners;

import heyblock0712.hnbcchat.HNBC_Chat;
import heyblock0712.hnbcchat.cord.ConfigManager;
import heyblock0712.hnbcchat.cord.DiscordManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(ChatEvent event) {
        if (event.getSender() instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) event.getSender();
            String serverName = player.getServer().getInfo().getName();
            String playerMessage = event.getMessage();

            if (playerMessage.startsWith("/")) return; // 排除指令
            String message = "[" + serverName + "] <" + player.getName() + "> " + playerMessage;

            HNBC_Chat.getIntention().getLogger().info(message);

            // 聊天互通
            TextComponent MinecraftToMinecraftMessage = formatMessage(serverName, player.getName(), playerMessage);

            for (ProxiedPlayer recipient : ProxyServer.getInstance().getPlayers()) {
                if (!recipient.getServer().getInfo().getName().equals(serverName)) {
                    recipient.sendMessage(MinecraftToMinecraftMessage);
                }
            }


            // Discord 支援
            String channeID = ConfigManager.getConfig().getString("Channels.global");
            if (channeID == null) return;
            DiscordManager.sendMessage(channeID, message);
        }

    }

    private TextComponent formatMessage(String serverName, String playerName, String playerMessage) {
        String message = ConfigManager.getConfig().getString("MinecraftToMinecraft");
        message = message
                .replace("%server%", serverName)
                .replace("%player_name%", playerName)
                .replace("%player_message%", playerMessage);
        message = ChatColor.translateAlternateColorCodes('&', message);
        return new TextComponent(message);
    }
}
