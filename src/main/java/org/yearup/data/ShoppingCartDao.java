package org.yearup.data;

import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    // add additional method signatures here
    ShoppingCart getAllItems(int userId);
    ShoppingCart addProduct(int productId);
    void update(int productId, ShoppingCartItem product);
    void delete(int productId);
}
