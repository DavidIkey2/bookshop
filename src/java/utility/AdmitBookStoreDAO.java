package utility;

import java.util.*;
import java.sql.*;
import model.Book;

/**
 * The {@code AdmitBookStoreDAO} class provides data access methods for retrieving book information from a database.
 */
public class AdmitBookStoreDAO {

    private Connection con;

    /**
     * Constructs a new {@code AdmitBookStoreDAO} and establishes a connection to the database.
     */
    public AdmitBookStoreDAO() {
        try {
            // Load the Driver class file
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            System.err.println("Getting Connection!");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/BooksDB",
                    "user1", "password");

            if (con != null) {
                System.err.println("Got Connection!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Retrieves all books from the database.
     *
     * @return a list of all books in the database
     * @throws SQLException if a database access error occurs
     */
    public List<Book> getAllBooks() throws SQLException {
        
        Statement statement = con.createStatement();
        ResultSet rs = null;
        List list = new ArrayList();

        rs = statement.executeQuery("SELECT * FROM USER1.TBooks");
        while (rs.next()) {
            System.out.println("rs has records");
            String isbn = rs.getString(1);
            String title = rs.getString(2);
            String author = rs.getString(4);
            double price = rs.getDouble(3);
            list.add(new Book(isbn, title, author, price));
        }
        return list;
    }
}