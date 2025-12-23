/*
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⢔⣶⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡜⠀⠀⡼⠗⡿⣾⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢄⣀⠀⠀⠀⡇⢀⡼⠓⡞⢩⣯⡀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⣀⣀⠀⠀⠀⠀⠉⠳⢜⠰⡹⠁⢰⠃⣩⣿⡇⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⢷⣿⠿⣉⣩⠛⠲⢶⡠⢄⢙⣣⠃⣰⠗⠋⢀⣯⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⣯⣠⠬⠦⢤⣀⠈⠓⢽⣿⢔⣡⡴⠞⠻⠙⢳⡄           C O D E D  B Y  
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣵⣳⠖⠉⠉⢉⣩⣵⣿⣿⣒⢤⣴⠤⠽⣬⡇
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⢻⣟⠟⠋⢡⡎⢿⢿⠳⡕⢤⡉⡷⡽⠁        V A N I X Y   A K A   L O R E N Z Z
⣧⢮⢭⠛⢲⣦⣀⠀⠀⠀⠀⡀⠀⠀⠀⡾⣥⣏⣖⡟⠸⢺⠀⠀⠈⠙⠋⠁⠀⠀
⠈⠻⣶⡛⠲⣄⠀⠙⠢⣀⠀⢇⠀⠀⠀⠘⠿⣯⣮⢦⠶⠃⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⢻⣿⣥⡬⠽⠶⠤⣌⣣⣼⡔⠊⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⢠⣿⣧⣤⡴⢤⡴⣶⣿⣟⢯⡙⠒⠤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠘⣗⣞⣢⡟⢋⢜⣿⠛⡿⡄⢻⡮⣄⠈⠳⢦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠈⠻⠮⠴⠵⢋⣇⡇⣷⢳⡀⢱⡈⢋⠛⣄⣹⣲⡀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠈⢿⣱⡇⣦⢾⣾⠿⠟⠿⠷⠷⣻⠧⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⠻⠽⠞⠊⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
*/
package dev.lorenzz.queue.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public interface C {

    static String translate(String message){
        if(message == null){
            return null;
        }
        return ChatColor.translateAlternateColorCodes ('&', message);
    }

    static List<String> translateStrings(List<String> untranslated) {
        List<String> translated = new ArrayList();

        for(String line : untranslated) {
            if (!line.isEmpty()) {
                translated.add(translate(line));
            }
        }

        return translated;
    }

    static void info(String message){
        message = ChatColor.translateAlternateColorCodes('&', message);
        message = "&a[QUEUE] &7 " + message;
        Bukkit.getConsoleSender().sendMessage( C.translate ( message ));
    }

    static void warning(String message){
        Bukkit.getLogger().warning("[QUEUE] " + message);
    }

    static void error(String message){
        Bukkit.getLogger().severe("[QUEUE] " + message);
    }


    static void line(){
        Bukkit.getConsoleSender().sendMessage( C.translate ( "&7----------------------------------------" ));
    }

    static void debug(String message){
        message = ChatColor.translateAlternateColorCodes('&', message);
        message = "&4[DEBUG] &7 " + message;
        Bukkit.getConsoleSender().sendMessage( C.translate ( message ));
    }

    static void logstart() {
        String logmessage = """
                
                &7&m---------------------------------------
                &7Welcome to Queue &8(Queue System Plugin)!
                &7Version: &a%s
                &7Developed by &cLorenzo
                &7&m----------------------------------------
                """;
        logmessage = logmessage.formatted ( Bukkit.getPluginManager ( ).getPlugin ( "Queue" ).getDescription ( ).getVersion ( ) );
        Bukkit.getConsoleSender ( ).sendMessage ( C.translate ( logmessage ) );
    }
    static void logstop() {
        String logmessage = """
                
                &7&m----------------------------------------
                &7Goodbye see you soon..!
                &7Version: &a%s
                &7Developed by &cLorenzo
                &7&m----------------------------------------
                """;
        logmessage = logmessage.formatted ( Bukkit.getPluginManager ( ).getPlugin ( "Queue" ).getDescription ( ).getVersion ( ) );
        Bukkit.getConsoleSender ( ).sendMessage ( C.translate ( logmessage ) );
    }
}