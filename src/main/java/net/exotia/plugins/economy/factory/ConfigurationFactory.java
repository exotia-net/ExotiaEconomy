package net.exotia.plugins.economy.factory;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;

public class ConfigurationFactory {
    private final File dataDirectory;

    public ConfigurationFactory(@NonNull File dataDirectory) {
        this.dataDirectory = dataDirectory;
    }
    public <T extends OkaeriConfig> T produce(@NonNull Class<T> type, @NonNull String fileName, ObjectSerializer<?>... serializers) {
        return this.produce(type, new File(this.dataDirectory, fileName), serializers);
    }
    public <T extends OkaeriConfig> T produce(@NonNull Class<T> type, @NonNull File file, ObjectSerializer<?>... serializers) {
        return ConfigManager.create(type, it -> it
                .withConfigurer(new YamlBukkitConfigurer())
                .withBindFile(file)
                .withSerdesPack(new SerdesBukkit())
                .withSerdesPack(serdesRegistry -> {
                    for (ObjectSerializer<?> serializer : serializers) {
                        serdesRegistry.register(serializer);
                    }
                })
                .saveDefaults().load(true));
    }
}