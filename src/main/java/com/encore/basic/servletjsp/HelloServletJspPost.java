package com.encore.basic.servletjsp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello-servlet-jsp-post")
public class HelloServletJspPost extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

//      콘솔에 출력
        System.out.println("password = " + password);
        System.out.println("email = " + email);
        System.out.println("name = " + name);

//        응답 header
        resp.setContentType("text/plain");
        resp.setContentType("UTF-8");
//        응답 body
        PrintWriter out = resp.getWriter();
        out.print("ok");

//        버퍼를 통해 조립이 이루어지므로, 버퍼를 비우는 과정.
        out.flush();
    }


}
