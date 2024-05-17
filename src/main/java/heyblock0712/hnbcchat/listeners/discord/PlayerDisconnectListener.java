package heyblock0712.hnbcchat.listeners.discord;

import heyblock0712.hnbcchat.cord.ConfigManager;
import heyblock0712.hnbcchat.cord.DiscordManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerDisconnectListener implements Listener {
    private static String channelID;

    public PlayerDisconnectListener(String channelID) {
        PlayerDisconnectListener.channelID = channelID;
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        if (!ConfigManager.getConfig().getBoolean("Discord.PlayerDisconnect.Enable", true)) return; // 是否啟用

        ProxiedPlayer player = event.getPlayer();

        String message = player.getName() + " 離開了伺服器";
        DiscordManager.sendMessage(channelID, message);
        DiscordManager.channelEdit(channelID, (ProxyServer.getInstance().getOnlineCount() - 1) + " 人在伺服器中");
    }
}
