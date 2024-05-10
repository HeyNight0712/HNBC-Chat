package heyblock0712.hnbcchat.listeners.discord;

import heyblock0712.hnbcchat.cord.ConfigManager;
import heyblock0712.hnbcchat.cord.DiscordManager;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerSwitchListener implements Listener {
    private static String channelID;

    public ServerSwitchListener(String channelID) {
        ServerSwitchListener.channelID = channelID;
    }

    @EventHandler
    public void onServerSwitch(ServerSwitchEvent event) {
        if (!ConfigManager.getConfig().getBoolean("Discord.PlayerDisconnect.Enable", true)) return; // 是否啟用

        ProxiedPlayer player = event.getPlayer();
        ServerInfo fromServer = event.getFrom();
        ServerInfo toServer = player.getServer().getInfo();

        if (fromServer == null) return; // 過濾 登入玩家

        String message = player.getName() + "從 " + fromServer.getName() + " 移動至 " + toServer.getName();
        DiscordManager.sendMessage(channelID, message);
    }
}
