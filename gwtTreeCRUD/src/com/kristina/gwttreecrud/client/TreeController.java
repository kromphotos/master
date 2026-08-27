package com.kristina.gwttreecrud.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.allnodes.AllNodesPresenter;
import com.kristina.gwttreecrud.client.nodeactions.NodeActionsPresenter;
import com.kristina.gwttreecrud.client.tree.TreePresenter;
import com.kristina.gwttreecrud.shared.TreeNode;

public class TreeController {
    private TreePresenter treePresenter;
    private AllNodesPresenter allNodesPresenter;
    private NodeActionsPresenter nodeActionsPresenter;

    private GwtServiceAsync service = GWT.create(GwtService.class);
    
    public TreeController(TreePresenter treePresenter, AllNodesPresenter allNodesPresenter,NodeActionsPresenter nodeActionsPresenter) {
        this.treePresenter = treePresenter;
        this.allNodesPresenter = allNodesPresenter;
        this.nodeActionsPresenter = nodeActionsPresenter;
    }
    
    public void refresh() {
        service.getAllNodes(new AsyncCallback<List<TreeNode>>() {
            @Override
            public void onSuccess(List<TreeNode> nodes) {
                treePresenter.refreshNodes(nodes);
                allNodesPresenter.refreshNodes(nodes);
            }
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка обновления данных", caught);
            }
        });
    }
    public void clearSelection() {
        treePresenter.clearSelection();
        nodeActionsPresenter.clearSelection();
    }

}
