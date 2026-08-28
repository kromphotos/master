package com.kristina.gwttreecrud.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.kristina.gwttreecrud.client.allnodes.AllNodesPresenter;
import com.kristina.gwttreecrud.client.allnodes.AllNodesView;
import com.kristina.gwttreecrud.client.allnodes.AllNodesViewData;
import com.kristina.gwttreecrud.client.nodeactions.NodeActionsPresenter;
import com.kristina.gwttreecrud.client.nodeactions.NodeActionsView;
import com.kristina.gwttreecrud.client.nodeadd.NodeAddPresenter;
import com.kristina.gwttreecrud.client.nodeadd.NodeAddView;
import com.kristina.gwttreecrud.client.nodeinfo.NodeInfoPresenter;
import com.kristina.gwttreecrud.client.nodeinfo.NodeInfoView;
import com.kristina.gwttreecrud.client.nodeinfo.NodeInfoViewData;
import com.kristina.gwttreecrud.client.tree.TreePresenter;
import com.kristina.gwttreecrud.client.tree.TreeView;
import com.kristina.gwttreecrud.client.tree.TreeViewData;
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
        TreeView view = new TreeView();
        TreeViewData viewData = new TreeViewData();
        
        NodeActionsView actionsView = new NodeActionsView();
        
        NodeInfoView nodeInfoView = new NodeInfoView();
        NodeInfoViewData nodeInfoViewData = new NodeInfoViewData();
        NodeInfoPresenter nodeInfoPresenter =
                new NodeInfoPresenter(nodeInfoView, nodeInfoViewData);
        nodeInfoView.setPresenter(nodeInfoPresenter);

        NodeAddView addView = new NodeAddView();
        NodeAddPresenter addPresenter = new NodeAddPresenter(addView);
        addView.setPresenter(addPresenter);
        
        NodeActionsPresenter actionsPresenter = new NodeActionsPresenter(actionsView, viewData, nodeInfoPresenter, addPresenter);
 
        actionsView.setPresenter(actionsPresenter);
        TreePresenter presenter = new TreePresenter(view, viewData, nodeInfoPresenter, actionsPresenter);
        
        view.setPresenter(presenter);
        
        //presenter.loadNodes();
        AllNodesView view2 = new AllNodesView();
        AllNodesViewData viewData2 = new AllNodesViewData();
        AllNodesPresenter presenter2 = new AllNodesPresenter(view2, viewData2);
        
        TreeController controller = new TreeController(presenter, presenter2, actionsPresenter);
        addPresenter.setController(controller);
        actionsPresenter.setController(controller);
        nodeInfoPresenter.setController(controller);
        controller.refresh();
        
        HorizontalPanel mainPanel = new HorizontalPanel();
        
        Label treeTitle = new Label("Tree:");
        treeTitle.getElement().getStyle().setProperty("fontWeight","bold");
        Label space = new Label();
        space.setWidth("40px");
        VerticalPanel treePanel = new VerticalPanel();
        treePanel.add(treeTitle);
        treePanel.add(view);
        
        VerticalPanel selectedPanel = new VerticalPanel();
        selectedPanel.add(nodeInfoView);
        mainPanel.add(treePanel);
        mainPanel.add(space);
        mainPanel.add(selectedPanel);

        RootPanel.get().add(mainPanel);
        
        Label verticalSpace1 = new Label();
        verticalSpace1.setHeight("30px");
        RootPanel.get().add(verticalSpace1);
        
        RootPanel.get().add(actionsView);
        
        Label verticalSpace2 = new Label();
        verticalSpace2.setHeight("30px");
        RootPanel.get().add(verticalSpace2);
        
        VerticalPanel allNodesPanel = new VerticalPanel();
        Label allNodesTitle = new Label("All nodes:");
        allNodesTitle.getElement().getStyle().setProperty("fontWeight","bold");
        
        //presenter2.loadNodes();
        allNodesPanel.add(allNodesTitle);
        allNodesPanel.add(view2);
        RootPanel.get().add(allNodesPanel);
    }
}
