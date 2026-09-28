SELECT * FROM tree_node WHERE id = :id;

SELECT * FROM tree_node;

DELETE FROM tree_node WHERE id = :id;

UPDATE tree_node
SET name = :name, ip = :ip, port = :port
WHERE id = :id;

INSERT INTO tree_node(parent_id,name,ip,port) VALUES (:parent_id, :name, :ip, :port);