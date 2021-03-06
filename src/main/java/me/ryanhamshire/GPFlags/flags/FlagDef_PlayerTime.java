package me.ryanhamshire.GPFlags.flags;

import me.ryanhamshire.GPFlags.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class FlagDef_PlayerTime extends PlayerMovementFlagDefinition implements Listener {

    @Override
    public boolean allowMovement(Player player, Location lastLocation, Location to)
    {
        if(lastLocation == null) return true;
        Flag flag = this.GetFlagInstanceAtLocation(to, player);

        if(flag == null) {
            if (this.GetFlagInstanceAtLocation(lastLocation, player) != null) {
                player.resetPlayerTime();
            }
            return true;
        }
        if(flag == this.GetFlagInstanceAtLocation(lastLocation, player)) return true;

        String time = flag.parameters;
        if(time.equalsIgnoreCase("day")) {
            player.setPlayerTime(0, false);
        } else if(time.equalsIgnoreCase("noon")) {
            player.setPlayerTime(6000, false);
        } else if(time.equalsIgnoreCase("night")) {
            player.setPlayerTime(12566, false);
        } else if(time.equalsIgnoreCase("midnight")) {
            player.setPlayerTime(18000, false);
        }
        return true;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Flag flag = this.GetFlagInstanceAtLocation(player.getLocation(), player);

        if(flag != null) {
            String time = flag.parameters;
            if (time.equalsIgnoreCase("day")) {
                player.setPlayerTime(0, false);
            } else if (time.equalsIgnoreCase("noon")) {
                player.setPlayerTime(6000, false);
            } else if (time.equalsIgnoreCase("night")) {
                player.setPlayerTime(12566, false);
            } else if (time.equalsIgnoreCase("midnight")) {
                player.setPlayerTime(18000, false);
            }
        }
    }

    public FlagDef_PlayerTime(FlagManager manager, GPFlags plugin) {
        super(manager, plugin);
    }

    @Override
    public SetFlagResult ValidateParameters(String parameters)
    {
        if(parameters.isEmpty())
        {
            return new SetFlagResult(false, new MessageSpecifier(Messages.PlayerTimeRequired));
        }
        if(!parameters.equalsIgnoreCase("day") && !parameters.equalsIgnoreCase("noon") &&
        !parameters.equalsIgnoreCase("night") && !parameters.equalsIgnoreCase("midnight")) {
            return new SetFlagResult(false, new MessageSpecifier(Messages.PlayerTimeRequired));
        }
        return new SetFlagResult(true, this.getSetMessage(parameters));
    }

    @Override
    public String getName() {
        return "PlayerTime";
    }

    @Override
	public MessageSpecifier getSetMessage(String parameters) {
        return new MessageSpecifier(Messages.PlayerTimeSet, parameters);
    }

    @Override
    public MessageSpecifier getUnSetMessage() {
        return new MessageSpecifier(Messages.PlayerTimeUnSet);
    }

}
