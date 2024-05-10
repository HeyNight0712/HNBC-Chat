package heyblock0712.hnbcchat.listeners;

import heyblock0712.hnbcchat.HNBC_Chat;
import heyblock0712.hnbcchat.cord.ConfigManager;
import heyblock0712.hnbcchat.utils.MessageManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(ChatEvent event) {
        if (event.getSender() instanceof ProxiedPlayer) {
            if (!ConfigManager.getConfig().getBoolean("PlayerChat.Enable", true)) return; // 是否啟用

            ProxiedPlayer player = (ProxiedPlayer) event.getSender();
            String serverName = player.getServer().getInfo().getName();
            String playerMessage = event.getMessage();

            if (playerMessage.startsWith("/")) return; // 排除指令

            TextComponent message = new TextComponent("<" + player.getName() + "> " + playerMessage);

            // Message
            MessageManager.channelMessage(serverName, message);
            HNBC_Chat.getIntention().getLogger().info(message.getText());
        }
    }

}
