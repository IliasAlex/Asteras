/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Asteras;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ηλίας
 */
public class Insert_delete extends HttpServlet {

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
            out.println("<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "    <head>\n"
                    + "        <meta charset=\"utf-8\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n"
                    + "        <title>Bootstrap Table with Add and Delete Row Feature</title>\n"
                    + "        <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Roboto|Varela+Round|Open+Sans\">\n"
                    + "        <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css\">\n"
                    + "        <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/icon?family=Material+Icons\">\n"
                    + "        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css\">\n"
                    + "        <script src=\"https://code.jquery.com/jquery-3.5.1.min.js\"></script>\n"
                    + "        <script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js\"></script>\n"
                    + "        <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js\"></script>\n"
                    + "        <style>\n"
                    + "            body {\n"
                    + "                color: #404E67;\n"
                    + "                background: #F5F7FA;\n"
                    + "                font-family: 'Open Sans', sans-serif;\n"
                    + "            }\n"
                    + "            .table-wrapper {\n"
                    + "                width: 700px;\n"
                    + "                margin: 30px auto;\n"
                    + "                background: #fff;\n"
                    + "                padding: 20px;	\n"
                    + "                box-shadow: 0 1px 1px rgba(0,0,0,.05);\n"
                    + "            }\n"
                    + "            .table-title {\n"
                    + "                padding-bottom: 10px;\n"
                    + "                margin: 0 0 10px;\n"
                    + "            }\n"
                    + "            .table-title h2 {\n"
                    + "                margin: 6px 0 0;\n"
                    + "                font-size: 22px;\n"
                    + "            }\n"
                    + "            .table-title .add-new {\n"
                    + "                float: right;\n"
                    + "                height: 30px;\n"
                    + "                font-weight: bold;\n"
                    + "                font-size: 12px;\n"
                    + "                text-shadow: none;\n"
                    + "                min-width: 100px;\n"
                    + "                border-radius: 50px;\n"
                    + "                line-height: 13px;\n"
                    + "            }\n"
                    + "            .table-title .add-new i {\n"
                    + "                margin-right: 4px;\n"
                    + "            }\n"
                    + "            table.table {\n"
                    + "                table-layout: fixed;\n"
                    + "            }\n"
                    + "            table.table tr th, table.table tr td {\n"
                    + "                border-color: #e9e9e9;\n"
                    + "            }\n"
                    + "            table.table th i {\n"
                    + "                font-size: 13px;\n"
                    + "                margin: 0 5px;\n"
                    + "                cursor: pointer;\n"
                    + "            }\n"
                    + "            table.table th:last-child {\n"
                    + "                width: 100px;\n"
                    + "            }\n"
                    + "            table.table td a {\n"
                    + "                cursor: pointer;\n"
                    + "                display: inline-block;\n"
                    + "                margin: 0 5px;\n"
                    + "                min-width: 24px;\n"
                    + "            }    \n"
                    + "            table.table td a.add {\n"
                    + "                color: #27C46B;\n"
                    + "            }\n"
                    + "            table.table td a.edit {\n"
                    + "                color: #FFC107;\n"
                    + "            }\n"
                    + "            table.table td a.delete {\n"
                    + "                color: #E34724;\n"
                    + "            }\n"
                    + "            table.table td i {\n"
                    + "                font-size: 19px;\n"
                    + "            }\n"
                    + "            table.table td a.add i {\n"
                    + "                font-size: 24px;\n"
                    + "                margin-right: -1px;\n"
                    + "                position: relative;\n"
                    + "                top: 3px;\n"
                    + "            }    \n"
                    + "            table.table .form-control {\n"
                    + "                height: 32px;\n"
                    + "                line-height: 32px;\n"
                    + "                box-shadow: none;\n"
                    + "                border-radius: 2px;\n"
                    + "            }\n"
                    + "            table.table .form-control.error {\n"
                    + "                border-color: #f50000;\n"
                    + "            }\n"
                    + "            table.table td .add {\n"
                    + "                display: none;\n"
                    + "            }\n"
                    + "        </style>\n"
                    + "        <script>\n"
                    + "            $(document).ready(function () {\n"
                    + "                // Delete row on delete button click\n"
                    + "                $(document).on(\"click\", \".delete\", function () {\n"
                    + "                    name = this.id;\n"
                    + "                    $(this).parents(\"tr\").remove();\n"
                    + "                    $(\".add-new\").removeAttr(\"disabled\");\n"
                    + "                    window.location.href = 'http://localhost:8080/Asteras/DeleteAuthor?name=' + name;"
                    + "                });\n"
                    + "            });\n"
                    + "        </script>\n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "        <div class=\"container-lg\">\n"
                    + "            <div class=\"table-responsive\">\n"
                    + "                <div class=\"table-wrapper\">\n"
                    + "                    <div class=\"table-title\">\n"
                    + "                        <div class=\"row\">\n"
                    + "                            <div class=\"col-sm-8\"><h2>Authors <b>Details</b></h2></div>\n"
                    + "\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                    <form action=\"http://localhost:8080/Asteras/DeleteAuthor\">\n" 
                    + "                            <br>\n" 
                    + "                            <label for=\"name\">Delete by Name:</label>\n" 
                    + "                            <input type=\"text\" id=\"name\" name=\"name\"><br>\n" 
                    + "                            <input type=\"submit\" value=\"Delete\"><br><br>\n" 
                    + "                    </form>"
                    + "                    <table class=\"table table-bordered\">\n"
                    + "                        <thead>\n"
                    + "                            <tr>\n"
                    + "                                <th>Name</th>\n"
                    + "                                <th>Actions</th>\n"
                    + "                            </tr>\n"
                    + "                        </thead>\n"
                    + "                        <tbody>\n"
            );

            File folder = new File(System.getProperty("user.dir")+"/Data");
            if (folder.listFiles() != null) {
                for (final File fileEntry : folder.listFiles()) {
                    if( fileEntry.length() == 0){
                        continue;
                    }
                    String name = fileEntry.getName().replace(".bib", "");
                    out.println("<tr>\n"
                            + "<td>" + name + "</td>\n"
                            + "<td>\n"
                            + "<a class=\"delete\" title=\"Delete\" id = \""+name+"\"data-toggle=\"tooltip\"><i class=\"material-icons\">&#xE872;</i></a>\n"
                            + "</td>\n"
                            + "</tr>    \n");
                }
            }

            out.println("                      </tbody>\n"
                    + "                    </table>\n"
                    + "                    <form method=\"post\" action=\"BibFileUpload\" enctype=\"multipart/form-data\">\n"
                    + "                        <div class=\"col-sm-4\">\n"
                    + "                            <input type=\"file\" id=\"file\" name=\"file\"required>\n"
                    + "                        </div>\n"
                    + "                        <div class=\"col-sm-4\">\n"
                    +" <br>"
                    + "                            <button type=\"submit\" class=\"btn btn-info add-new\">Submit</button>\n"
                    + "                            <a href=\"http://localhost:8080/Asteras/\" class=\"btn btn-primary\">Home</a>"
                    + "                        </div>\n"
                    + "                    </form>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>     \n"
                    + "    </body>\n"
                    + "</html>\n"
            );
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
