package heyblock0712.hnbcchat.listeners.discord;

import heyblock0712.hnbcchat.HNBC_Chat;
import heyblock0712.hnbcchat.utils.MessageManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

import java.awt.*;
import java.util.List;

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

        // Role
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
        textComponent.addExtra("Discord");
        textComponent.addExtra("] " + username + " » " + message);
        textComponent.getExtra().get(0).setColor(ChatColor.of(highestRoleColor));

        // MC
        MessageManager.channelMessage(textComponent);

        // Info
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

    /**
     * 設置 channelID
     * @param channelID channelID
     */
    public static void setChannelID(String channelID) {
        DiscordToMinecraftMessageListener.channelID = channelID;
    }
}
