package com.kristina.gwttreecrud.client;

import com.google.gwt.core.client.GWT;


//TODO(by Tutor)
// Плохое имя. Креатор это тот, кто что то создает на постоянке.
// Почему тогда глоабльный ивент бас ты не назвала креатором?
public class GwtServiceCreator {
    private static final GwtServiceAsync INSTANCE = GWT.create(GwtService.class);
    
    public static GwtServiceAsync get() {
        return INSTANCE;
    }
    

  //TODO(by Tutor)
  // зачем?
    private GwtServiceCreator() {
        
    }
}
