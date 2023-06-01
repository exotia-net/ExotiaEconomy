package net.exotia.plugins.economy.configuration.files;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.injector.Injector;
import lombok.Getter;
import net.exotia.plugins.economy.configuration.files.sections.CommandsSection;

@Getter
public class PluginConfiguration extends OkaeriConfig {
    private CommandsSection commands = new CommandsSection();

    public void injectSections(Injector injector) {
        injector.registerInjectable(this.commands);
    }
}
