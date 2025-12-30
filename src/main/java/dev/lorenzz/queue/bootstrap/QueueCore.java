/*
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⢔⣶⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡜⠀⠀⡼⠗⡿⣾⠀⠀
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

package dev.lorenzz.queue.bootstrap;

import dev.lorenzz.queue.cmds.admin.QueueAdminCmd;
import dev.lorenzz.queue.cmds.user.QueueCmd;
import dev.lorenzz.queue.managment.ManagerService;
import dev.lorenzz.queue.queue.QueueDispatcher;
import dev.lorenzz.queue.util.C;
import dev.lorenzz.queue.util.ConfigFile;
import lombok.Getter;
import me.empire.commandapi.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class QueueCore
{
    @Getter
    private static QueueCore instance;
    @Getter
    private JavaPlugin plugin ;
    @Getter
    private ConfigFile configFile;
    @Getter
    private ManagerService managerService;
    @Getter
    private QueueDispatcher queueDispatcher;

    public QueueCore ( JavaPlugin plugin ) {
        this.plugin = plugin;
    }
    private void registerService(){
        C.info("Registering service...");
        this.managerService = new ManagerService(this);
        try{
            this.managerService.init();
            this.queueDispatcher = new QueueDispatcher(plugin, managerService.getQueueManager());
            this.queueDispatcher.start();

        } catch (Exception exception ){
            C.line();
            C.error("An error occurred while initializing the ManagerService: " + exception.getMessage());
            C.error("The server will be stopped");
            C.line();
            Bukkit.getServer().shutdown();
        }
    }

    private void registerChannels(){
        this.plugin.getServer().getMessenger().registerOutgoingPluginChannel(this.plugin, "BungeeCord");
    }

    private void registerCommands(){
        C.info("Registering commands...");
        CommandManager manager = new CommandManager(this.plugin); 
        manager.register(
                //user
                new QueueCmd(),
                //admin
                new QueueAdminCmd()
        );


    }
    private void registerConfigs(){
        C.info("Loading config files...");
        this.configFile = new ConfigFile(this.plugin, "config");
        this.configFile.saveFile();
    }

    public void start(){
        instance = this ;
        registerChannels();
        registerConfigs();
        registerService();
        registerCommands();
        C.info("&7Plugin start please wait");
        C.logstart();
    }
    public void stop(){
        C.info("&7Plugin stopping...");
        if (this.managerService != null) {
            this.managerService.shutdown();
        }
        if (this.queueDispatcher != null) {
            this.queueDispatcher.stop();
        }
        C.logstop();
    }
    public static QueueCore get(){
        return instance;
    }
}
