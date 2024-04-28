package heyblock0712.hnbcchat;

import heyblock0712.hnbcchat.cord.ConfigManager;
import net.md_5.bungee.api.plugin.Plugin;

public final class HNBC_Chat extends Plugin {
    private static Plugin intention;

    @Override
    public void onEnable() {
        intention = this;
        ConfigManager.loadConfig();

        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        intention = null;
        // Plugin shutdown logic
    }

    public static Plugin getIntention() {return intention;}
}
