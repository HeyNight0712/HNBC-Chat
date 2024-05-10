package heyblock0712.hnbcchat;

import heyblock0712.hnbcchat.cord.ConfigManager;
import heyblock0712.hnbcchat.cord.DiscordManager;
import heyblock0712.hnbcchat.listeners.PlayerChatListener;
import heyblock0712.hnbcchat.listeners.PlayerDisconnectListener;
import heyblock0712.hnbcchat.listeners.ServerSwitchListener;
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
        getProxy().getPluginManager().registerListener(this, new heyblock0712.hnbcchat.listeners.PlayerLoginListener());
        getProxy().getPluginManager().registerListener(this, new PlayerDisconnectListener());
        getProxy().getPluginManager().registerListener(this, new ServerSwitchListener());

        // Discord Listener
        String channelId = ConfigManager.getConfig().getString("Discord.Channels.Global");
        JDA jda = DiscordManager.getJDA();
        if (jda != null) {
            // DC
            jda.addEventListener(new DiscordToMinecraftMessageListener(channelId));

            // BC
            getProxy().getPluginManager().registerListener(this, new heyblock0712.hnbcchat.listeners.discord.MinecraftToDiscordMessageListener(channelId));
            getProxy().getPluginManager().registerListener(this, new heyblock0712.hnbcchat.listeners.discord.PlayerLoginListener(channelId));
            getProxy().getPluginManager().registerListener(this, new heyblock0712.hnbcchat.listeners.discord.ServerSwitchListener(channelId));
        }
    }

    @Override
    public void onDisable() {
        intention = null;
        getProxy().unregisterChannel("BungeeCord");
    }

    public static Plugin getIntention() {return intention;}
}
