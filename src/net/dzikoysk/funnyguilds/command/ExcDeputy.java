package net.dzikoysk.funnyguilds.command;

import net.dzikoysk.funnyguilds.basic.Guild;
import net.dzikoysk.funnyguilds.basic.User;
import net.dzikoysk.funnyguilds.basic.util.UserUtils;
import net.dzikoysk.funnyguilds.command.util.Executor;
import net.dzikoysk.funnyguilds.data.Messages;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExcDeputy implements Executor {
	
	@Override
	public void execute(CommandSender s, String[] args){
		
		Messages m = Messages.getInstance();
		Player p = (Player) s;
		User owner = User.get(p);
		
		if(!owner.hasGuild()){
			p.sendMessage(m.getMessage("deputyHasNotGuild"));
			return;
		}
		
		if(!owner.isOwner()){
			p.sendMessage(m.getMessage("deputyIsNotOwner"));
			return;
		}
		
		if(args.length < 1){
			p.sendMessage(m.getMessage("deputyPlayer"));
			return;
		}
		
		String name = args[0];
		if(!UserUtils.playedBefore(name)){
			p.sendMessage(m.getMessage("deputyPlayedBefore"));
			return;
		}
		
		User user = User.get(name);
		Guild guild = owner.getGuild();
		
		if(!guild.getMembers().contains(user)){
			p.sendMessage(m.getMessage("deputyIsNotMember"));
			return;
		}
		
		if(user.isDeputy()){
			guild.setDeputy(null);
			p.sendMessage(m.getMessage("deputyRemove"));
			Player o = Bukkit.getPlayer(user.getName());
			if(o != null) o.sendMessage(m.getMessage("deputyMember"));
		}else{
			guild.setDeputy(user);
			p.sendMessage(m.getMessage("deputySet"));
			Player o = Bukkit.getPlayer(user.getName());
			if(o != null) o.sendMessage(m.getMessage("deputyOwner"));
		}
	}
}