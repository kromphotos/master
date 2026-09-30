package com.kristina.gwttreecrud.server;
import java.util.List;

import com.kristina.gwttreecrud.shared.TreeCrudProgramException;
import com.kristina.gwttreecrud.shared.TreeNode;


public interface TreeNodeService {
    List<TreeNode> findAll() throws TreeCrudProgramException;
    TreeNode findById(Integer id) throws TreeCrudProgramException;
    void deleteById(Integer id) throws TreeCrudProgramException;
    TreeNode updateNode(TreeNode node) throws TreeCrudProgramException;
    TreeNode insertNode(TreeNode node) throws TreeCrudProgramException;
    List<TreeNode> getAllChildById(Integer parentId) throws TreeCrudProgramException;
    List<TreeNode> getAllRoots() throws TreeCrudProgramException;
}
