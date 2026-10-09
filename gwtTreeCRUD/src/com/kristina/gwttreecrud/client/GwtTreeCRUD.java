package com.kristina.gwttreecrud.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
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
    private static final String STYLE_PANEL_TITLE = "panel-title";
    private static final String STYLE_MAIN_CONTAINER = "main-container";
    private static final String STYLE_TREE_CONTAINER = "tree-container";
    private static final String STYLE_NODE_INFO_CONTAINER = "node-info-container";
    private static final String STYLE_LEFT_COLUMN = "left-column";
    private static final String STYLE_BOTTOM_LEFT_CONTAINER = "bottom-left-container";
    private static final String STYLE_ALL_NODES_PANEL = "all-nodes-panel";
    private static final String STYLE_ALL_NODES_HEADER = "all-nodes-header";
    private static final String STYLE_APP_TITLE = "app-title";
    private static final String STYLE_BOTTOM_CONTAINER = "bottom-container";
    
    private static final String NAME_OF_APP = "GWT Flow Table Tree";
    private static final String NAME_OF_ALL_NODES_PANEL = "All nodes:";

    @Override
    public void onModuleLoad() {
        // ---------- Tree ----------
        TreeView treeView = new TreeView();
        new TreePresenter(treeView);

        // ---------- Node Info ----------
        NodeInfoView nodeInfoView = new NodeInfoView();
        new NodeInfoPresenter(nodeInfoView);

        // ---------- Node Add ----------
        NodeAddView nodeAddView = new NodeAddView();
        new NodeAddPresenter(nodeAddView);

        // ---------- Node Actions ----------
        NodeActionsView nodeActionsView = new NodeActionsView();
        new NodeActionsPresenter(nodeActionsView);

        // ---------- All Nodes ----------
        AllNodesView allNodesView = new AllNodesView();
        new AllNodesPresenter(allNodesView);

        // ------ Дерево ------
        FlowPanel treeContainer = new FlowPanel();
        treeContainer.addStyleName(STYLE_TREE_CONTAINER);
        treeContainer.add(treeView);
        
        // ---------- Карточка ----------
        FlowPanel nodeInfoContainer = new FlowPanel();
        nodeInfoContainer.addStyleName(STYLE_NODE_INFO_CONTAINER);
        nodeInfoContainer.add(nodeInfoView);
        
        // ---------- Нижняя часть левой колонки: дерево + карточка ----------
        FlowPanel bottomLeftContainer = new FlowPanel();
        bottomLeftContainer.addStyleName(STYLE_BOTTOM_LEFT_CONTAINER);
        bottomLeftContainer.add(treeContainer);
        bottomLeftContainer.add(nodeInfoContainer);
        
        // ---------- Левая колонка: кнопки + (дерево + карточка) ----------
        FlowPanel leftColumn = new FlowPanel();
        leftColumn.addStyleName(STYLE_LEFT_COLUMN);
        leftColumn.add(nodeActionsView);
        leftColumn.add(bottomLeftContainer);

        // ---------- Правая колонка: таблица ---------
        Label allNodesTitle = new Label(NAME_OF_ALL_NODES_PANEL);
        allNodesTitle.addStyleName(STYLE_PANEL_TITLE);
        
        FlowPanel allNodesHeader = new FlowPanel();
        allNodesHeader.addStyleName(STYLE_ALL_NODES_HEADER);
        allNodesHeader.add(allNodesTitle);
        
        FlowPanel allNodesPanel = new FlowPanel();
        allNodesPanel.addStyleName(STYLE_ALL_NODES_PANEL);
        allNodesPanel.add(allNodesHeader);
        allNodesPanel.add(allNodesView);
        
        // ---------- Контейнер для левой и правой части ----------
        FlowPanel bottomContainer = new FlowPanel();
        bottomContainer.addStyleName(STYLE_BOTTOM_CONTAINER);
        bottomContainer.add(leftColumn);
        bottomContainer.add(allNodesPanel);

        // ---------- Главный контейнер ----------
        FlowPanel mainContainer = new FlowPanel();
        mainContainer.addStyleName(STYLE_MAIN_CONTAINER);

        Label appTitle = new Label(NAME_OF_APP);
        appTitle.addStyleName(STYLE_APP_TITLE);
        mainContainer.add(appTitle);
        mainContainer.add(bottomContainer);

        RootPanel.get().add(mainContainer);
    }
}
