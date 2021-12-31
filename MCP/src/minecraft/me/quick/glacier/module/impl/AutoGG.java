package me.quick.glacier.module.impl;


import me.quick.glacier.event.EventTarget;
import me.quick.glacier.event.impl.EventReceiveChat;
import me.quick.glacier.module.Module;
import me.quick.glacier.util.misc.MathUtils;
import me.quick.glacier.util.setting.impl.BooleanSetting;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class AutoGG extends Module {

	public BooleanSetting toxicMode = new BooleanSetting("Toxic Mode", false, this);
	
	public AutoGG() {
		super("AutoGG", "Automatically GG", new ResourceLocation(""));
		addSettings(toxicMode);
	}
	
	private final String[] strings = new String[]{"1st Killer - ", "1st Place - ", "You died! Want to play again? Click here!", " - Damage Dealt - ", "1st - ", "Winning Team - ", "Winners: ", "Winner: ", "Winning Team: ", " win the game!", "1st Place: ", "Last team standing!", "Winner #1 (", "Top Survivors", "Winners - "};

	
	@EventTarget
	public void onChat(EventReceiveChat e) {
		for (String string : strings) {
			if(e.getMessage().contains(string)) {
				if(toxicMode.isEnabled()) {
					ArrayList<String> toxicMessages = new ArrayList<>();
					toxicMessages.add("EASE.");
					toxicMessages.add("Wow, you guys suck a lot.");
					toxicMessages.add("EASY!");
					toxicMessages.add("Even a 70 year old grandma plays better than you...");
					toxicMessages.add("You probably click at max 7 CPS.");

					mc.thePlayer.sendChatMessage(toxicMessages.get((int) MathUtils.randomNumber(toxicMessages.size() - 1, 0)));
				} else {
					mc.thePlayer.sendChatMessage("/ac gg");
				}
				return;
			}
		}
	}

}
