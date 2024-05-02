package heyblock0712.hnbcchat.listeners;

import heyblock0712.hnbcchat.HNBC_Chat;
import heyblock0712.hnbcchat.cord.ConfigManager;
import heyblock0712.hnbcchat.cord.DiscordManager;
import heyblock0712.hnbcchat.utils.MessageManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerDisconnectListener implements Listener {
    private ProxiedPlayer player;

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        player = event.getPlayer();
        TextComponent formatMessage;

        // MC
        if (ConfigManager.getConfig().getBoolean("PlayerDisconnect.Enable", true)) {
            formatMessage = new TextComponent(player.getName() + " 離開了伺服器");
            MessageManager.channelMessage(formatMessage);
        }

        // Log
        if (ConfigManager.getConfig().getBoolean("PlayerDisconnect.Log", true)) {
            HNBC_Chat.getIntention().getLogger().info(player.getName() + " 離開了伺服器");
        }

        // Discord
        if (ConfigManager.getConfig().getBoolean("Discord.PlayerDisconnect.Enable", true)) {
            String channeID = ConfigManager.getConfig().getString("Discord.Channels.Global");
            if (channeID == null) return; // 檢查頻道

            String PlayerDisconnect = ConfigManager.getConfig().getString("Discord.PlayerDisconnect.Message");
            if (PlayerDisconnect == null) return; // 檢查字串 如果沒有是同關閉

            formatMessage = formatMessage(PlayerDisconnect);
            DiscordManager.sendMessage(channeID, formatMessage.getText());
        }
    }

    private TextComponent formatMessage(String configMessage) {
        configMessage = configMessage
                .replace("%player_name%", player.getName());
        configMessage = ChatColor.translateAlternateColorCodes('&', configMessage);
        return new TextComponent(configMessage);
    }
}
