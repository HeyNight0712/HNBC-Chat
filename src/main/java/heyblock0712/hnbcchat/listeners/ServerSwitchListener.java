package heyblock0712.hnbcchat.listeners;

import heyblock0712.hnbcchat.HNBC_Chat;
import heyblock0712.hnbcchat.cord.ConfigManager;
import heyblock0712.hnbcchat.utils.MessageManager;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerSwitchListener implements Listener {
    private ProxiedPlayer player;
    private ServerInfo fromServer;
    private ServerInfo toServer;

    @EventHandler
    public void onServerSwitch(ServerSwitchEvent event) {
        if (!ConfigManager.getConfig().getBoolean("PlayerSwitchesServer.Enable", true)) return; // 是否啟用
        player = event.getPlayer();
        fromServer = event.getFrom();
        toServer = player.getServer().getInfo();

        if (fromServer == null) return;
        TextComponent formatMessage;

        formatMessage = new TextComponent(player.getName() + " " + fromServer.getName() + " 移動到 " + toServer.getName());
        MessageManager.channelMessage(formatMessage);
        HNBC_Chat.getIntention().getLogger().info(player.getName() + " " + fromServer.getName() + " 移動到 " + toServer.getName());
    }
}
