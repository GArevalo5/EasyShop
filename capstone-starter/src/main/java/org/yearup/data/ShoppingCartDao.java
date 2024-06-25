package org.yearup.data;

import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId, ShoppingCartItem shoppingCartItem);
    // add additional method signatures here
    ShoppingCart addProductToCart(int userId, int productId);
    ShoppingCart updateProductInCart(int userId, int productId, int quantity);
    ShoppingCart DeleteCart(int userId);
}
