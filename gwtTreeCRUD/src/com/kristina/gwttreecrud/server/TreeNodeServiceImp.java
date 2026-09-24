package com.kristina.gwttreecrud.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.kristina.gwttreecrud.server.dao.TreeNodeDaoImp;
import com.kristina.gwttreecrud.server.mapper.NodeMapper;
import com.kristina.gwttreecrud.shared.TreeNode;

public class TreeNodeServiceImp implements TreeNodeService {
    private final static String RESOURCE = "mybatis-config.xml";
    private static SqlSessionFactory sqlSessionFactory;
    static {
        try (InputStream inputStream = Resources.getResourceAsStream(RESOURCE)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TreeNode> findAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            TreeNodeDaoImp dao = new TreeNodeDaoImp(mapper);
            return dao.findAll();
        } catch (PersistenceException e) {
            System.err.println("Ошибка: " + e.getMessage());
            return null;
        }
    }

    @Override
    public TreeNode findById(Integer id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            TreeNodeDaoImp dao = new TreeNodeDaoImp(mapper);
            return dao.findById(id);
        } catch (PersistenceException e) {
            System.err.println("Ошибка: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            TreeNodeDaoImp dao = new TreeNodeDaoImp(mapper);
            dao.deleteById(id);
            session.commit();
        } catch (PersistenceException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public void updateNode(TreeNode node) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            TreeNodeDaoImp dao = new TreeNodeDaoImp(mapper);
            dao.updateNode(node);
            session.commit();
        } catch (PersistenceException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public TreeNode insertNode(TreeNode node) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            TreeNodeDaoImp dao = new TreeNodeDaoImp(mapper);
            dao.insertNode(node);
            session.commit();
            return node;
        } catch (PersistenceException e) {
            System.err.println("Ошибка: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<TreeNode> getAllChildById(Integer parentId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            TreeNodeDaoImp dao = new TreeNodeDaoImp(mapper);
            return dao.getAllChildById(parentId);
        } catch (PersistenceException e) {
            System.err.println("Ошибка: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public List<TreeNode> getAllRoots() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            NodeMapper mapper = session.getMapper(NodeMapper.class);
            TreeNodeDaoImp dao = new TreeNodeDaoImp(mapper);
            return dao.getAllRoots();
        } catch (PersistenceException e) {
            System.err.println("Ошибка: " + e.getMessage());
            return null;
        }
    }

}
