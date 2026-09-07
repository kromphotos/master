package com.kristina.gwttreecrud.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.kristina.gwttreecrud.client.allnodes.AllNodesPresenter;
import com.kristina.gwttreecrud.client.allnodes.AllNodesView;
import com.kristina.gwttreecrud.client.nodeactions.NodeActionsPresenter;
import com.kristina.gwttreecrud.client.nodeactions.NodeActionsView;
import com.kristina.gwttreecrud.client.nodeadd.NodeAddPresenter;
import com.kristina.gwttreecrud.client.nodeadd.NodeAddView;
import com.kristina.gwttreecrud.client.nodeinfo.NodeInfoPresenter;
import com.kristina.gwttreecrud.client.nodeinfo.NodeInfoView;
import com.kristina.gwttreecrud.client.nodeinfo.NodeInfoViewData;
import com.kristina.gwttreecrud.client.tree.TreePresenter;
import com.kristina.gwttreecrud.client.tree.TreeView;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtTreeCRUD implements EntryPoint {
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";

    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    //    private final GwtServiceAsync greetingService = GWT.create(GwtService.class);

    /**
     * This is the entry point method.
     */
    /*
    public void onModuleLoad() {
        TestWidget widget = new TestWidget();
        RootPanel.get().add(widget);
        
        AllNodesTable table = new AllNodesTable();
        RootPanel.get().add(table);
    }
    */
    @Override
    public void onModuleLoad() {
        // ---------- Tree ----------
        TreeView treeView = new TreeView();
        TreePresenter treePresenter = new TreePresenter(treeView);
        treeView.setPresenter(treePresenter);
        
        
        // ---------- Node Info ----------
        NodeInfoView nodeInfoView = new NodeInfoView();
        NodeInfoViewData nodeInfoViewData = new NodeInfoViewData();
        NodeInfoPresenter nodeInfoPresenter =
                new NodeInfoPresenter(nodeInfoView, nodeInfoViewData);
        nodeInfoView.setPresenter(nodeInfoPresenter);

        // ---------- Node Add ----------
        NodeAddView nodeAddView = new NodeAddView();
        NodeAddPresenter nodeAddPresenter = new NodeAddPresenter(nodeAddView);
        nodeAddView.setPresenter(nodeAddPresenter);
        

        // ---------- Node Actions ----------
        NodeActionsView nodeActionsView = new NodeActionsView();
        NodeActionsPresenter nodeActionsPresenter = new NodeActionsPresenter(nodeActionsView);
        nodeActionsView.setPresenter(nodeActionsPresenter);
        
        // ---------- All Nodes ----------
        AllNodesView allNodesView = new AllNodesView();
        AllNodesPresenter allNodesPresenter = new AllNodesPresenter(allNodesView);
        
        // ---------- Controller ----------
        TreeController controller = new TreeController(treePresenter, allNodesPresenter, nodeActionsPresenter, nodeInfoPresenter, nodeAddPresenter);
        treePresenter.setController(controller);
        nodeInfoPresenter.setController(controller);
        nodeActionsPresenter.setController(controller);
        nodeAddPresenter.setController(controller);
        
        controller.refresh();
        
        HorizontalPanel mainPanel = new HorizontalPanel();
        
        Label treeTitle = new Label("Tree:");
        treeTitle.getElement().getStyle().setProperty("fontWeight","bold");
        Label space = new Label();
        space.setWidth("40px");
        VerticalPanel treePanel = new VerticalPanel();
        treePanel.add(treeTitle);
        treePanel.add(treeView);
        
        VerticalPanel selectedPanel = new VerticalPanel();
        selectedPanel.add(nodeInfoView);
        mainPanel.add(treePanel);
        mainPanel.add(space);
        mainPanel.add(selectedPanel);

        RootPanel.get().add(mainPanel);
        
        Label verticalSpace1 = new Label();
        verticalSpace1.setHeight("30px");
        RootPanel.get().add(verticalSpace1);
        
        RootPanel.get().add(nodeActionsView);
        
        Label verticalSpace2 = new Label();
        verticalSpace2.setHeight("30px");
        RootPanel.get().add(verticalSpace2);
        
        VerticalPanel allNodesPanel = new VerticalPanel();
        Label allNodesTitle = new Label("All nodes:");
        allNodesTitle.getElement().getStyle().setProperty("fontWeight","bold");
        
        //presenter2.loadNodes();
        allNodesPanel.add(allNodesTitle);
        allNodesPanel.add(allNodesView);
        RootPanel.get().add(allNodesPanel);
    }
}
