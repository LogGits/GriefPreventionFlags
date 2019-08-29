package me.ryanhamshire.GPFlags;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.raid.RaidTriggerEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

public class FlagDef_RaidMemberOnly extends FlagDefinition {

	@EventHandler
	private void onRaidTrigger(RaidTriggerEvent event) {
		Flag flag = this.GetFlagInstanceAtLocation(event.getPlayer().getLocation(), null);
		if (flag == null) return;
		Player player = event.getPlayer();
		Claim claim = GriefPrevention.instance.dataStore.getClaimAt(player.getLocation(), false, null);
		if (claim == null) return;
		if (claim.allowAccess(player) != null) {
			event.setCancelled(true);
			player.removePotionEffect(PotionEffectType.BAD_OMEN);
			GPFlags.sendMessage(player, TextMode.Warn, Messages.RaidMemberOnlyDeny);
		}
	}

	FlagDef_RaidMemberOnly(FlagManager manager, GPFlags plugin) {
		super(manager, plugin);
	}

	@Override
	String getName() {
		return "RaidMemberOnly";
	}

	@Override
	MessageSpecifier GetSetMessage(String parameters) {
		return new MessageSpecifier(Messages.EnabledRaidMemberOnly);
	}

	@Override
	MessageSpecifier GetUnSetMessage() {
		return new MessageSpecifier(Messages.DisabledRaidMemberOnly);
	}

	@Override
	List<FlagType> getFlagType() {
		return Collections.singletonList(FlagType.CLAIM);
	}

}
