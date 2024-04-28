package heyblock0712.hnbcchat.cord;

import heyblock0712.hnbcchat.HNBC_Chat;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigManager {
    private static Configuration config;
    private static File configFile;

    public static boolean loadConfig() {
        File dataFolder = HNBC_Chat.getIntention().getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        configFile = new File(dataFolder, "config.yml");

        try {
            if (!configFile.exists()) {
                InputStream inputStream = HNBC_Chat.getIntention().getResourceAsStream("config.yml");
                Files.copy(inputStream, configFile.toPath());
            }

            config = YamlConfiguration.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            HNBC_Chat.getIntention().getLogger().severe("無法加載配置文件: " + configFile.getAbsolutePath());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Configuration getConfig() {return config;}

    // 添加其他方法以便获取和设置配置参数
    public String getString(String path) {
        return config.getString(path);
    }

    public int getInt(String path) {
        return config.getInt(path);
    }

    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    // 添加方法以便设置配置参数
    public void set(String path, Object value) {
        config.set(path, value);
    }
}
