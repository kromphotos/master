package com.kristina.gwttreecrud.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.kristina.gwttreecrud.shared.TreeNode;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GwtService extends RemoteService {
    List<TreeNode> getAllNodes() throws IllegalArgumentException;
}
