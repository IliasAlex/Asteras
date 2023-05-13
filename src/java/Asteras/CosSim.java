/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Asteras;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ηλίας
 */
@WebServlet(name = "CosSim", urlPatterns = {"/CosSim"})
public class CosSim extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String query = request.getParameter("query");
            query = query.toLowerCase();
            String k = request.getParameter("k");
            if ( k.equals("")){
                k = "5";
            }
            LuceneTester tester = new LuceneTester();

            String[] parts = query.split(" ");

            String result = "";
            ArrayList<Author> scores = null;
            if (parts.length > 1) {
                result += tester.getCosineSimilarity(parts[0], parts[1]);
            } else if (parts.length == 1) {
                scores = tester.getCosineSimilarity(parts[0]);

            }

            out.println("<!DOCTYPE html>\n"
                    + "<!--\n"
                    + "Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license\n"
                    + "Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Html.html to edit this template\n"
                    + "-->\n"
                    + "<html>\n"
                    + "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">\n"
                    + "    <head>\n"
                    + "        <title>CosSim</title>\n"
                    + "        <meta charset=\"UTF-8\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "    </head>\n"
                    + "\n"
                    + "\n"
                    + "    <body>\n"
                    + "        <div class=\"container\">\n"
                    + "            <div class=\"ibox-content\">\n"
                    + "                <br>\n"
                    + "            <a href=\"http://localhost:8080/Asteras/\" class=\"btn btn-primary\">Home</a><br><br>\n"
                    + "                                <label for=\"k_results\">Top k results: </label>\n"
                    + "                                <input type=\"text\" id=\"k_results\" name=\"k_results\" required><br>\n"
                    + "                                <input name =\"submit_btn\" id = \"submit_btn\" type=\"submit\" value=\"Submit\">\n"
                    + "            <br>\n"
                    + "        </div>\n"
                    + "            \n"
                    + "        </div>\n"
                    + "        \n"
                    + "        <table class=\"table\">\n"
                    + "            <thead>\n"
                    + "                <tr>\n"
                    + "                    <th scope=\"col\">#</th>\n"
                    + "                    <th scope=\"col\">Author 1</th>\n"
                    + "                    <th scope=\"col\">Author 2</th>\n"
                    + "                    <th scope=\"col\">Score(cos sim)</th>\n"
                    + "                </tr>\n"
                    + "            </thead>\n"
                    + "            <tbody>");

            if (scores == null) {
                out.println("<tr>\n"
                        + "                    <th scope=\"row\">1</th>\n"
                        + "                    <td>" + parts[0] + "</td>\n"
                        + "                    <td>" + parts[1] + "</td>\n"
                        + "                    <td>" + result + "</td>\n"
                        + "                </tr>");
            } else {
                int i = 1;
                for (Author score : scores) {
                    out.println("<tr>\n"
                            + "                    <th scope=\"row\">" + i + "</th>\n"
                            + "                    <td>" + parts[0] + "</td>\n"
                            + "                    <td>" + score.getName() + "</td>\n"
                            + "                    <td>" + score.getScore() + "</td>\n"
                            + "                </tr>");
                    i++;
                    if (i > Integer.parseInt(k)) {
                        break;
                    }
                }
            }

            out.println("</tbody>\n"
                    + "        </table>\n"
                    + "    </body>\n"
                    + "</html>");

            out.println("<script>\n"
                    + "    function refreshK() {\n"
                    + "        window.location.href = 'http://localhost:8080/Asteras/CosSim?query=" + query + "'" + " +' &k=' + document.getElementById(\"k_results\").value;\n"
                    + "    }\n"
                    + "\n"
                    + "    document.getElementById(\"submit_btn\").addEventListener(\"click\", refreshK);\n"
                    + "\n"
                    + "</script> ");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
