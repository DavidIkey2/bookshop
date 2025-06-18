package controller;

import dispatchers.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import model.Book;
import utility.BookDAO;

/**
 * The {@code FrontController} servlet acts as a centralized controller for handling
 * all incoming HTTP requests to the web application. It uses the Command Pattern to
 * dispatch requests to specific {@code Action} handlers based on parameters.
 */
public class FrontController extends HttpServlet {

    /** Maps action names to their corresponding Action implementations. */
    private final HashMap actions = new HashMap();

    /**
     * Initializes the FrontController by dynamically loading action classes
     * defined in the deployment descriptor.
     *
     * @param config the servlet configuration
     * @throws ServletException if action class loading fails
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        Enumeration e = config.getInitParameterNames();
        while (e.hasMoreElements()) {
            String actionName = (String) e.nextElement();
            String className = config.getInitParameter(actionName);
            try {
                Class actionClass = Class.forName(className);
                Action actionInstance = (Action) actionClass.newInstance();
                actions.put(actionName, actionInstance);
            } catch (Exception ex) {
                throw new ServletException("Failed to load action: " + className, ex);
            }
        }
        // actions.put("add_to_cart", new AddToCartAction());
    }

    /**
     * Handles GET requests by delegating to doPost.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.err.println("doGet()");
        doPost(request, response);
    }

    /**
     * Processes HTTP POST requests and dispatches them to the appropriate action
     * or handles book loading if no action is specified.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        //Breakpoint Purpose: See what action was submitted from the form (e.g., add_to_cart, view_cart, etc.)
        String requestedAction = request.getParameter("action");
        HttpSession session = request.getSession();
        BookDAO dao = new BookDAO();
        String nextPage = "";

        if (requestedAction == null) {
            try {
                //Breakpoint Purpose: To Check BookDAO Results (MVC Binding)
                List books = dao.findAll();
                //Breakpoint Purpose: Confirm books are retrieved from the database and added to session:
                session.setAttribute("books", books);
                nextPage = "/jsp/titles.jsp";
            } catch (Exception ex) {
                request.setAttribute("result", ex.getMessage());
                nextPage = "/jsp/error.jsp";
            } finally {
                dispatch(request, response, nextPage);
            }
        } else if (actions.containsKey(requestedAction)) {
            //Breakpoint Purpose: See which Action class is being used.
            Action action = (Action) actions.get(requestedAction);
            //Breakpoint Inspect: Inside AddToCartAction, ViewCartAction, etc., and what they do with request/session.
            nextPage = action.execute(request, response);
            dispatch(request, response, nextPage);
        } else {
            nextPage = "/jsp/error.jsp";
            //Breakpoint Purpose: Confirm error flow and how it gets routed.
            request.setAttribute("result", "Unknown action: " + requestedAction);
            dispatch(request, response, nextPage);
        }
    }

    /**
     * Helper method to find a {@code Book} object from the session-stored list using its ISBN.
     *
     * @param isbn the ISBN of the book to search for
     * @param session the current HTTP session
     * @return the found {@code Book} object or {@code null} if not found
     */
    private Book getBookFromList(String isbn, HttpSession session) {
        List list = (List) session.getAttribute("books");
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Book book = (Book) list.get(i);
                if (isbn.equals(book.getIsbn())) {
                    return book;
                }
            }
        }
        return null;
    }

    /**
     * Dispatches the request to the specified JSP page.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param page the path to the JSP page
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    private void dispatch(HttpServletRequest request, HttpServletResponse response, String page)
            throws ServletException, IOException {
        //Breakpoint Confirms that control is reaching the dispatch logic.
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return servlet description
     */
    public String getServletInfo() {
        return "controller.FrontController Information";
    }
}

//package controller;
//
//import dispatchers.*;
//import javax.servlet.*;
//import javax.servlet.http.*;
//import java.io.*;
//import java.util.*;
//import model.Book;
//import model.CartItem;
//import utility.AdmitBookStoreDAO;
//
///**
// * FrontController class to handle HTTP requests and responses.
// */
//public class FrontController extends HttpServlet {
//
//    private final HashMap actions = new HashMap();
//
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//
//        // Original hard-coded action mappings replaced here:
//        //
//        // actions.put("add_to_cart", new AddToCartAction());
//        // actions.put("checkout", new CheckoutAction());
//        // actions.put("continue", new ContinueShoppingAction());
//        // actions.put("update_cart", new UpdateCartAction());
//        // actions.put("view_cart", new ViewCartAction());
//
//        Enumeration e = config.getInitParameterNames();
//        while (e.hasMoreElements()) {
//            String actionName = (String) e.nextElement();
//            String className = config.getInitParameter(actionName);
//            try {
//                Class actionClass = Class.forName(className);
//                Action actionInstance = (Action) actionClass.newInstance(); // Java 1.5
//                actions.put(actionName, actionInstance);
//            } catch (Exception ex) {
//                throw new ServletException("Failed to load action: " + className, ex);
//            }
//        }
//    }
//
//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.err.println("doGet()");
//        doPost(request, response);
//    }
//
//    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("text/html");
//
//        String requestedAction = request.getParameter("action");
//        HttpSession session = request.getSession();
//        AdmitBookStoreDAO dao = new AdmitBookStoreDAO();
//        String nextPage = "";
//
//        if (requestedAction == null) {
//            List books = null;
//            nextPage = "/jsp/error.jsp";
//            try {
//                books = dao.getAllBooks();
//                session.setAttribute("books", books);
//                nextPage = "/jsp/titles.jsp";
//            } catch (Exception ex) {
//                request.setAttribute("result", ex.getMessage());
//                nextPage = "/jsp/error.jsp";
//            } finally {
//                this.dispatch(request, response, nextPage);
//            }
//        } else if (actions.containsKey(requestedAction)) {
//            Action action = (Action) actions.get(requestedAction);
//            nextPage = action.execute(request, response);
//            this.dispatch(request, response, nextPage);
//        } else {
//            nextPage = "/jsp/error.jsp";
//            request.setAttribute("result", "Unknown action: " + requestedAction);
//            this.dispatch(request, response, nextPage);
//        }
//    }
//
//    private Book getBookFromList(String isbn, HttpSession session) {
//        List list = (List) session.getAttribute("books");
//        if (list != null) {
//            for (int i = 0; i < list.size(); i++) {
//                Book book = (Book) list.get(i);
//                if (isbn.equals(book.getIsbn())) {
//                    return book;
//                }
//            }
//        }
//        return null;
//    }
//
//    private void dispatch(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
//        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
//        dispatcher.forward(request, response);
//    }
//
//    public String getServletInfo() {
//        return "controller.FrontController Information";
//    }
//}
