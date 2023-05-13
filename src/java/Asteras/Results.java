package Asteras;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.lucene.queryparser.classic.ParseException;

/**
 *
 * @author Ηλίας
 */
public class Results extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        ArrayList<Author> results = new ArrayList<>();

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            String query = request.getParameter("query");
            String k = request.getParameter("k");
            System.out.println("K: " + k);
            LuceneTester tester = new LuceneTester();

            tester.run(query, k);
            for (String i : tester.matches.keySet()) {
                results.add(tester.matches.get(i));
            }
            Collections.sort(results);
            out.println("<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "    <head>\n"
                    + "        <meta charset=\"utf-8\">\n"
                    + "        <!--  This file has been downloaded from bootdey.com @bootdey on twitter -->\n"
                    + "        <!--  All snippets are MIT license http://bootdey.com/license -->\n"
                    + "        <title>Search Results</title>\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                    + "        <script src=\"https://code.jquery.com/jquery-1.10.2.min.js\"></script>\n"
                    + "        <link href=\"https://netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css\" rel=\"stylesheet\">\n"
                    + "        <script src=\"https://netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js\"></script>\n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "        <div class=\"container bootstrap snippets bootdey\">\n"
                    + "            <div class=\"row\">\n"
                    + "                <div class=\"col-lg-12\">\n"
                    + "                    <div class=\"ibox float-e-margins\">\n"
                    + "                        <div class=\"ibox-content\">\n"
                    + "                            <a href=\"http://localhost:8080/Asteras/\" class=\"btn btn-primary\">Home</a><br><br>\n"
                    + "                                <label for=\"k_results\">Top k results: </label>\n"
                    + "                                <input type=\"text\" id=\"k_results\" name=\"k_results\" required><br>\n"
                    + "                                <input name =\"submit_btn\" id = \"submit_btn\" type=\"submit\" value=\"Submit\">\n");

            out.println("<h2>\n"
                    + results.size() + " results found for: <span class=\"text-navy\">" + query + "</span>\n"
                    + "   </h2>\n"
                    + "   <small>Request time  ( " + tester.time / 10.0 + " Seconds)</small>");

            for (Author i : results) {
                out.println("                            <div class=\"hr-line-dashed\"></div>\n"
                        + "                            <div class=\"search-result\">\n"
                        + "                                <h3><a href=\"#\">" + i.getName() + " score: " + i.getScore() + "</a></h3>\n"
                        + "                                <a href=\"https://dblp.org/search?q=" + i.getName() + "\"" + "class=\"search-link\">https://dblp.org/search?q=" + i.getName() + "</a>\n"
                        + "                                <p>\n"
                        + i.getField() + "\n"
                        + "                                </p>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"hr-line-dashed\"></div>");
            }

            out.println("</div>\n"
                    + "                    </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "\n"
                    + "\n"
                    + "        <style type=\"text/css\">\n"
                    + "            body{\n"
                    + "                background:#eee;\n"
                    + "            }\n"
                    + "            .ibox-content {\n"
                    + "                background-color: #FFFFFF;\n"
                    + "                color: inherit;\n"
                    + "                padding: 15px 20px 20px 20px;\n"
                    + "                border-color: #E7EAEC;\n"
                    + "                border-image: none;\n"
                    + "                border-style: solid solid none;\n"
                    + "                border-width: 1px 0px;\n"
                    + "            }\n"
                    + "\n"
                    + "            .search-form {\n"
                    + "                margin-top: 10px;\n"
                    + "            }\n"
                    + "\n"
                    + "            .search-result h3 {\n"
                    + "                margin-bottom: 0;\n"
                    + "                color: #1E0FBE;\n"
                    + "            }\n"
                    + "\n"
                    + "            .search-result .search-link {\n"
                    + "                color: #006621;\n"
                    + "            }\n"
                    + "\n"
                    + "            .search-result p {\n"
                    + "                font-size: 12px;\n"
                    + "                margin-top: 5px;\n"
                    + "            }\n"
                    + "\n"
                    + "            .hr-line-dashed {\n"
                    + "                border-top: 1px dashed #E7EAEC;\n"
                    + "                color: #ffffff;\n"
                    + "                background-color: #ffffff;\n"
                    + "                height: 1px;\n"
                    + "                margin: 20px 0;\n"
                    + "            }\n"
                    + "\n"
                    + "            h2 {\n"
                    + "                font-size: 24px;\n"
                    + "                font-weight: 100;\n"
                    + "            }\n"
                    + "\n"
                    + "\n"
                    + "        </style>\n"
                    + "\n"
                    + "        <script type=\"text/javascript\">\n"
                    + "\n"
                    + "\n"
                    + "        </script>\n"
                    + "    </body>\n"
                    + "</html>");

            out.println("<script>\n"
                    + "    function refreshK() {\n"
                    + "        window.location.href = 'http://localhost:8080/Asteras/Results?query=" + query + "'"+ " +' &k=' + document.getElementById(\"k_results\").value;\n"
                    + "    }\n"
                    + "\n"
                    + "    document.getElementById(\"submit_btn\").addEventListener(\"click\", refreshK);\n"
                    + "\n"
                    + "</script> ");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(Results.class.getName()).log(Level.SEVERE, null, ex);
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
