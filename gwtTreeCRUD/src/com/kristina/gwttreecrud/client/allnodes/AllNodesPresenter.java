package com.kristina.gwttreecrud.client.allnodes;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.AppGwtService;
import com.kristina.gwttreecrud.client.events.AppEventBus;
import com.kristina.gwttreecrud.client.events.DeleteNodeEvent;
import com.kristina.gwttreecrud.client.events.DeleteNodeEventHandler;
import com.kristina.gwttreecrud.client.events.NodeAddedEvent;
import com.kristina.gwttreecrud.client.events.NodeAddedEventHandler;
import com.kristina.gwttreecrud.client.events.NodeUpdatedEvent;
import com.kristina.gwttreecrud.client.events.NodeUpdatedEventHandler;
import com.kristina.gwttreecrud.shared.TreeCrudProgramException;
import com.kristina.gwttreecrud.shared.TreeNode;

public class AllNodesPresenter implements NodeUpdatedEventHandler, NodeAddedEventHandler, DeleteNodeEventHandler {
    private static final String ERROR_UNKNOWN = "An unknown error occurred";
    
    private AllNodesInterface view;

    public AllNodesPresenter(AllNodesInterface view) {
        this.view = view;
        AppEventBus.get().addHandler(NodeUpdatedEvent.TYPE, this);
        AppEventBus.get().addHandler(NodeAddedEvent.TYPE, this);
        AppEventBus.get().addHandler(DeleteNodeEvent.TYPE, this);
        loadNodes();
    }

    private void loadNodes() {
        AppGwtService.get().getAllNodes(new AsyncCallback<List<TreeNode>>() {
            @Override
            public void onSuccess(List<TreeNode> nodes) {
                view.showNodes(convertToData(nodes));
            }

            @Override
            public void onFailure(Throwable e) {
                if (e instanceof TreeCrudProgramException) {
                    Window.alert(e.getMessage());
                } else {
                    Window.alert(ERROR_UNKNOWN);
                }
            }
        });
    }

    private List<AllNodesViewData> convertToData(List<TreeNode> nodes) {
        List<AllNodesViewData> newNodes = new ArrayList<>();
        if (nodes != null) {
            for (TreeNode node : nodes) {
                newNodes.add(new AllNodesViewData(node.getId(), node.getParentId(), node.getName(), node.getIp(), node.getPort()));
            }
        }
        return newNodes;
    }

    @Override
    public void onNodeUpdated(NodeUpdatedEvent event) {
        loadNodes();
    }

    @Override
    public void nodeAdded(NodeAddedEvent event) {
        loadNodes();
    }

    @Override
    public void deleteNode(DeleteNodeEvent event) {
        loadNodes();
    }
}
