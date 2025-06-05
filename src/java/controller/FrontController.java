package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import model.Book;
import model.CartItem;
import utility.AdmitBookStoreDAO;

/**
 * FrontController class to handle HTTP requests and responses.
 */
public class FrontController extends HttpServlet {

    private final HashMap actions = new HashMap();

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.err.println("doGet()");
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String requestedAction = request.getParameter("action");
        HttpSession session = request.getSession();
        AdmitBookStoreDAO dao = new AdmitBookStoreDAO();
        String nextPage = "";

        if (requestedAction == null) {
            dao = new AdmitBookStoreDAO();
            List<Book> books = null;
            nextPage = "/jsp/error.jsp";
            session = request.getSession();
            try {
                books = dao.getAllBooks();
                session.setAttribute("books", books);
                nextPage = "/jsp/titles.jsp";
            } catch (Exception ex) {
                request.setAttribute("result", ex.getMessage());
                nextPage = "/jsp/error.jsp";
            } finally {
                this.dispatch(request, response, nextPage);
            }
        } else if (requestedAction.equals("add_to_cart")) {
            nextPage = "/jsp/titles.jsp";

            Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");
            String[] selectedBooks = request.getParameterValues("add");

            if (selectedBooks == null || selectedBooks.length == 0) {
                this.dispatch(request, response, nextPage);
                return;
            }

            if (cart == null) {
                cart = new HashMap();
                for (String isbn : selectedBooks) {
                    int quantity = Integer.parseInt(request.getParameter(isbn));
                    Book book = this.getBookFromList(isbn, session);
                    CartItem item = new CartItem(book);
                    item.setQuantity(quantity);
                    cart.put(isbn, item);
                }
                session.setAttribute("cart", cart);
            } else {
                for (String isbn : selectedBooks) {
                    int quantity = Integer.parseInt(request.getParameter(isbn));
                    if (cart.containsKey(isbn)) {
                        CartItem item = cart.get(isbn);
                        item.setQuantity(quantity);
                    } else {
                        Book book = this.getBookFromList(isbn, session);
                        CartItem item = new CartItem(book);
                        item.setQuantity(quantity);
                        cart.put(isbn, item);
                    }
                }
            }

            this.dispatch(request, response, nextPage);

        } else if (requestedAction.equals("checkout")) {
            nextPage = "/jsp/checkout.jsp";
            this.dispatch(request, response, nextPage);

        } else if (requestedAction.equals("continue")) {
            nextPage = "/jsp/titles.jsp";
            this.dispatch(request, response, nextPage);

        } else if (requestedAction.equals("update_cart")) {
            nextPage = "/jsp/cart.jsp";
            Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");
            String[] booksToRemove = request.getParameterValues("remove");

            if (booksToRemove != null) {
                for (String bookToRemove : booksToRemove) {
                    cart.remove(bookToRemove);
                }
            }

            for (Map.Entry<String, CartItem> entry : cart.entrySet()) {
                String isbn = entry.getKey();
                CartItem item = entry.getValue();
                int quantity = Integer.parseInt(request.getParameter(isbn));
                item.updateQuantity(quantity);
            }

            this.dispatch(request, response, nextPage);

        } else if (requestedAction.equals("view_cart")) {
            nextPage = "/jsp/cart.jsp";
            Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");
            if (cart == null) {
                nextPage = "/jsp/titles.jsp";
            }
            this.dispatch(request, response, nextPage);
        }
    }

    private Book getBookFromList(String isbn, HttpSession session) {
        List<Book> list = (List<Book>) session.getAttribute("books");
        for (Book book : list) {
            if (isbn.equals(book.getIsbn())) {
                return book;
            }
        }
        return null;
    }

    private void dispatch(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    public String getServletInfo() {
        return "controller.FrontController Information";
    }
}
