package com.kristina.gwttreecrud.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class TestWidget extends Composite {
    private FlowPanel panel;
    private Button button;
    
    public TestWidget()
    {
        init();
    }
    
    void init(){
        panel = new FlowPanel();
        button = new Button("Нажать");
        
        panel.add(button);
        
        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Label label = new Label();
                label.setText("Привет!");
                panel.add(label);
            }
        });
        initWidget(panel);
    }

}
