package heyblock0712.hnbcchat.listeners.discord;

import heyblock0712.hnbcchat.cord.DiscordManager;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class MinecraftToDiscordMessageListener implements Listener {
    private static String channelID;

    public MinecraftToDiscordMessageListener(String channelID) {
        MinecraftToDiscordMessageListener.channelID = channelID;
    }

    @EventHandler
    public void onPlayerChat(ChatEvent event) {
        if (event.getSender() instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) event.getSender();
            String playerMessage = event.getMessage();

            if (playerMessage.startsWith("/")) return; // 排除指令

            TextComponent textComponent = new TextComponent("<" + player.getName() + "> " + playerMessage);
            DiscordManager.sendMessage(channelID, textComponent.getText());
        }
    }
}
