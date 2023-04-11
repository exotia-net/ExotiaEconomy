package net.exotia.plugins.economy.configuration.files;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class MessagesConfiguration extends OkaeriConfig {
    private String playerIsOffline = "&8&l>> &cTen gracz jest offline!";
    private List<String> balance = Arrays.asList("", "&8&l>> &7Monety na twoim koncie: &e{account_balance}", "&8&l>> &7Monety w twoim ekwipunku: &e{physical_balance}", "");
}
