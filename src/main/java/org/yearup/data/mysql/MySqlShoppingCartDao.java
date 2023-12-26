package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {
    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        String query = "SELECT * FROM shopping_cart WHERE user_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int cartUserId = resultSet.getInt("user_id");
                int productId = resultSet.getInt("product_id");
                int quantity = resultSet.getInt("quantity");
                return new ShoppingCart()
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ShoppingCart getAllItems(int userId) {
        List<>
        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement()){

        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ShoppingCart addProduct(int productId) {
        return null;
    }

}

