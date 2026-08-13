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
}
