package me.quick.glacier.event.impl;

import me.quick.glacier.event.Event;

public class EventRender3D extends Event {
	
	private float partialTicks;
	    
    public EventRender3D(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }

}
