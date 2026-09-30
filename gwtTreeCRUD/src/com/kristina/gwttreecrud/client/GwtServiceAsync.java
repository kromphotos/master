package com.kristina.gwttreecrud.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.shared.TreeNode;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GwtServiceAsync {
    void getAllNodes(AsyncCallback<List<TreeNode>> callback);

    void findById(Integer id, AsyncCallback<TreeNode> callback);

    void updateNode(TreeNode node, AsyncCallback<TreeNode> callback);

    void insertNode(TreeNode node, AsyncCallback<TreeNode> callback);

    void deleteById(Integer id, AsyncCallback<Void> callback);

    void getAllChildById(Integer parentId, AsyncCallback<List<TreeNode>> callback);

    void getAllRoots(AsyncCallback<List<TreeNode>> callback);
}
