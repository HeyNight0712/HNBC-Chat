package heyblock0712.hnbcchat.listeners;

import heyblock0712.hnbcchat.HNBC_Chat;
import heyblock0712.hnbcchat.cord.ConfigManager;
import heyblock0712.hnbcchat.cord.DiscordManager;
import heyblock0712.hnbcchat.utils.MessageManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerSwitchesServerListener implements Listener {
    private ProxiedPlayer player;
    private ServerInfo fromServer;
    private ServerInfo toServer;

    @EventHandler
    public void onPlayerSwitchesServer(ServerSwitchEvent event) {
        player = event.getPlayer();
        fromServer = event.getFrom();
        toServer = player.getServer().getInfo();

        if (fromServer == null) return;
        TextComponent formatMessage;

        // MC
        if (ConfigManager.getConfig().getBoolean("PlayerSwitchesServer.Enable", true)) {
            formatMessage = new TextComponent(player.getName() + " " + fromServer.getName() + " 移動到 " + toServer.getName());
            MessageManager.channelMessage(formatMessage);
        }

        // Log
        if (ConfigManager.getConfig().getBoolean("PlayerDisconnect.Log", true)) {
            HNBC_Chat.getIntention().getLogger().info(player.getName() + " " + fromServer.getName() + " 移動到 " + toServer.getName());
        }

        // Discord
        if (ConfigManager.getConfig().getBoolean("Discord.PlayerDisconnect.Enable", true)) {
            String channeID = ConfigManager.getConfig().getString("Discord.Channels.Global");
            if (channeID == null) return; // 檢查頻道

            String PlayerSwitchesServer = ConfigManager.getConfig().getString("Discord.PlayerSwitchesServer.Message");
            if (PlayerSwitchesServer == null) return; // 檢查字串 如果沒有是同關閉

            formatMessage = formatMessage(PlayerSwitchesServer);
            DiscordManager.sendMessage(channeID, formatMessage.getText());
        }
    }

    private TextComponent formatMessage(String configMessage) {
        configMessage = configMessage
                .replace("%player_name%", player.getName())
                .replace("%from_server%", fromServer.getName())
                .replace("%to_server%", toServer.getName());
        configMessage = ChatColor.translateAlternateColorCodes('&', configMessage);
        return new TextComponent(configMessage);
    }
}
