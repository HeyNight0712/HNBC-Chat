package heyblock0712.hnbcchat;

import heyblock0712.hnbcchat.cord.ConfigManager;
import heyblock0712.hnbcchat.cord.DiscordManager;
import heyblock0712.hnbcchat.listeners.PlayerChatListener;
import heyblock0712.hnbcchat.listeners.PlayerDisconnectListener;
import heyblock0712.hnbcchat.listeners.PlayerLoginListener;
import heyblock0712.hnbcchat.listeners.PlayerSwitchesServerListener;
import heyblock0712.hnbcchat.listeners.discord.DiscordToMinecraftMessageListener;
import net.dv8tion.jda.api.JDA;
import net.md_5.bungee.api.plugin.Plugin;

public final class HNBC_Chat extends Plugin {
    private static Plugin intention;

    @Override
    public void onEnable() {
        intention = this;
        ConfigManager.loadConfig();

        DiscordManager.startBot();

        getProxy().registerChannel("BungeeCord");

        getProxy().getPluginManager().registerListener(this, new PlayerChatListener());
        getProxy().getPluginManager().registerListener(this, new PlayerLoginListener());
        getProxy().getPluginManager().registerListener(this, new PlayerDisconnectListener());
        getProxy().getPluginManager().registerListener(this, new PlayerSwitchesServerListener());

        // Discord Listener
        DiscordToMinecraftMessageListener discordToMinecraftMessageListener = new DiscordToMinecraftMessageListener(ConfigManager.getConfig().getString("Discord.Channels.Global"));
        JDA jda = DiscordManager.getJDA();
        if (jda != null) {
            jda.addEventListener(discordToMinecraftMessageListener);
        }
    }

    @Override
    public void onDisable() {
        intention = null;
        getProxy().unregisterChannel("BungeeCord");
    }

    public static Plugin getIntention() {return intention;}
}
