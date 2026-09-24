package com.kristina.gwttreecrud.client.allnodes;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.GwtServiceAsync;
import com.kristina.gwttreecrud.client.GwtServiceCreator;
import com.kristina.gwttreecrud.client.events.AppEventBus;
import com.kristina.gwttreecrud.client.events.DeleteNodeEvent;
import com.kristina.gwttreecrud.client.events.DeleteNodeEventHandler;
import com.kristina.gwttreecrud.client.events.NodeAddedEvent;
import com.kristina.gwttreecrud.client.events.NodeAddedEventHandler;
import com.kristina.gwttreecrud.client.events.NodeUpdatedEvent;
import com.kristina.gwttreecrud.client.events.NodeUpdatedEventHandler;
import com.kristina.gwttreecrud.shared.TreeNode;

public class AllNodesPresenter implements NodeUpdatedEventHandler, NodeAddedEventHandler, DeleteNodeEventHandler {
    private AllNodesInterface view;
    private GwtServiceAsync service = GwtServiceCreator.get();

    public AllNodesPresenter(AllNodesInterface view) {
        this.view = view;
        AppEventBus.get().addHandler(NodeUpdatedEvent.TYPE, this);
        AppEventBus.get().addHandler(NodeAddedEvent.TYPE, this);
        AppEventBus.get().addHandler(DeleteNodeEvent.TYPE, this);
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

    
    //TODO(by Tutor)
    // Зачем тут два одинаковый куска кода? Различие только в названии. логика одинаковая, запрос тот же
    
    public void loadNodes() {
        service.getAllNodes(new AsyncCallback<List<TreeNode>>() {
            @Override
            public void onSuccess(List<TreeNode> nodes) {
                refreshNodes(nodes);
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

    //TODO(by Tutor)
    // Избыточно. Три строки ради кода, который уместится в одну.
    // Порядок методов ВАЖЕН. Если я начинаю читать твой метод loadNodes,
    // который вызывает refreshNodes, который вызывает convertToData,
    // то они должны идти друг за другом. Как convertToData оказался в начале класса?
    //
    // При использовании одного loadNodes, остальные методы будут избыточноми. располагать код можно и в onSuccess()
    
    public void refreshNodes(List<TreeNode> nodes) {
        view.showNodes(convertToData(nodes));
    }

    @Override
    public void onNodeUpdated(NodeUpdatedEvent event) {
        reloadNodes();
    }

    @Override
    public void nodeAdded(NodeAddedEvent event) {
        reloadNodes();
    }

    @Override
    public void deleteNode(DeleteNodeEvent event) {
        reloadNodes();
    }
}
