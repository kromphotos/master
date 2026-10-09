package com.kristina.gwttreecrud.client.nodeactions;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.client.AppGwtService;
import com.kristina.gwttreecrud.client.events.AddChildNodeEvent;
import com.kristina.gwttreecrud.client.events.AddRootNodeEvent;
import com.kristina.gwttreecrud.client.events.AppEventBus;
import com.kristina.gwttreecrud.client.events.ClearSelectionEvent;
import com.kristina.gwttreecrud.client.events.ClearSelectionEventHandler;
import com.kristina.gwttreecrud.client.events.DeleteNodeEvent;
import com.kristina.gwttreecrud.client.events.EditNodeEvent;
import com.kristina.gwttreecrud.client.events.NodeSelectedEvent;
import com.kristina.gwttreecrud.client.events.NodeSelectedEventHandler;
import com.kristina.gwttreecrud.client.nodeactions.NodeActionsInterface.NodeActionsViewHandler;
import com.kristina.gwttreecrud.shared.TreeCrudProgramException;
import com.kristina.gwttreecrud.shared.TreeNode;

public class NodeActionsPresenter implements NodeSelectedEventHandler, ClearSelectionEventHandler {
    private static final String ERROR_UNKNOWN = "An unknown error occurred";
    private static final String WINDOW_MESSAGE = "Are you sure you want to delete?";

    private NodeActionsView view;
    private TreeNode selectedNode;

    public NodeActionsPresenter(NodeActionsView view) {
        this.view = view;

        view.setHandler(new NodeActionsViewHandler() {
            @Override
            public void onEdit() {
                if (selectedNode != null) {
                    AppEventBus.get().fireEvent(new EditNodeEvent());
                }
            }

            @Override
            public void onDelete() {
                if (selectedNode == null) {
                    return;
                }

                if (!Window.confirm(WINDOW_MESSAGE)) {
                    return;
                }

                final Long nodeId = selectedNode.getId();

                AppGwtService.get().deleteById(nodeId, new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        AppEventBus.get().fireEvent(new DeleteNodeEvent(nodeId));
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

            @Override
            public void onChild() {
                if (selectedNode == null) {
                    return;
                }
                AppEventBus.get().fireEvent(new AddChildNodeEvent(selectedNode.getId()));
            }

            @Override
            public void onAddRoot() {
                AppEventBus.get().fireEvent(new AddRootNodeEvent());
            }
        });

        AppEventBus.get().addHandler(NodeSelectedEvent.TYPE, this);
        AppEventBus.get().addHandler(ClearSelectionEvent.TYPE, this);
    }

    @Override
    public void onNodeSelected(NodeSelectedEvent event) {
        TreeNode node = event.getNode();
        selectedNode = node;
        view.setNodeSelected(node != null);
    }

    @Override
    public void clearSelection(ClearSelectionEvent event) {
        selectedNode = null;
        view.setNodeSelected(false);
    }
}
