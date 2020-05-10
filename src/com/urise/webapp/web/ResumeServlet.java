package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class ResumeServlet extends HttpServlet {
    private Storage storage; //  = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        Writer writer = response.getWriter();
        //  String name = request.getParameter("name");
        writer.write("<html>\n" +
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
                "</tr>\n");

        for (Resume resume : storage.getAllSorted()) {
            writer.write(
                    "<tr>\n" +
                            "     <td><a href=\"resume?uuid=" + resume.getUuid() + "\">" + resume.getFullName() + "</a></td>\n" +
                            "     <td>" + resume.getContact(ContactType.MAIL) + "</td>\n" +
                            "</tr>\n");
        }
        writer.write("</table>\n" +
                "</section>\n" +
                "</body>\n" +
                "</html>\n");

       /*             "<tr></tr>\n" +
                "</table>\n" +
                "</section>\n" +
                "</body>\n" +
                "</html>");
       // response.getWriter().write(name == null ? "Hello, here I am! Yours, resumes" : "Hello " + name + ", here I am! Yours, resumes");
        response.getWriter().write(tableOutput);*/
    }
}
