package me.quick.glacier.cosmetic;

import net.minecraft.util.ResourceLocation;

public class Cosmetic {

    public String name;
    public ResourceLocation location, icon;
    public boolean wearing, owned;

    public String getName() {
        return name;
    }

    public void setIcon(ResourceLocation icon) {
        this.icon = icon;
    }

    public void setLocation(ResourceLocation location) {
        this.location = location;
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    public ResourceLocation getLocation() {
        return location;
    }

    public boolean isWearing() {
        return wearing;
    }

    public boolean isOwned() {
        return owned;
    }

    public void setWearing(boolean wearing) {
        this.wearing = wearing;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }
}
