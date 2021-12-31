package me.quick.glacier.cosmetic.cape;

import net.minecraft.util.ResourceLocation;

public enum Capes {

    NONE("None", new ResourceLocation("")),
    ANTI_TIKTOK("Anti-TikTok", new ResourceLocation("glacier/capes/anti-tiktok.png")),
    GLACIER_WHITE_LOGO("Glacier Logo (white)", new ResourceLocation("glacier/capes/glacier-white-logo.png")),
    GLACIER_AQUA_SPLATTER("Glacier Splatter (aqua)", new ResourceLocation("glacier/capes/glacier-aqua-splatter.png")),
    GLACIER_RED_SPLATTER("Glacier Splatter (red)", new ResourceLocation("glacier/capes/GlacierColor5.png")),
    GLACIER_PURPLE_SPLATTER("Glacier Splatter (purple)", new ResourceLocation("glacier/capes/GlacierColor4.png")),
    GLACIER_ORANGE_SPLATTER("Glacier Splatter (orange)", new ResourceLocation("glacier/capes/GlacierColor2.png")),
    GLACIER_GREEN_SPLATTER("Glacier Splatter (green)", new ResourceLocation("glacier/capes/GlacierColor3.png")),
    TREE("Tree", new ResourceLocation("glacier/capes/tree-cape.png")),
    CAMP("Camp", new ResourceLocation("glacier/capes/cape1camp.png"));

    String name;
    ResourceLocation location;
    boolean owned;

    Capes(String name, ResourceLocation location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return name.replace("glacier/capes/", " ").replace(".png", " ");
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    public ResourceLocation getResourceLocation() {
        return location;
    }


}
