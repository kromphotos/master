package com.kristina.gwttreecrud.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.kristina.gwttreecrud.shared.TreeCrudProgramException;
import com.kristina.gwttreecrud.shared.TreeNode;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GwtService extends RemoteService {
    List<TreeNode> getAllNodes() throws TreeCrudProgramException;

    TreeNode findById(Integer id) throws TreeCrudProgramException;

    TreeNode updateNode(TreeNode node) throws TreeCrudProgramException;

    TreeNode insertNode(TreeNode node) throws TreeCrudProgramException;

    void deleteById(Integer id) throws TreeCrudProgramException;

    List<TreeNode> getAllChildById(Integer parentId) throws TreeCrudProgramException;

    List<TreeNode> getAllRoots() throws TreeCrudProgramException;
}
