package me.quick.glacier.misc.profile;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import me.quick.glacier.Glacier;
import me.quick.glacier.util.misc.MathUtils;
import me.quick.glacier.util.setting.Setting;
import me.quick.glacier.util.setting.impl.BooleanSetting;
import me.quick.glacier.util.setting.impl.ModeSetting;
import me.quick.glacier.util.setting.impl.NumberSetting;
import me.quick.glacier.module.Module;
import me.quick.glacier.module.hud.RenderModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ProfileSaving {

	public String configFileName;
	private File dir, configDir;
	private File dataFile;
	   
	public ProfileSaving(String configFileName) {
		this.configFileName = configFileName;
		dir = new File(System.getenv("LOCALAPPDATA"), "Glacier");
		System.out.println(dir.toString());
		configDir = new File(dir, "profiles");
		if(!dir.exists()) {
			dir.mkdir();
		}
		if(!configDir.exists()) {
			configDir.mkdir();
		}
		dataFile = new File(configDir, configFileName);
		if(!dataFile.exists()) {
			try {
				dataFile.createNewFile();
			} catch (IOException e) {e.printStackTrace();}
		}
	}
	
	public void save() {
		ArrayList<String> toSave = new ArrayList<>();

		for(Module mod : Glacier.INSTANCE.manager.modules) {
			if(mod instanceof RenderModule) {
				RenderModule rm = (RenderModule) mod;
				toSave.add("hudmodule:" + mod.getName() + ":" + mod.isEnabled() + ":" + rm.getX() + ":" + rm.getY());
			} else {
				toSave.add("module:" + mod.getName() + ":" + mod.isEnabled());
			}
		}
		
		for(Module mod : Glacier.INSTANCE.manager.modules) {
			for(Setting setting : mod.settings) {
				
				if(setting instanceof BooleanSetting) {
					BooleanSetting bool = (BooleanSetting) setting;
					toSave.add("setting:" + mod.getName() + ":" + setting.name + ":" + bool.isEnabled());
				}
				
				if(setting instanceof NumberSetting) {
					NumberSetting numb = (NumberSetting) setting;
					toSave.add("setting:" + mod.getName() + ":" + setting.name + ":" + numb.getValue());
				}
				
				if(setting instanceof ModeSetting) {
					ModeSetting mode = (ModeSetting) setting;

					try {
						toSave.add("setting:" + mod.getName() + ":" + setting.name + ":" + mode.getMode());
					} catch(ArrayIndexOutOfBoundsException e) {
						//Monsoon.sendMessage("Invalid mode");
						//Monsoon.sendMessage("Could either be an old config, or someone wanted your game to crash.");
					}
				}
			}
		}
		
		try {
			PrintWriter pw = new PrintWriter(this.dataFile);
			for(String str : toSave) {
				pw.println(str);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void load() {
		ArrayList<String> lines = new ArrayList<>();

		ArrayList<Module> nonApparentModules = new ArrayList<>();

		for(Module m : Glacier.INSTANCE.manager.getModuleList()) {
			nonApparentModules.add(m);
		}
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.dataFile));
			String line = reader.readLine();
			while(line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		for(String s : lines) {
			String[] args = s.split(":");
			if(s.toLowerCase().startsWith("module:") || s.toLowerCase().startsWith("hudmodule:")) {
				Module m = Glacier.INSTANCE.manager.getModule(args[1]);
				if(m != null) {
					if(m instanceof RenderModule) {
						RenderModule rm = (RenderModule) m;
						rm.setEnabled(Boolean.parseBoolean(args[2]));
						rm.setX(Integer.parseInt(args[3]));
						rm.setY(Integer.parseInt(args[4]));
					} else {
						m.setEnabled(Boolean.parseBoolean(args[2]));
					}
					nonApparentModules.remove(m);
				}
			} else if(s.toLowerCase().startsWith("setting:")) {
				Module m =  Glacier.INSTANCE.manager.getModule(args[1]);
				if(m != null) {
					for(Setting setting : m.settings) {
						if(setting.name.equalsIgnoreCase(args[2]) && setting != null) {
							if(setting instanceof BooleanSetting) {
								((BooleanSetting) setting).setEnabled(Boolean.parseBoolean(args[3]));
							}
							if(setting instanceof NumberSetting) {
								try {
									((NumberSetting)setting).setValue(Double.parseDouble(args[3]));
								} catch(ArrayIndexOutOfBoundsException e) {
									//Monsoon.sendMessage("Invalid amount " + args[3]);
									//Monsoon.sendMessage("Could either be an old config, or someone wanted your game to crash.");
								}
							}
							if(setting instanceof ModeSetting) {
								//System.out.println(args[3]);
								try {
									((ModeSetting) setting).setMode(args[3]);
								} catch(ArrayIndexOutOfBoundsException e) {
									//Monsoon.sendMessage("Invalid mode " + args[3]);
									//Monsoon.sendMessage("Could either be an old config, or someone wanted your game to crash.");
								}
							}
						}
					}
				}
			} else if(s.toLowerCase().startsWith("commandprefix:")) {
				//Monsoon.INSTANCE.commandManager.setPrefix(args[1]);
			}
		}

		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		for(Module lol : nonApparentModules) {
			if(lol instanceof RenderModule) {
				RenderModule m = (RenderModule) lol;

				m.setX((int) MathUtils.randomNumber(sr.getScaledWidth_double(), 0));
				m.setY((int) MathUtils.randomNumber(sr.getScaledHeight_double(), 0));
			}
		}
	}
}