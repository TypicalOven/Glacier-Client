package me.quick.glacier.module;

import java.util.ArrayList;


import me.quick.glacier.event.EventTarget;
import me.quick.glacier.event.impl.EventClientTick;
import me.quick.glacier.event.impl.EventRender2D;
import me.quick.glacier.module.hud.RenderModule;
import me.quick.glacier.module.hud.impl.*;
import me.quick.glacier.module.impl.*;
import net.minecraft.client.Minecraft;

public class ModuleManager {
    public ArrayList<Module> modules;
    public Minecraft mc = Minecraft.getMinecraft();

    public TimeChanger timeChanger;
    public BlockOverlay blockoverlay;
    public ToggleSprint togglesprint;
    
    public ModuleManager() {
        this.modules = new ArrayList<>();

        //modules.add(new Watermark());
        modules.add(new FPSModule());
        modules.add(new CPSModule());
        modules.add(new ReachDisplay());
        modules.add(new ClockModule());
        modules.add(new PingModule());
        modules.add(new Keystrokes());
        modules.add(new Coords());
        modules.add(new ArmorStatus());
        modules.add(blockoverlay = new BlockOverlay());
        modules.add(timeChanger = new TimeChanger());
        modules.add(togglesprint = new ToggleSprint());
        modules.add(new PotionStatus());
        modules.add(new Fullbright());
        modules.add(new AutoGG());
    }
    
    public Module getModule(String name) {
		for (Module m : modules) {
			if (m.name.equalsIgnoreCase(name)) {
				return m;
			}
		}
		return null;
	}
    
    public ArrayList<Module> getModuleList() {
		return modules;
	}
    
    public ArrayList<RenderModule> getRenderModuleList() {
    	ArrayList<RenderModule> shit = new ArrayList<>();
		for(Module tocase : modules) {
    		if(tocase instanceof RenderModule) {
    			RenderModule m = (RenderModule) tocase;
				shit.add(m);
    		}
    	}
		return shit;
	}
   
    
    public void renderHook() {
    	for(RenderModule m : getRenderModuleList()) {
			if (m.isEnabled()) {
    			m.render();
    		}
    	}
    }

}
