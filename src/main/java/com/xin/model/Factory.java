package com.xin.model;

public class Factory {
    public Object getObject(String kind) {
        if(kind == "daily") {
            return new Daily();
        }
        return null;
    }
}
