package com.urise.webapp.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
      //  String name = request.getParameter("name");
        String tableOutput = ("<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "<link rel=\"stylesheet\" href=\"basejava/web/css/style.css\">\n" +
                "<title>Все резюме</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<section>\n" +
                "<table bgcolor = \"#FFA07A \"  border=\"1\" cellpadding=\"8\" cellspacing=\"0\">\n" +
                "<tr bgcolor = \"#66CDAA \">\n" +
                "<th>№</th>\n" +
                "<th>Имя</th>\n" +
                "</tr>\n" +
                "<tr></tr>\n" +
                "</table>\n" +
                "</section>\n" +
                "</body>\n" +
                "</html>");
       // response.getWriter().write(name == null ? "Hello, here I am! Yours, resumes" : "Hello " + name + ", here I am! Yours, resumes");
        response.getWriter().write(tableOutput);
    }
}
