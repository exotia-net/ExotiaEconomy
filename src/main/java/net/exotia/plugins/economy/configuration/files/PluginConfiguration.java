package net.exotia.plugins.economy.configuration.files;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.injector.Injector;
import lombok.Getter;
import net.exotia.plugins.economy.configuration.files.sections.CommandsSection;

@Getter
public class PluginConfiguration extends OkaeriConfig {
    private CommandsSection commands = new CommandsSection();
    @Comment("transfer fee in percent")
    private double transferFee = 5;
    private int minTransferAmount = 10;

    public void injectSections(Injector injector) {
        injector.registerInjectable(this.commands);
    }
}
