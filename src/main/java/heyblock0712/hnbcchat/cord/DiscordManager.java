package heyblock0712.hnbcchat.cord;

import heyblock0712.hnbcchat.HNBC_Chat;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.md_5.bungee.api.plugin.Plugin;

public class DiscordManager {
    private static JDA jda;
    private static final Plugin plugin = HNBC_Chat.getIntention();

    public static void startBot() {
        String TOKEN = ConfigManager.getConfig().getString("Discord.BotToken");
        if (TOKEN == null || TOKEN.isEmpty()) {
            plugin.getLogger().info("未使用 Discord Token 默認禁用");
            return;
        }

        try {
            jda = JDABuilder.createDefault(TOKEN)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_MESSAGE_TYPING, GatewayIntent.MESSAGE_CONTENT)
                    .build();
        } catch (Exception e) {
            plugin.getLogger().warning("你輸入的 TOKEN 錯誤 默認禁用: " + e.getMessage());
            return;
        }

        plugin.getLogger().info("已獲取 TOKEN 將啟用 Discord 相關功能");
    }

    public static void sendMessage(String channelId, String message) {
        if (jda == null) return;
        TextChannel textChannel = jda.getTextChannelById(channelId);
        if (textChannel == null || message == null) return;
        textChannel.sendMessage(message).queue();
    }

    public static void sendMessage(String channelId, EmbedBuilder embed) {
        if (jda == null) return;
        TextChannel textChannel = jda.getTextChannelById(channelId);
        if (textChannel == null || embed == null) return;
        textChannel.sendMessageEmbeds(embed.build()).queue();
    }

    public static void channelEdit(String channelId, String message) {
        TextChannel textChannel = jda.getTextChannelById(channelId);
        if (textChannel == null) return;
        textChannel.getManager().setTopic(message).queue();
    }

    public static JDA getJDA() {
        if (jda == null) return null;
        return jda;
    }
}
