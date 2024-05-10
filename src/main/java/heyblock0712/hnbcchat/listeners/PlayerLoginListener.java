package heyblock0712.hnbcchat.listeners;

import heyblock0712.hnbcchat.HNBC_Chat;
import heyblock0712.hnbcchat.cord.ConfigManager;
import heyblock0712.hnbcchat.cord.DiscordManager;
import heyblock0712.hnbcchat.utils.MessageManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PostLoginEvent event) {
        if (!ConfigManager.getConfig().getBoolean("PlayerLogin.Enable", true)) return; // 是否啟用

        ProxiedPlayer player = event.getPlayer();

        TextComponent formatMessage = new TextComponent(player.getName() + " 加入了伺服器");
        MessageManager.channelMessage(formatMessage);
        HNBC_Chat.getIntention().getLogger().info(player.getName() + " 加入了伺服器");
    }
}
