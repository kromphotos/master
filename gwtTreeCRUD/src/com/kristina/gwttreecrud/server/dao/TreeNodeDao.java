package com.kristina.gwttreecrud.server.dao;
import java.util.List;

import com.kristina.gwttreecrud.shared.TreeNode;


public interface TreeNodeDao {
    List<TreeNode> findAll();
    TreeNode findById(Integer id);
    void deleteById(Integer id);
    void updateNode(TreeNode node);
    TreeNode insertNode(TreeNode node);
    List<TreeNode> getAllChildById(Integer parentId);
    List<TreeNode> getAllRoots();
}
