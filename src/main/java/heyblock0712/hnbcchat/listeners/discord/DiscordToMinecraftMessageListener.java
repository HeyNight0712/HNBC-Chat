package heyblock0712.hnbcchat.listeners.discord;

import heyblock0712.hnbcchat.HNBC_Chat;
import heyblock0712.hnbcchat.cord.DiscordManager;
import heyblock0712.hnbcchat.utils.MessageManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class DiscordToMinecraftMessageListener extends ListenerAdapter {
    private static String channelID;

    public DiscordToMinecraftMessageListener(String channelID) {
        DiscordToMinecraftMessageListener.channelID = channelID;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // 過濾 非頻道訊息
        if (!event.isFromGuild() || !event.isFromType(ChannelType.TEXT) || !event.getChannel().getId().equals(channelID)) {
            return;
        }
        if (event.getAuthor().isBot()) return; // 過濾 Bot 訊息

        String message = event.getMessage().getContentDisplay();
        Member member = event.getMember();
        String username = member != null ? member.getEffectiveName() : event.getAuthor().getName();

        if (message.startsWith("玩家列表")) {
            playerList();
            return;
        }

        // get RoleColor
        String highestRoleColor = "null";
        if (member != null) {
            List<Role> roles = member.getRoles();
            if (!roles.isEmpty()) {
                Role topRole = roles.get(0);
                for (Role role : roles) {
                    if (role.getPosition() > topRole.getPosition()) {
                        topRole = role;
                    }
                }
                highestRoleColor = roleToColor(topRole.getColor());
            }
        }

        // textComponent
        TextComponent textComponent = new TextComponent("[");
        textComponent.setColor(ChatColor.WHITE);

        TextComponent discordComponent = new TextComponent("Discord");
        discordComponent.setColor(ChatColor.of(highestRoleColor));
        textComponent.addExtra(discordComponent);

        TextComponent messageComponent = new TextComponent("] " + username + " » " + message);
        messageComponent.setColor(ChatColor.WHITE);
        textComponent.addExtra(messageComponent);

        // Message
        MessageManager.channelMessage(textComponent);
        HNBC_Chat.getIntention().getLogger().info(textComponent.toPlainText());
    }

    /**
     * 將 Role 轉換成 Minecraft 顏色
     * @param color Role 顏色
     * @return Minecraft 顏色
     */
    private String roleToColor(Color color) {
        if (color == null) return "#FFFFFF"; // 如果颜色为空，返回白色
        return String.format("#%06X", (color.getRGB() & 0x00FFFFFF));
    }

    private void playerList() {
        // get all Player Count
        int globalPlayerCount = ProxyServer.getInstance().getOnlineCount();

        // Embed
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.decode("#00FFD5"));
        embed.setTitle("玩家列表");
        embed.setDescription("線上總玩家: " + globalPlayerCount);

        // get server Player Count
        Map<String, ServerInfo> servers = ProxyServer.getInstance().getServers();
        for (Map.Entry<String, ServerInfo> entny : servers.entrySet()) {
            ServerInfo server = entny.getValue();
            int playersOnServer = server.getPlayers().size();

            StringBuilder playerName = new StringBuilder();
            for (ProxiedPlayer player: server.getPlayers()) {
                playerName.append("`").append(player.getName()).append("`").append("\n");
            }

            embed.addField("分流 " + entny.getKey() + " 上的玩家數: " + playersOnServer, playerName.toString(), false);
        }

        DiscordManager.sendMessage(channelID, embed);
    }
}
