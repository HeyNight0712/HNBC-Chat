package heyblock0712.hnbcchat.utils;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MessageManager {
    /**
     * 過濾 serverName 伺服器訊息
     * @param serverName 伺服器名稱
     * @param message 發送訊息
     */
    public static void channelMessage(String serverName, TextComponent message) {
        for (ProxiedPlayer recipient : ProxyServer.getInstance().getPlayers()) {
            if (!recipient.getServer().getInfo().getName().equals(serverName)) {
                recipient.sendMessage(message);
            }
        }
    }

    /**
     * 發送 訊息到所有玩家
     * @param message 發送訊息
     */
    public static void channelMessage(TextComponent message) {
        for (ProxiedPlayer recipient : ProxyServer.getInstance().getPlayers()) {
            recipient.sendMessage(message);
        }
    }
}
