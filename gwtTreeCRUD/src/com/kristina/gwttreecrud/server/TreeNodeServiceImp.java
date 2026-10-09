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
import com.kristina.gwttreecrud.shared.TreeCrudProgramException;
import com.kristina.gwttreecrud.shared.TreeNode;

public class TreeNodeServiceImp implements TreeNodeService {
    private static final String RESOURCE = "mybatis-config.xml";
    
    private static SqlSessionFactory sqlSessionFactory;
    
    static {
        try (InputStream inputStream = Resources.getResourceAsStream(RESOURCE)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private TreeNodeDaoImp getTreeNodeDao(SqlSession session) {
        NodeMapper mapper = session.getMapper(NodeMapper.class);
        return new TreeNodeDaoImp(mapper);
    }

    @Override
    public List<TreeNode> findAll() throws TreeCrudProgramException {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return getTreeNodeDao(session).findAll();
        } catch (PersistenceException e) {
            throw new TreeCrudProgramException("Ошибка при загрузке всех узлов");
        }
    }

    @Override
    public TreeNode findById(Long id) throws TreeCrudProgramException {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return getTreeNodeDao(session).findById(id);
        } catch (PersistenceException e) {
            throw new TreeCrudProgramException("Ошибка при поиске узла по id = " + id);
        }
    }

    @Override
    public void deleteById(Long id) throws TreeCrudProgramException {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            getTreeNodeDao(session).deleteById(id);
            session.commit();
        } catch (PersistenceException e) {
            throw new TreeCrudProgramException("Ошибка при удалении узла id = " + id);
        }
    }

    @Override
    public TreeNode updateNode(TreeNode node) throws TreeCrudProgramException {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            getTreeNodeDao(session).updateNode(node);
            session.commit();
            return node;
        } catch (PersistenceException e) {
            throw new TreeCrudProgramException("Ошибка при обновлении узла");
        }
    }

    @Override
    public TreeNode insertNode(TreeNode node) throws TreeCrudProgramException {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            getTreeNodeDao(session).insertNode(node);
            session.commit();
            return node;
        } catch (PersistenceException e) {
            throw new TreeCrudProgramException("Ошибка при добавлении узла");
        }
    }

    @Override
    public List<TreeNode> getAllChildById(Long parentId) throws TreeCrudProgramException {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return getTreeNodeDao(session).getAllChildById(parentId);
        } catch (PersistenceException e) {
            throw new TreeCrudProgramException("Ошибка при загрузке детей узла id = " + parentId);
        }
    }
    
    @Override
    public List<TreeNode> getAllRoots() throws TreeCrudProgramException {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return getTreeNodeDao(session).getAllRoots();
        } catch (PersistenceException e) {
            throw new TreeCrudProgramException("Ошибка при загрузке корневых узлов");
        }
    }
}
