package com.kristina.gwttreecrud.client.allnodes;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.GwtService;
import com.kristina.gwttreecrud.client.GwtServiceAsync;
import com.kristina.gwttreecrud.client.events.AppEventBus;
import com.kristina.gwttreecrud.client.events.NodeUpdatedEvent;
import com.kristina.gwttreecrud.client.events.NodeUpdatedEventHandler;
import com.kristina.gwttreecrud.client.events.NodesLoadedEvent;
import com.kristina.gwttreecrud.shared.TreeNode;

public class AllNodesPresenter implements NodeUpdatedEventHandler {
    private AllNodesView view;
    private GwtServiceAsync service = GWT.create(GwtService.class);

    public AllNodesPresenter(AllNodesView view) {
        this.view = view;
        AppEventBus.get().addHandler(NodeUpdatedEvent.TYPE, this);
    }

    public List<AllNodesViewData> convertToData(List<TreeNode> nodes) {
        List<AllNodesViewData> newNodes = new ArrayList<>();
        if (nodes != null) {
            for (TreeNode node : nodes) {
                newNodes.add(new AllNodesViewData(node.getId(), node.getParentId(), node.getName(), node.getIp(), node.getPort()));
            }
        }
        return newNodes;
    }

    public void loadNodes() {
        service.getAllNodes(new AsyncCallback<List<TreeNode>>() {
            @Override
            public void onSuccess(List<TreeNode> nodes) {
                refreshNodes(nodes);
                AppEventBus.get().fireEvent(new NodesLoadedEvent(nodes));
            }
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка загрузки данных", caught);
            }
        });
    }

    public void reloadNodes() {
        service.getAllNodes(new AsyncCallback<List<TreeNode>>() {
            @Override
            public void onSuccess(List<TreeNode> nodes) {
                refreshNodes(nodes);
            }
            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка обновления данных", caught);
            }
        });
    }

    public void refreshNodes(List<TreeNode> nodes) {
        view.showNodes(convertToData(nodes));
    }
    
    @Override
    public void onNodeUpdated(NodeUpdatedEvent event) {
        reloadNodes();
    }


}
