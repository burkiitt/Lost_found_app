//package ru.relex.jakartaee_project;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.*;
//import jakarta.servlet.annotation.*;
//import ru.relex.jakartaee_project.Test_bag.Bag;
//
//public class HelloServlet extends HttpServlet {
//
//
//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        HttpSession session = request.getSession();
//        String name = request.getParameter("name");
//        Integer price = Integer.parseInt(request.getParameter("price"));
//
//        List<Bag> list = (List<Bag>) session.getAttribute("list");
//
//        if(list == null) {
//            list = new ArrayList<>();
//        }
//
//
//        Bag bag = new Bag();
//        bag.setName(name);
//        bag.setPrice(price);
//
//        list.add(bag);
//        session.setAttribute("list", list);
//        PrintWriter out = response.getWriter();
//        out.println("<html><body>");
//        //out.println("<h1>" + "Hello mister/miss :" +name+" "+surname + "</h1>");
//        out.println("<h1>Hello</h1>");
//        out.println("</body></html>");
//        RequestDispatcher dispatcher = request.getRequestDispatcher("bag.jsp");
//        dispatcher.forward(request,response);
//    }
//}
