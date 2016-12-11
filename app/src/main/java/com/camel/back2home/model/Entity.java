package com.camel.back2home.model;


import com.camel.back2home.model.anotation.Ignore;
import com.camel.back2home.model.anotation.Key;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Entity implements Serializable, Cloneable {


    //	@SerializedName("id")
    @Key
    protected Long id = 0L;

    @Ignore
    private Action action = Action.None;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Action getAction() {
        return this.action;
    }

    public void setAction(Action value) {
        this.action = value;
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> T getClone() {

        Entity obj = null;
        try {
            obj = (Entity) super.clone();
        } catch (CloneNotSupportedException e) {
            // Log.e(App.TAG, e.getMessage());
        }
        return (T) obj;
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> T getMe() {
        return (T) this;
    }
}
