package com.celeste.celestelobby.manager;

import com.celeste.celestelobby.CelesteLobby;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ConfigManager {

    private final CelesteLobby plugin;

    private final FileConfiguration config;

    public ConfigManager(final CelesteLobby plugin) {
        this.plugin = plugin;

        this.config = new YamlConfiguration();

        load();
    }

    @SuppressWarnings("unchecked")
    public <T> T get(final String path) {
        final T result = (T) config.get(path, "§cThere was an error loading the message: §e" + path);

        if (result instanceof String)
            return (T) ((String) result).replace("&", "\u00A7");

        if (result instanceof List)
            return (T) ((List<String>) result).stream()
              .map(line -> line.replace("&", "\u00A7"))
              .collect(Collectors.toList());

        return result;
    }

    @SneakyThrows
    public void put(final String path, final Object value) {
        final File file = new File(plugin.getDataFolder(), "config.yml");

        config.set(path, value);
        config.save(file);
    }

    @SneakyThrows
    public void load() {
        // config.yml
        final File fileConfig = new File(plugin.getDataFolder(), "config.yml");

        if (!fileConfig.exists()) plugin.saveResource("config.yml", false);

        config.load(fileConfig);
    }

}
