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
import com.kristina.gwttreecrud.client.tree.TreePresenter;
import com.kristina.gwttreecrud.client.tree.TreeView;

public class GwtTreeCRUD implements EntryPoint {
    private static final String STYLE_VERTICAL_SPACE = "vertical-space";
    private static final String STYLE_HORIZONTAL_SPACE = "horizontal-space";
    private static final String STYLE_PANEL_TITLE = "panel-title";

    private static final String NAME_OF_ALL_NODES_PANEL = "All nodes:";
    private static final String NAME_OF_TREE_PANEL = "Tree:";

    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";

    @Override
    public void onModuleLoad() {
        // ---------- Tree ----------
        TreeView treeView = new TreeView();
        TreePresenter treePresenter = new TreePresenter(treeView);

        //TODO(by Tutor)
        // зачем? это можно вызывать в конструкторе презентора
        // ---------- Node Info ----------
        NodeInfoView nodeInfoView = new NodeInfoView();
        //TODO(by Tutor)
        // а вью дата то тут чего делает и как вдруг она тут оказалась?
        NodeInfoPresenter nodeInfoPresenter = new NodeInfoPresenter(nodeInfoView);

        // ---------- Node Add ----------
        NodeAddView nodeAddView = new NodeAddView();
        NodeAddPresenter nodeAddPresenter = new NodeAddPresenter(nodeAddView);

        // ---------- Node Actions ----------
        NodeActionsView nodeActionsView = new NodeActionsView();
        NodeActionsPresenter nodeActionsPresenter = new NodeActionsPresenter(nodeActionsView);

        // ---------- All Nodes ----------
        AllNodesView allNodesView = new AllNodesView();
        AllNodesPresenter allNodesPresenter = new AllNodesPresenter(allNodesView);
        //TODO(by Tutor)
        // зачем? это можно вызывать в конструкторе презентора

        HorizontalPanel mainPanel = new HorizontalPanel();

        //---дерево + информация об узле---
        Label treeTitle = new Label(NAME_OF_TREE_PANEL);
        Label space = new Label();

        space.addStyleName(STYLE_HORIZONTAL_SPACE);
        treeTitle.addStyleName(STYLE_PANEL_TITLE);

        VerticalPanel treePanel = new VerticalPanel();
        treePanel.add(treeTitle);
        treePanel.add(treeView);

        VerticalPanel selectedPanel = new VerticalPanel();
        selectedPanel.add(nodeInfoView);

        mainPanel.add(treePanel);
        mainPanel.add(space);
        mainPanel.add(selectedPanel);

        RootPanel.get().add(mainPanel);

        //---отступ между деревом и кнопками---
        Label verticalSpace1 = new Label();
        verticalSpace1.addStyleName(STYLE_VERTICAL_SPACE);
        RootPanel.get().add(verticalSpace1);

        //---кнопки действий--
        RootPanel.get().add(nodeActionsView);

        //---отступ между кнопками и таблицей allnodes---
        Label verticalSpace2 = new Label();
        verticalSpace2.addStyleName(STYLE_VERTICAL_SPACE);
        RootPanel.get().add(verticalSpace2);

        //---таблица всех узлов---
        VerticalPanel allNodesPanel = new VerticalPanel();
        Label allNodesTitle = new Label(NAME_OF_ALL_NODES_PANEL);
        
        allNodesTitle.addStyleName(STYLE_PANEL_TITLE);

        allNodesPanel.add(allNodesTitle);
        allNodesPanel.add(allNodesView);

        RootPanel.get().add(allNodesPanel);
    }
}
