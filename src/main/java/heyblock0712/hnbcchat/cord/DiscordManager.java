package heyblock0712.hnbcchat.cord;

import heyblock0712.hnbcchat.HNBC_Chat;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.md_5.bungee.api.plugin.Plugin;

public class DiscordManager {
    private static JDA jda;
    private static Plugin plugin = HNBC_Chat.getIntention();

    public static void startBot() {
        String TOKEN = ConfigManager.getConfig().getString("BotToken");
        if (TOKEN == null || TOKEN.isEmpty()) {
            plugin.getLogger().info("未使用 Discord Token 默認禁用");
            return;
        }

        try {
            jda = JDABuilder.createDefault(TOKEN)
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
        if (textChannel == null) return;
        textChannel.sendMessage(message).queue();
    }
}
