package me.quick.glacier.module.hud.impl;

import me.quick.glacier.module.hud.RenderModule;
import me.quick.glacier.util.setting.impl.BooleanSetting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;


import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ArmorStatus extends RenderModule {
	
	BooleanSetting hor = new BooleanSetting("Horizontal", true, this);

	public ArmorStatus() {
		super("Armor Status", "Displays your Armor", new ResourceLocation("glacier/icon/module/armorstats.png"));
		super.addSettings(hor);
	}
	
	@Override
	public int getWidth() {
		if(hor.isEnabled()) {
		    return 78;
		} else {
			return 17;
		}
	}

	@Override
	public int getHeight() {
		if(hor.isEnabled()) {
		    return 20;
		} else {
			return 63;
		}
	}

	@Override
	public void render() {
		if(hor.isEnabled()) {
		     mc.getRenderItem().renderItemAndEffectIntoGUI(mc.thePlayer.getCurrentArmor(3), getX(), getY() + 2);
		     mc.getRenderItem().renderItemAndEffectIntoGUI(mc.thePlayer.getCurrentArmor(2), getX() + 20, getY() + 2);
		     mc.getRenderItem().renderItemAndEffectIntoGUI(mc.thePlayer.getCurrentArmor(1), getX() + 40, getY() + 2);
		     mc.getRenderItem().renderItemAndEffectIntoGUI(mc.thePlayer.getCurrentArmor(0), getX() + 60, getY() + 2);
		} else {
			for(int i = 0; i < mc.thePlayer.inventory.armorInventory.length; i++) {
				ItemStack itemStack = mc.thePlayer.inventory.armorInventory[i];
				renderItemStack(getX(), getY(), i, itemStack);
			}
		}
		
	}
	
	public void renderDummy() {
		if(hor.isEnabled()) {
			mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Items.diamond_helmet), getX(), getY() + 2);
			mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Items.diamond_chestplate), getX() + 20, getY() + 2);
			mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Items.diamond_leggings), getX() + 40, getY() + 2);
			mc.getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(Items.diamond_boots), getX() + 60, getY() + 2);
		} else {
			renderItemStack(getX(), getY(), 3, new ItemStack(Items.diamond_helmet));
			renderItemStack(getX(), getY(), 2, new ItemStack(Items.diamond_chestplate));
			renderItemStack(getX(), getY(), 1, new ItemStack(Items.diamond_leggings));
			renderItemStack(getX(), getY(), 0, new ItemStack(Items.diamond_boots));
		}
		
	}

	private void renderItemStack(int x, int y, int i, ItemStack is) {
		
		if(is == null) {
			return;
		}
		
		GL11.glPushMatrix();
		int yAdd = (-16 * i) + 48;
		
		/*if(is.getItem().isDamageable()) {
			double damage = ((is.getMaxDamage() - is.getItemDamage()) / (double) is.getMaxDamage() * 100);
			font.drawString(String.format("%.0f", damage), pos.getAbsoluteX() + 20, pos.getAbsoluteY() + yAdd + 5, 0x00ff00);
		}*/
		
		RenderHelper.enableGUIStandardItemLighting();
		mc.getRenderItem().renderItemAndEffectIntoGUI(is, x, y + yAdd);
		GL11.glPopMatrix();
		
	}
}
