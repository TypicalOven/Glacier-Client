package me.quick.glacier.event.impl;

import me.quick.glacier.event.Event;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;

public class EventUpdateModel extends Event {
    public Entity entity;
    public ModelPlayer modelPlayer;

    public EventUpdateModel(Entity entity, ModelPlayer modelPlayer) {
        this.entity = entity;
        this.modelPlayer = modelPlayer;
    }
}
