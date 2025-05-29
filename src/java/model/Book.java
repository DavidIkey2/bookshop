package model;

import java.text.DecimalFormat;
import java.io.*;

/**
 * The {@code Book} class represents a book with an ISBN, title, author, and price. It implements the {@code Serializable} interface to allow its instances to
 * be serialized.
 */
public class Book implements Serializable {

    private String isbn = "";
    private String title = "";
    private String author = "";
    private double price = 0.00;

    /**
     * Constructs a new {@code Book} with the specified ISBN, title, author, and price.
     *
     * @param isbn the ISBN of the book
     * @param title the title of the book
     * @param author the author of the book
     * @param price the price of the book
     */
    public Book(String isbn, String title, String author, double price) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.price = price;
    }

    /**
     * Returns the ISBN of the book.
     *
     * @return the ISBN of the book
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Returns the title of the book.
     *
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the author of the book.
     *
     * @return the author of the book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the price of the book.
     *
     * @return the price of the book
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns a string representation of the book, which includes the title.
     *
     * @return a string representation of the book
     */
    @Override
    public String toString() {
        return "Title: " + title + "  ";
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if ((this.isbn == null) ? (other.isbn != null) : !this.isbn.equals(other.isbn)) {
            return false;
        }
        return true;
    }

    /**
     * Returns the price of the book formatted as a dollar amount.
     *
     * @return the price of the book formatted as a dollar amount
     */
    public String getDollarPrice() {
        DecimalFormat priceFormat = new DecimalFormat("0.00");
        return "$" + priceFormat.format(this.price);
    }
}
