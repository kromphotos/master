package com.kristina.treecrud;

import java.util.List;

import com.kristina.treecrud.bean.TreeNode;
import com.kristina.treecrud.service.TreeNodeServiceImp;

public class Main {

    public static void main(String[] args) {
        
        TreeNodeServiceImp service = new TreeNodeServiceImp();
        
        System.out.println("Test findAll(): ");
        List<TreeNode> nodes = service.findAll();
        for (TreeNode node: nodes) {
            System.out.println(node);
        }
        
        System.out.println("Test findById(): ");
        System.out.println(service.findById(100));
        
        System.out.println("Test insertNode(): ");
        TreeNode node2 = new TreeNode();
        node2.setParentId(2);
        node2.setIp("2.22.22.2");
        node2.setName("childNode7");
        node2.setPort(2277);
        service.insertNode(node2);
        nodes = service.findAll();
        for (TreeNode node: nodes) {
            System.out.println(node);
        }
        
        System.out.println("Test deleteById():");
        service.deleteById(node2.getId());
        nodes = service.findAll();
        for (TreeNode node: nodes) {
            System.out.println(node);
        }
        
        System.out.println("Test updateNode():");
        TreeNode node3 = new TreeNode();
        node3.setId(25);
        node3.setParentId(3);
        node3.setIp("3.32.32.3");
        node3.setName("childNode8");
        node3.setPort(1234);
        service.updateNode(node3);
    }

}
