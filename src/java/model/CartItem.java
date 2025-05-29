package model;

import java.text.DecimalFormat;
import java.io.*;

/**
 * The {@code CartItem} class represents an item in a shopping cart, which includes a book and its quantity. It implements the {@code Serializable} interface to
 * allow its instances to be serialized.
 */
public class CartItem implements Serializable {

    private Book book;
    private int quantity = 0;
    private double costOfItem = 0.00;

    /**
     * Constructs a new {@code CartItem} with the specified book.
     *
     * @param book the book to be added to the cart
     */
    public CartItem(Book book) {
        this.book = book;
    }

    /**
     * Sets the quantity of the book in the cart by adding the specified amount to the current quantity.
     *
     * @param aQuantity the amount to add to the current quantity
     */
    public void setQuantity(int aQuantity) {
        quantity += aQuantity;
        calculateOrderCost();
    }

    /**
     * Updates the quantity of the book in the cart to the specified amount.
     *
     * @param newQuantity the new quantity of the book
     */
    public void updateQuantity(int newQuantity) {
        quantity = newQuantity;
        calculateOrderCost();
    }

    /**
     * Calculates the total cost of the item based on the quantity and the price of the book.
     */
    private void calculateOrderCost() {
        costOfItem = quantity * book.getPrice();
    }

    /**
     * Returns the total cost of the item formatted as a dollar amount.
     *
     * @return the total cost of the item formatted as a dollar amount
     */
    public String getDollarOrderCost() {
        DecimalFormat cost = new DecimalFormat("0.00");
        return "$" + cost.format(this.costOfItem);
    }

    /**
     * Returns a string representation of the cart item, which includes the book and its quantity.
     *
     * @return a string representation of the cart item
     */
    @Override
    public String toString() {
        return book + "       " + "- Quantity: " + quantity;
    }

    /**
     * Returns the book in the cart item.
     *
     * @return the book in the cart item
     */
    public Book getBook() {
        return book;
    }

    /**
     * Returns the total cost of the item.
     *
     * @return the total cost of the item
     */
    public double getOrderCost() {
        return costOfItem;
    }

    /**
     * Returns the quantity of the book in the cart item.
     *
     * @return the quantity of the book in the cart item
     */
    public int getQuantity() {
        return quantity;
    }
}
