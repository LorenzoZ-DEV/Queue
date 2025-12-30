package me.empire.commandapi.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

/**
 * @author MTR
 */

@Getter
@AllArgsConstructor
public enum LanguageLocale {
    NO_PERMISSION("no-permission", "&5&lCORE &8&l» &cNessun permesso!"),
    INPUT_LOWER_THAN_MIN("input-too-low", "&5&lCORE &8&l» &cErrore: You must enter a value higher than %s!"),
    INPUT_HIGHER_THAN_MAX("input-too-high", "&5&lCORE &8&l» &cErrore: You must enter a value lower than %s!"),
    OFFLINE_PLAYER("offline-player", "&5&lCORE &8&l» &cErrore: %s non è online!"),
    PLAYER_NEVER_JOINED("player-never-joined", "&5&lCORE &8&l» &cErrore: %s non è mai entrato nel server!"),
    INVALID_GAMEMODE("invalid-gamemode", "&5&lCORE &8&l» &cErrore: %s non e una gamemode valida!"),
    INVALID_BOOLEAN("invalid-boolean", "&5&lDEVELOPER &8&l» &cErrore: %s non è un booleano!"),
    INVALID_NUMBER("invalid-integer", "&5&lDEVELOPER &8&l» &cErrore: %s non è un numero valido!"),
    INVALID_ENUM("invalid-option", "&5&lCORE &8&l» &cErrore: %s is not a valid option! Available Options: %s"),
    PLAYERS_ONLY("players-only", "&5&lCORE &8&l» &cErrore: Questo puo commando puo essere eseguito solo da un giocatore!"),
    CONSOLES_ONLY("consoles-only", "&5&lCORE &8&l» &cError: This commandapi can only be used by players!"),
    COMMAND_NOT_REGISTERED_CORRECTLY("commandapi-not-registered-correctly", "&c&lBUG &8&l» &cError: /%s commandapi is not registered correctly!"),
    ERROR_OCCURRED("error-occurred", "&5&lCORE &8&l» &cError: An unexpected error has occurred!"),
    HELP_COMMAND_HEADER("help-commandapi-header", "&ePagina Help &d(/<commandapi>) &7- &b(<page>/<max>)\n&r"),
    HELP_COMMAND_FOOTER("help-commandapi-footer", "&r\n&7Trovati (<count>) sottocommandi per  (/<commandapi>)"),
    HELP_COMMAND_ENTRY_DESCRIPTION("help-commandapi-entry", " &7- &e/<commandapi> &d<arguments> &7- &f<description>"),
    HELP_COMMAND_ENTRY_NO_DESCRIPTION("help-commandapi-entry-no-description", " &7- &e/<commandapi> &d<arguments>")
    ;

    private final String path;

    @Setter
    private Object value;

    public String getString() {
        return (String) value;
    }

    public String getFormattedString(Object... replacements) {
        return String.format((String) value, replacements);
    }

    @SneakyThrows
    public static void init(Plugin plugin) {
        File file = new File(plugin.getDataFolder(), "commands-language.yml");

        for (LanguageLocale locale : values()) {
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

            if (!yamlConfiguration.contains(locale.getPath())) {
                yamlConfiguration.set(locale.getPath(), locale.getValue());
                yamlConfiguration.save(file);
            }

            locale.setValue(yamlConfiguration.get(locale.getPath()));
        }
    }
}
