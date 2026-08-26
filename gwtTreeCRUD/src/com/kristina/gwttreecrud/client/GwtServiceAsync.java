package com.kristina.gwttreecrud.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kristina.gwttreecrud.shared.TreeNode;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GwtServiceAsync {
    void getAllNodes(AsyncCallback<List<TreeNode>> callback)
            throws IllegalArgumentException;
    void findById(Integer id, AsyncCallback<TreeNode> callback)
            throws IllegalArgumentException;
    void updateNode(TreeNode node, AsyncCallback<Void> callback)
            throws IllegalArgumentException;
    void insertNode(TreeNode node, AsyncCallback<Void> callback)
            throws IllegalArgumentException;
    void deleteById(Integer id, AsyncCallback<Void> callback)
            throws IllegalArgumentException; 
}
