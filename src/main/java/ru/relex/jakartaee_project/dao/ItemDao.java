package ru.relex.jakartaee_project.dao;

import ru.relex.jakartaee_project.entity.Item;
import ru.relex.jakartaee_project.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDao {
    private static final ItemDao instance = new ItemDao();
    private ItemDao() {}
    public static ItemDao getInstance() {
        return instance;
    }

    String save_SQL = """
            insert into items(user_id, category_id , type ,title, description,location,event_date )
            values (?,?,?,?,?,?,?)
            """;
    String delete_SQL = """
            delete from items where id = ?;
            """;
    String findAll_SQL= """
            Select id,user_id,category_id,type,title,description,location,event_date,status,created_at from items 
            """;
    String findById_SQL=findAll_SQL + """
              where id = ?
            """;
    String update_SQL = """
            update items
            set category_id=?,type=?,title=?,description=?,location=?,event_date=?,status=?
            where id=?;
            """;
    public Item save (Item item) {
        try(Connection connection = ConnectionManager.get();
            var statement = connection.prepareStatement(save_SQL,Statement.RETURN_GENERATED_KEYS);
        ){
            statement.setLong(1, item.getUser_id());
            statement.setLong(2, item.getCategory_id());
            statement.setString(3, item.getType());
            statement.setString(4, item.getTitle());
            statement.setString(5, item.getDescription());
            statement.setString(6, item.getLocation());
            statement.setTimestamp(7, Timestamp.valueOf(item.getEvent_date()));

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()) {
                item.setId(rs.getLong("id"));
            }
            return item;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Long save_return_id (Item item) {
        try(Connection connection = ConnectionManager.get();
            var statement = connection.prepareStatement(save_SQL,Statement.RETURN_GENERATED_KEYS);
        ){
            statement.setLong(1, item.getUser_id());
            statement.setLong(2, item.getCategory_id());
            statement.setString(3, item.getType());
            statement.setString(4, item.getTitle());
            statement.setString(5, item.getDescription());
            statement.setString(6, item.getLocation());
            statement.setTimestamp(7, Timestamp.valueOf(item.getEvent_date()));

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()) {
                return rs.getLong("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1L;
    }
    public boolean delete (Long id) {
        try(
                Connection connection = ConnectionManager.get();
                var statement = connection.prepareStatement(delete_SQL);
                ) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Item> findAll() {
        try(
                Connection connection = ConnectionManager.get();
                var statement = connection.prepareStatement(findAll_SQL);
                ) {
            List<Item> items = new ArrayList<>();
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                items.add(buildItem(rs));
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Item findById(Long id) {
        try(
                Connection connection = ConnectionManager.get();
                var statement = connection.prepareStatement(findById_SQL);
                ){
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return buildItem(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private static Item buildItem(ResultSet rs) throws SQLException {
        return new Item(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("category_id"),
                rs.getString("type"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("location"),
                rs.getTimestamp("event_date").toLocalDateTime(),
                rs.getString("status"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
    public boolean update(Item item) {
        try(
                Connection connection = ConnectionManager.get();
                var statement = connection.prepareStatement(update_SQL);
                ){
            statement.setLong(1, item.getCategory_id());
            statement.setString(2, item.getType());
            statement.setString(3, item.getTitle());
            statement.setString(4, item.getDescription());
            statement.setString(5, item.getLocation());
            statement.setTimestamp(6, Timestamp.valueOf(item.getEvent_date()));
            statement.setString(7, item.getStatus());
            statement.setLong(8, item.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
