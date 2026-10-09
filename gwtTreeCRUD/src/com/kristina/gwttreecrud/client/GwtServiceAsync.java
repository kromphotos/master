package com.kristina.gwttreecrud.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.shared.TreeNode;

public interface GwtServiceAsync {
    void getAllNodes(AsyncCallback<List<TreeNode>> callback);

    void findById(Long id, AsyncCallback<TreeNode> callback);

    void updateNode(TreeNode node, AsyncCallback<TreeNode> callback);

    void insertNode(TreeNode node, AsyncCallback<TreeNode> callback);

    void deleteById(Long id, AsyncCallback<Void> callback);

    void getAllChildById(Long parentId, AsyncCallback<List<TreeNode>> callback);

    void getAllRoots(AsyncCallback<List<TreeNode>> callback);
}
