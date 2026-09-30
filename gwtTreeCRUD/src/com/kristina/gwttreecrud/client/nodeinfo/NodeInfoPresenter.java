package com.kristina.gwttreecrud.client.nodeinfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.AppGwtService;
import com.kristina.gwttreecrud.client.events.AppEventBus;
import com.kristina.gwttreecrud.client.events.ClearSelectionEvent;
import com.kristina.gwttreecrud.client.events.ClearSelectionEventHandler;
import com.kristina.gwttreecrud.client.events.EditNodeEvent;
import com.kristina.gwttreecrud.client.events.EditNodeEventHandler;
import com.kristina.gwttreecrud.client.events.NodeSelectedEvent;
import com.kristina.gwttreecrud.client.events.NodeSelectedEventHandler;
import com.kristina.gwttreecrud.client.events.NodeUpdatedEvent;
import com.kristina.gwttreecrud.client.nodeinfo.NodeInfoInterface.NodeInfoViewHandler;
import com.kristina.gwttreecrud.shared.TreeNode;

public class NodeInfoPresenter implements NodeSelectedEventHandler, EditNodeEventHandler, ClearSelectionEventHandler {
    //TODO(by Tutor)
    //все там же все теже люди
    private static final String ERROR_PORT_NOT_NUMBER = "Порт должен быть числом!";
    private static final String ERROR_OF_EMPTY_FIELD = "Одно из полей было пустое!";

    private NodeInfoInterface view;
    private TreeNode selectedNode;

    public NodeInfoPresenter(NodeInfoInterface view) {
        this.view = view;

        view.setHandler(new NodeInfoViewHandler() {
            @Override
            public void onSaveNode(String name, String ip, String port) {
                saveNode(name, ip, port);
            }

            @Override
            public void onCancel() {
                if (selectedNode != null) {
                    NodeInfoPresenter.this.view.showNode(new NodeInfoViewData(selectedNode.getId(),
                            selectedNode.getParentId(),
                            selectedNode.getName(),
                            selectedNode.getIp(),
                            selectedNode.getPort()));
                }
            }
        });

        AppEventBus.get().addHandler(NodeSelectedEvent.TYPE, this);
        AppEventBus.get().addHandler(EditNodeEvent.TYPE, this);
        AppEventBus.get().addHandler(ClearSelectionEvent.TYPE, this);
    }

    //TODO(by Tutor)
    // опять публичный
    /*
    private void selectNode(TreeNode node) {
        selectedNode = node;
    
        if (node == null) {
            clear();
            return;
        }
        viewData = new NodeInfoViewData(node.getId(),
                node.getParentId(),
                node.getName(),
                node.getIp(),
                node.getPort());
        
        viewData.setData(
                node.getId(),
                node.getParentId(),
                node.getName(),
                node.getIp(),
                node.getPort());
    
        view.showNode(viewData);
    }
    */

    private void updateNodeInfo(TreeNode node) {
        selectedNode = node;

        if (selectedNode == null) {
            clear();
            return;
        }

        view.showNode(new NodeInfoViewData(node.getId(),
                node.getParentId(),
                node.getName(),
                node.getIp(),
                node.getPort()));
    }

    //TODO(by Tutor)
    // что ж они вообще все публичные то

    private void clear() {
        selectedNode = null;
        view.clear();
    }
    /*
    public void startEdit() {
        if (selectedNode == null) {
            return;
        }
    
        view.showEditMode(viewData);
    }
    */

    //TODO(by Tutor)
    // использутеся один раз зачем ему свой метод
    /*
    public void cancelEdit() {
        if (selectedNode == null) {
            return;
        }
    
        view.showNode(viewData);
    }
    */
    private void saveNode(String name, String ip, String port) {
        //TODO(by Tutor)
        // что т происходит? зачем мы сохраняем указатель?
        //final TreeNode node = selectedNode;
        if (selectedNode == null) {
            return;
        }

        if (name.trim().isEmpty() || ip.trim().isEmpty() || port.trim().isEmpty()) {
            view.showError(ERROR_OF_EMPTY_FIELD);
            return;
        }

        Integer portInt;

        try {
            portInt = Integer.valueOf(port);
        } catch (NumberFormatException e) {
            view.showError(ERROR_PORT_NOT_NUMBER);
            return;
        }

        selectedNode.setName(name);
        selectedNode.setIp(ip);
        selectedNode.setPort(portInt);

        AppGwtService.get().updateNode(selectedNode, new AsyncCallback<TreeNode>() {
            @Override
            public void onSuccess(TreeNode updatedNode) {
                GWT.log("Узел успешно обновлён");
                //TODO(by Tutor)
                // зачем мы второй раз обновляем данные одного и того же объекта?
                // зачем нам вообще нужен этот метод?
                if (selectedNode.getId().equals(updatedNode.getId())) {
                    updateNodeInfo(updatedNode);//обновляет не данные объекта а данные вью!
                }
                AppEventBus.get().fireEvent(new NodeUpdatedEvent(updatedNode));//рассылка обновления
            }

            @Override
            public void onFailure(Throwable caught) {
                GWT.log("Ошибка обновления узла", caught);
            }
        });
    }

    @Override
    public void onNodeSelected(NodeSelectedEvent event) {
        TreeNode node = event.getNode();
        updateNodeInfo(node);
    }

    @Override
    public void editNode(EditNodeEvent event) {
        //TODO(by Tutor)
        // этот метод вновь используетс я один раз и только тут, зачем он отдельно вынесен?
        if (selectedNode == null) {
            return;
        }
        view.showEditMode(new NodeInfoViewData(selectedNode.getId(),
                selectedNode.getParentId(),
                selectedNode.getName(),
                selectedNode.getIp(),
                selectedNode.getPort()));
    }

    @Override
    public void clearSelection(ClearSelectionEvent event) {
        clear();
    }
}
