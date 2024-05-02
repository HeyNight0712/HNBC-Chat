package heyblock0712.hnbcchat;

import heyblock0712.hnbcchat.cord.ConfigManager;
import heyblock0712.hnbcchat.cord.DiscordManager;
import heyblock0712.hnbcchat.listeners.PlayerChatListener;
import heyblock0712.hnbcchat.listeners.PlayerDisconnectListener;
import heyblock0712.hnbcchat.listeners.PlayerLoginListener;
import heyblock0712.hnbcchat.listeners.PlayerSwitchesServerListener;
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
    }

    @Override
    public void onDisable() {
        intention = null;
        getProxy().unregisterChannel("BungeeCord");
    }

    public static Plugin getIntention() {return intention;}
}
