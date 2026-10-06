package com.kristina.gwttreecrud.server.dao;

import java.util.List;

import com.kristina.gwttreecrud.shared.TreeNode;

public interface TreeNodeDao {
    List<TreeNode> findAll();

    TreeNode findById(Long id);

    void deleteById(Long id);

    void updateNode(TreeNode node);

    void insertNode(TreeNode node);

    List<TreeNode> getAllChildById(Long parentId);

    List<TreeNode> getAllRoots();
}
