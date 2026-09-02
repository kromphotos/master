package com.kristina.gwttreecrud.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.allnodes.AllNodesPresenter;
import com.kristina.gwttreecrud.client.nodeactions.NodeActionsPresenter;
import com.kristina.gwttreecrud.client.nodeadd.NodeAddPresenter;
import com.kristina.gwttreecrud.client.nodeinfo.NodeInfoPresenter;
import com.kristina.gwttreecrud.client.tree.TreePresenter;
import com.kristina.gwttreecrud.shared.TreeNode;

public class TreeController {
    private TreePresenter treePresenter;
    private AllNodesPresenter allNodesPresenter;
    private NodeActionsPresenter nodeActionsPresenter;
    private NodeInfoPresenter nodeInfoPresenter;
    private NodeAddPresenter nodeAddPresenter;

    private TreeNode selectedNode;

    private GwtServiceAsync service = GWT.create(GwtService.class);

    public TreeController(TreePresenter treePresenter, AllNodesPresenter allNodesPresenter, NodeActionsPresenter nodeActionsPresenter,
            NodeInfoPresenter nodeInfoPresenter,
            NodeAddPresenter nodeAddPresenter) {
        this.treePresenter = treePresenter;
        this.allNodesPresenter = allNodesPresenter;
        this.nodeActionsPresenter = nodeActionsPresenter;
        this.nodeInfoPresenter = nodeInfoPresenter;
        this.nodeAddPresenter = nodeAddPresenter;
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

    //рассылка всем актуального состояния выбранной ноды
    public void selectNode(TreeNode node) {
        selectedNode = node;

        nodeActionsPresenter.selectNode(node);
        nodeInfoPresenter.selectNode(node);
    }

    public void editNode() {
        nodeInfoPresenter.startEdit();
    }

    public void addChildNode() {
        if (selectedNode == null) {
            return;
        }
        nodeAddPresenter.startAddChild(selectedNode.getId());
    }

    public void addRootNode() {
        nodeAddPresenter.startAddingRoot();
    }

    public void deleteNode() {
        if (selectedNode == null) {
            return;
        }

        service.deleteById(selectedNode.getId(), new AsyncCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                clearSelection();
                refresh();
            }

            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка удаления узла", caught);
            }
        });
    }

    public void clearSelection() {
        selectedNode = null;

        treePresenter.clearSelection();
        nodeActionsPresenter.clearSelection();
        nodeInfoPresenter.clear();
    }

}
