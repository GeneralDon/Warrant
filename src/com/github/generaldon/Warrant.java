package com.github.generaldon;
 
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
 
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
 
public class Warrant extends JavaPlugin {
        /*
         * I don't believe making an instance (public static Warrant plugin;) is necessary for this plugin
         * I may be wrong though. Feel free to keep it.
         */
        //This is how I do the logger, you don't have to do it this way
        public static final Logger logger=Logger.getLogger("Minecraft");
        private final ChatColor RED=ChatColor.RED;
        private final ChatColor BLUE=ChatColor.BLUE;
        private final ChatColor GRAY=ChatColor.GRAY;
        private final ChatColor PURPLE=ChatColor.DARK_PURPLE;
        //HashMap holding all the targets and their warrants
        private Map<Player,WarrantTimer> activeWarrants=new HashMap<Player,WarrantTimer>();
 
        @Override
        public void onEnable(){
                //No need to initialize logger again
                logger.info("Warrant version 0.0.1 enabled sucessfully!");
        }
       
        @Override
        public void onDisable(){
                logger.info("Warrant version 0.0.1 disabled sucessfully!");
        }
       
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
        {
        //I prefer to check the command, before I do anything else, but it's up to you
        if(cmd.getName().equalsIgnoreCase("warrant"))
        {
                //Be sure to check if the sender is a player, and not a remote sender before you cast
                if(sender instanceof Player)
                {
                        Player player=(Player)sender;
                        if(args.length==0)
                        {
                                //No arguments, only "/warrant" has been typed
                                player.sendMessage(RED + "No parameters given!");
                        }
                        /*
                                 * I don't know why you checked to see if the first argument was "warrant"
                                 * and then checked to see if the same argument was a player's name.
                                 * I will assume it was a mistake and leave it out.
                                 */
                        else if(args.length==2)
                        {
                                if(args[0].equalsIgnoreCase("revoke"))
                                {
                                        Player target=Bukkit.getPlayer(args[1]);
                                        if(target!=null)
                                        {
                                                //Player is found
                                                //Find the warrant issued to the target and revoke and remove it
                                                activeWarrants.get(target).revoke();
                                                activeWarrants.remove(target);
                                        }
                                        else
                                        {
                                                //Player is not found
                                                player.sendMessage(RED + "That user is not online!");
                                        }
                                }
                                else
                                {
                                        //First argument is not revoke but there are 2 arguments
                                        player.sendMessage(RED + "Incorrect usage!");
                                }
                        }
                        else if(args.length==3)
                        {
                                //There are two arguments: /warrant args[0] args[1]
                                //Start checking the first argument for "send", "revoke", and "accept"
                                if(args[0].equalsIgnoreCase("send"))
                                {
                                        //Bukkit.getServer().getPlayer(String) isn't necessary, you only need to use Bukkit.getPlayer(String)
                                        Player target=Bukkit.getPlayer(args[1]);
                                        if(target!=null)
                                        {
                                                //Player is found
                                                //Now parse the third argument
                                                int minutes=Integer.parseInt(args[2]);
                                                //Send the players messages
                                                player.sendMessage(GRAY + "Sending a warrant to " + target.getDisplayName());
                                                target.sendMessage(PURPLE + player.getName() + " has sent you a warrant!");
                                                //Give target 1 paper
                                                target.getInventory().addItem(new ItemStack(Material.PAPER));
                                                //Reference the target with a new active warrant
                                                activeWarrants.put(target,new WarrantTimer(target,minutes));                   
                                        }
                                        else
                                        {
                                                //Player is not found
                                                player.sendMessage(RED + "That user is not online!");
                                        }
                                }
                                else
                                {
                                        //First argument is not send but there are 3 arguments
                                        player.sendMessage(RED + "Incorrect usage!");
                                }
                        }
                        else
                        {
                                //Incorrect number of arguments
                                player.sendMessage(RED + "Incorrect usage!");
                        }
                }
        }
        return false;
        }
}