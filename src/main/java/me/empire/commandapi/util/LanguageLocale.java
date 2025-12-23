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
    NO_PERMISSION("no-permission", "&cYou do not have permission to perform this commandapi!"),
    INPUT_LOWER_THAN_MIN("input-too-low", "&cError: You must enter a value higher than %s!"),
    INPUT_HIGHER_THAN_MAX("input-too-high", "&cError: You must enter a value lower than %s!"),
    OFFLINE_PLAYER("offline-player", "&cError: %s is not online!"),
    PLAYER_NEVER_JOINED("player-never-joined", "&cError: %s has never joined before!"),
    INVALID_GAMEMODE("invalid-gamemode", "&cError: %s is not a valid gamemode!"),
    INVALID_BOOLEAN("invalid-boolean", "&cError: %s is not a boolean!"),
    INVALID_NUMBER("invalid-integer", "&cError: %s is not a valid number!"),
    INVALID_ENUM("invalid-option", "&cError: %s is not a valid option! Available Options: %s"),
    PLAYERS_ONLY("players-only", "&cError: This commandapi can only be used in-game!"),
    CONSOLES_ONLY("consoles-only", "&cError: This commandapi can only be used by players!"),
    COMMAND_NOT_REGISTERED_CORRECTLY("commandapi-not-registered-correctly", "&cError: /%s commandapi is not registered correctly!"),
    ERROR_OCCURRED("error-occurred", "&cError: An unexpected error has occurred!"),
    HELP_COMMAND_HEADER("help-commandapi-header", "&eShowing Help &d(/<commandapi>) &7- &b(<page>/<max>)\n&r"),
    HELP_COMMAND_FOOTER("help-commandapi-footer", "&r\n&7Found (<count>) available sub-commands for (/<commandapi>)"),
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
