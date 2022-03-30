package controller;

import model.Product;
import service.ProductService;
import service.ProductServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = "/products")
public class ProductServlet extends HttpServlet {

    private ProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          String action = request.getParameter("action");
          if (action == null){
              action = "";
          }
          switch (action){
              case "create":
                  showCreateForm(request, response);
                  break;
              case "edit":
                  showEditForm(request, response);
                  break;
              case "delete":
                  showDeleteForm(request,response);
                  break;
              case "view":
                  viewProduct(request,response);
                  break;
              default:
                  listProducts(request,response);
                  break;
          }
    }

    private void listProducts(HttpServletRequest request,HttpServletResponse response){
        List<Product> products = this.productService.findAll();
        request.setAttribute("products",products);

        RequestDispatcher dispatcher = request.getRequestDispatcher("product/list.jsp");
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   private void showCreateForm(HttpServletRequest request,HttpServletResponse response){
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/create.jsp");
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
   }

     private void showEditForm(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = this.productService.findById(id);
        RequestDispatcher dispatcher;
        if (product == null){
            dispatcher = request.getRequestDispatcher("error-404.jsp");
        }
        else {
            request.setAttribute("product",product);
            dispatcher=request.getRequestDispatcher("product/edit.jsp");
        }
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
     }

     private void  showDeleteForm(HttpServletRequest request, HttpServletResponse response){
         int id = Integer.parseInt(request.getParameter("id"));
         Product product = this.productService.findById(id);
         RequestDispatcher dispatcher;
         if (product == null){
             dispatcher = request.getRequestDispatcher("error-404.jsp");
         }
         else {
             request.setAttribute("product",product);
             dispatcher=request.getRequestDispatcher("product/delete.jsp");
         }
         try {
             dispatcher.forward(request,response);
         } catch (ServletException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

      private void viewProduct(HttpServletRequest request,HttpServletResponse response){
          int id = Integer.parseInt(request.getParameter("id"));
          Product product = this.productService.findById(id);
          RequestDispatcher dispatcher;
          if (product == null){
              dispatcher = request.getRequestDispatcher("error-404.jsp");
          }
          else {
              request.setAttribute("product",product);
              dispatcher=request.getRequestDispatcher("product/view.jsp");
          }
          try {
              dispatcher.forward(request,response);
          } catch (ServletException e) {
              e.printStackTrace();
          } catch (IOException e) {
              e.printStackTrace();
          }
      }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null){
            action = "";
        }
        switch (action){
            case "create":
                createProduct(request, response);
                break;
            case "edit":
                updateCustomer(request, response);
                break;
            case "delete":
                deleteCustomer(request,response);
                break;
            default:
                break;
        }
    }

    private void createProduct(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        int price =  Integer.parseInt(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int id = (int) (Math.random()*1000);
        boolean status = Boolean.parseBoolean(request.getParameter("status"));

        Product product = new Product(id,name,price,quantity,status);
        this.productService.save(product);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/create.jsp");
        request.setAttribute("message","New product was created");
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCustomer(HttpServletRequest request,HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        Product product = this.productService.findById(id);
        RequestDispatcher dispatcher;
        if (product==null){
            dispatcher = request.getRequestDispatcher("error-404.jsp");
        }
        else {
            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);

            this.productService.update(id,product);
            request.setAttribute("product",product);
            request.setAttribute("message","Product information was updated");
            dispatcher = request.getRequestDispatcher("product/edit.jsp");
        }
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = this.productService.findById(id);
        RequestDispatcher dispatcher;
        if(product == null){
            dispatcher = request.getRequestDispatcher("error-404.jsp");
        } else {
            this.productService.remove(id);
            try {
                response.sendRedirect("/products");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
