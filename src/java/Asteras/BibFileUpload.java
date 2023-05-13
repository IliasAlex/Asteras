/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Asteras;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.BibTeXParser;
import org.jbibtex.ParseException;

/**
 *
 * @author Ηλίας
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class BibFileUpload extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BibFileUpload</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("</body>");
            out.println("</html>");
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
        /* Receive file uploaded to the Servlet from the HTML5 form */
        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        for (Part part : request.getParts()) {
            part.write(fileName);
        }
        File source = new File(getServletContext().getAttribute(ServletContext.TEMPDIR) + "/" + fileName);
        File dest = new File(System.getProperty("user.dir") + "/dit-uop-professors/" + fileName);

        try {
            ZipFile zipFile = new ZipFile(source);
            extractFolder(source.getAbsolutePath(), System.getProperty("user.dir") + "/dit-uop-professors/");
            
            File zipF = new File(System.getProperty("user.dir") + "/dit-uop-professors/" + fileName);
            zipF.delete();

            File destFolder = new File(System.getProperty("user.dir") + "/dit-uop-professors/");
            for (final File fileEntry : destFolder.listFiles()) {
                if (fileEntry.isFile()) {
                    parse(fileEntry.getName());
                }
            }
        } catch (ZipException zipCurrupted) {
            copyFileUsingStream(source, dest);
            parse(fileName);
        }

        response.sendRedirect("http://localhost:8080/Asteras/Insert_delete");
    }

    private void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    private void extractFolder(String zipFile, String extractFolder) throws IOException {
        int BUFFER = 2048;
        File file = new File(zipFile);

        ZipFile zip = new ZipFile(file);
        String newPath = extractFolder;

        new File(newPath).mkdir();
        Enumeration zipFileEntries = zip.entries();

        // Process each entry
        while (zipFileEntries.hasMoreElements()) {
            // grab a zip file entry
            ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
            String currentEntry = entry.getName();

            File destFile = new File(newPath, currentEntry);
            //destFile = new File(newPath, destFile.getName());
            File destinationParent = destFile.getParentFile();

            // create the parent directory structure if needed
            destinationParent.mkdirs();

            if (!entry.isDirectory()) {
                BufferedInputStream is = new BufferedInputStream(zip
                        .getInputStream(entry));
                int currentByte;
                // establish buffer for writing file
                byte data[] = new byte[BUFFER];

                // write the current file to disk
                FileOutputStream fos = new FileOutputStream(destFile);
                BufferedOutputStream dest = new BufferedOutputStream(fos,
                        BUFFER);

                // read and write until last byte is encountered
                while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                    dest.write(data, 0, currentByte);
                }
                dest.flush();
                dest.close();
                is.close();
            }

        }

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

    public void parse(String filename) throws IOException {
        File f = new File("Data/" + filename);
        if (f.exists() && !f.isDirectory()) {
            f.delete();
        }
        try {
            File myObj = new File("dit-uop-professors/" + filename);
            FileReader myReader = new FileReader(myObj);

            BibTeXParser bibtexParser = new BibTeXParser();
            BibTeXDatabase bib_data = bibtexParser.parse(myReader);

            Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> entryMap = bib_data.getEntries();

            Collection<org.jbibtex.BibTeXEntry> entries = entryMap.values();

            FileWriter myWriter = new FileWriter(f);

            for (org.jbibtex.BibTeXEntry entry : entries) {
                org.jbibtex.Value author = entry.getField(org.jbibtex.BibTeXEntry.KEY_AUTHOR);
                org.jbibtex.Value editor = entry.getField(org.jbibtex.BibTeXEntry.KEY_EDITOR);
                org.jbibtex.Value title = entry.getField(org.jbibtex.BibTeXEntry.KEY_TITLE);
                org.jbibtex.Value booktitle = entry.getField(org.jbibtex.BibTeXEntry.KEY_BOOKTITLE);
                org.jbibtex.Value journal = entry.getField(org.jbibtex.BibTeXEntry.KEY_JOURNAL);
                
                if (title == null) {
                    continue;
                }
                String fname = filename.replace(".bib", "");
                if( author != null && !author.toUserString().toLowerCase().contains(fname)){
                    continue;
                }
                if( editor != null && !editor.toUserString().toLowerCase().contains(fname)){
                    continue;
                }
                
                String title_str = title.toUserString().replaceAll("[\n\r]", " ");
                
                
                myWriter.write("title: " + title_str + "\n");
                if (booktitle == null && journal != null) {
                    String journal_str = journal.toUserString().replaceAll("[\n\r]", " ");
                    myWriter.write("journal: " + journal_str + "\n");
                } else if (journal == null && booktitle != null) {
                    String booktitle_str = booktitle.toUserString().replaceAll("[\n\r]", " ");
                    myWriter.write("booktitle: " + booktitle_str + "\n");
                }
            }
            myWriter.close();
            myReader.close();
        } catch (ParseException e) {
            System.err.println(e);
        }
    }
}
