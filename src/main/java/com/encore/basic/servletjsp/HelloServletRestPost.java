package com.encore.basic.servletjsp;

import com.encore.basic.domain.Hello;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello-servlet-rest-post")
public class HelloServletRestPost extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        BufferedReader br = req.getReader();
//        StringBuilder sb = new StringBuilder();
//        String line = null;
//        while ( (line = br.readLine())!= null){
//            sb.append(line);
//        }

        ObjectMapper objectMapper = new ObjectMapper();
        Hello hello = objectMapper.readValue(req.getReader(), Hello.class);
        System.out.println("hello = " + hello);

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
