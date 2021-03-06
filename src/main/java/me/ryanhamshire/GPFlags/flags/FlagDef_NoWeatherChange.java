package me.ryanhamshire.GPFlags.flags;

import me.ryanhamshire.GPFlags.*;
import org.bukkit.World;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.Arrays;
import java.util.List;

public class FlagDef_NoWeatherChange extends FlagDefinition {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWeatherChange(WeatherChangeEvent event) {
        this.handleEvent(event.getWorld(), event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWeatherChange(ThunderChangeEvent event) {
        this.handleEvent(event.getWorld(), event);
    }

    private void handleEvent(World world, Cancellable event) {
        Flag flag = this.GetFlagInstanceAtLocation(world.getSpawnLocation(), null);
        if (flag == null) return;

        event.setCancelled(true);
    }

    public FlagDef_NoWeatherChange(FlagManager manager, GPFlags plugin) {
        super(manager, plugin);
    }

    @Override
    public String getName() {
        return "NoWeatherChange";
    }

    @Override
	public MessageSpecifier getSetMessage(String parameters) {
        return new MessageSpecifier(Messages.EnableNoWeatherChange);
    }

    @Override
    public MessageSpecifier getUnSetMessage() {
        return new MessageSpecifier(Messages.DisableNoWeatherChange);
    }

    @Override
    public List<FlagType> getFlagType() {
        return Arrays.asList(FlagType.WORLD, FlagType.SERVER);
    }
}
