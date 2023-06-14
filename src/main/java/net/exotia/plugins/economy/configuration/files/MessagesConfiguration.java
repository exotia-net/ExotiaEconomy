package net.exotia.plugins.economy.configuration.files;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class MessagesConfiguration extends OkaeriConfig {
    private String invalidCommandUsage = "&cNie poprawne użycie komendy &8>> &7{command}";
    private String invalidCommandHeader = "&cNie poprawne użycie komendy!";
    private String commandTemplate = "&8 >> &7{schematic}";
    private String noPermission = "&cNie masz permisji do tej komendy! &7({permissions})";
    private String invalidCoin = "&8&l>> &cNiepoprawna moneta!";
    private String invalidInteger = "&8&l>> &cNiepoprawna kwota!";
    private String playerIsOffline = "&8&l>> &cTen gracz jest offline!";
    private String onlyForPlayer = "&8&l>> &cTa komenda jest tylko dla gracza!";
    private List<String> balance = Arrays.asList("", "&8&l>> &7Monety na twoim koncie: &e{account_balance}", "&8&l>> &7Monety w twoim ekwipunku: &e{physical_balance}", "");

    private String adminGiveMoney = "&8&l>> &aDodałes {value} do konta gracza {player_name}";
    private String playerReceivedMoneyFromServer = "&8&l>> &aNa twoje konto wplynelo {value}!";
    private String adminTakeMoney = "&8&l>> &aZabrales {value} z konta gracza {player_name}";
    private String adminTookMoney = "&8&l>> &aZ twojego konta zostalo zabrane {value}!";

    private String youCanNotTransferMoneySelf = "&8&l>> &cNie mozesz przelac pieniedzy sam sobie!";
    private String tooSmallAmount = "&8&l>> &cKwota musi byc wieksza badz rowna {min}!";
    private String youDontHaveEnoughMoney = "&8&l>> &cNie masz tyle pieniedzy!";
    private String successfullyTransferred = "&8&l>> &aPrzelales {value} na konto gracza {player_name}";
    private String receivedTransfer = "&8&l>> &aOtrzymales {value} od gracza {player_name}";

    private String youSoldItems = "&8&l>> &aSprzedales {count} przedmiotow za laczna kwote za {price}";
    private String youSoldItem = "&8&l>> &aSprzedales {count} {item_name} za {price}";
}
