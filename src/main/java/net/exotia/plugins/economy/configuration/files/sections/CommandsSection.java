package net.exotia.plugins.economy.configuration.files.sections;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class CommandsSection extends OkaeriConfig {
    private HashMap<String, String> economyCommands = this.setupCommands();

    private HashMap<String, String> setupCommands() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pay", "przelej");
        hashMap.put("bank", "bank");
        hashMap.put("balance", "pieniadze");
        return hashMap;
    }
}
