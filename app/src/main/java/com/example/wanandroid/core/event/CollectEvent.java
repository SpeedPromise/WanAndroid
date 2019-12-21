package com.example.wanandroid.core.event;

public class CollectEvent {
    private boolean isCollect;
    public int position;

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public CollectEvent (boolean isCollect, int position) {
        this.isCollect = isCollect;
        this.position = position;
    }
}
