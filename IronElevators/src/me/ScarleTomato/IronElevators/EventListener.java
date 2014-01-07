package me.ScarleTomato.IronElevators;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class EventListener implements Listener{
	
	IronElevators inst;

	public EventListener(IronElevators inst){
		this.inst = inst;
	}
	
	@EventHandler
	public void downElevator(PlayerToggleSneakEvent e){
		Player p = e.getPlayer();
		Block b = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
		if(p.hasPermission("ironelevators.use") && !p.isSneaking()
				&& b.getType() == inst.elevatorMaterial){
			b = b.getRelative(BlockFace.DOWN, inst.minElevation);
			int i = inst.maxElevation; //16
			while	(i>0 && !	(
							   b.getType() == inst.elevatorMaterial
							&& b.getRelative(BlockFace.UP).getType().isTransparent()
							&& b.getRelative(BlockFace.UP, 2).getType().isTransparent()
							)
					)
			{
				//e.getPlayer().sendMessage("" + b.getLocation() + b.getType());
				i--;
				b = b.getRelative(BlockFace.DOWN);
			}
			if(i>0)
			{
				Location l = p.getLocation();
				l.setY(l.getY()-inst.maxElevation-3+i);
				p.teleport(l);
				p.getWorld().playSound(l, inst.elevatorWhoosh, 1, 0);
			}
		}
	}
	
	@EventHandler
	public void upElevator(PlayerMoveEvent e){
		Player p = e.getPlayer();
		Block b = e.getTo().getBlock().getRelative(BlockFace.DOWN);
		if(p.hasPermission("ironelevators.use") && e.getFrom().getY() < e.getTo().getY()
				&& b.getType() == inst.elevatorMaterial){
			b = b.getRelative(BlockFace.UP, inst.minElevation);
			int i = inst.maxElevation;
			while(i>0 && !(
							   b.getType() == inst.elevatorMaterial
							&& b.getRelative(BlockFace.UP).getType().isTransparent()
							&& b.getRelative(BlockFace.UP, 2).getType().isTransparent()
						  )
				 )
			{
				i--;
				b = b.getRelative(BlockFace.UP);
			}
			if(i>0)
			{
				Location l = p.getLocation();
				l.setY(l.getY()+inst.maxElevation+3-i);
				p.teleport(l);
				p.getWorld().playSound(l, inst.elevatorWhoosh, 1, 0);
			}
		}
	}
}
