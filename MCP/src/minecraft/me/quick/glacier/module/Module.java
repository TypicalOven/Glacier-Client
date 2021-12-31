package me.quick.glacier.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.quick.glacier.Glacier;
import me.quick.glacier.util.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class Module {
	
	public String name, description;
	public ResourceLocation icon;
	public Minecraft mc = Minecraft.getMinecraft();
	public boolean enabled;

	public List<Setting> settings = new ArrayList<>();
	
	public Module(String name, String description, ResourceLocation icon) {
		this.name = name;
		this.description = description;
		this.icon = icon;
	}

	public void addSettings(Setting... toAdd) {
		settings.addAll(Arrays.asList(toAdd));
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void toggle() {
		setEnabled(!enabled);
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if(enabled) {
			onEnable();
		} else {
			onDisable();
		}
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public ResourceLocation getIcon() {
		return icon;
	}

	public void onEnable() {
		Glacier.INSTANCE.eventManager.register(this);
	}
	public void onDisable() {
		Glacier.INSTANCE.eventManager.unregister(this);
	}
}

