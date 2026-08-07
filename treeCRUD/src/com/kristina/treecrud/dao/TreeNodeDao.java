package com.kristina.treecrud.dao;
import java.util.List;

import com.kristina.treecrud.bean.TreeNode;

public interface TreeNodeDao {
    List<TreeNode> findAll();
    TreeNode findById(Integer id);
    void deleteById(Integer id);
    void updateNode(TreeNode node);
    void insertNode(TreeNode node);
}
