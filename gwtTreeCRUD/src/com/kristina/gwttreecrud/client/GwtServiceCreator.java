package com.kristina.gwttreecrud.client;

import com.google.gwt.core.client.GWT;

public class GwtServiceCreator {
    private static final GwtServiceAsync INSTANCE = GWT.create(GwtService.class);
    public static GwtServiceAsync get() {
        return INSTANCE;
    }
    private GwtServiceCreator() {
        
    }
}
