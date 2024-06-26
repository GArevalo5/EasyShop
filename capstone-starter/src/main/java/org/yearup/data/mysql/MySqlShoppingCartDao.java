package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {
    private DataSource dataSource;

    @Autowired
    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
        this.dataSource = dataSource;
    }

    @Override
    public ShoppingCart getByUserId(int userId, ShoppingCartItem shoppingCartItem) {
        try(Connection connection = dataSource.getConnection()){

            String sql = "SELECT * FROM shopping_cart WHERE user_id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, shoppingCartItem.getProductId());
            ResultSet row = statement.executeQuery();

            if(row.next()){
                return getByUserId(userId, shoppingCartItem);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ShoppingCart addProductToCart(int userId, int productId) {
        try(Connection connection = dataSource.getConnection()){
            String sql = """
                    INSERT INTO shopping_cart (user_id, product_id, quantity)
                    VALUES (?, ?, 1)
                    ON DUPLICATE KEY UPDATE quantity = quantity + 1;
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ShoppingCart updateProductInCart(int userId, int productId, int quantity) {
        return null;
    }

    @Override
    public ShoppingCart DeleteCart(int userId)
    {
       try(Connection connection = dataSource.getConnection()){
           String sql = """
                   DELETE FROM shopping_cart
                           WHERE user_id = ?;
                   """;
           PreparedStatement statement = connection.prepareStatement(sql);
              statement.setInt(1, userId);
                statement.executeUpdate();


       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
       return null;
    }

}

