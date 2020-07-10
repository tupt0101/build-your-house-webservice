/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupt.test;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.dom.DOMResult;
import org.w3c.dom.NodeList;
import tupt.constants.PathConstant;
import tupt.resolvers.Crawler;
import tupt.resolvers.DataResolver;

/**
 *
 * @author sherl
 */
@WebServlet(name = "TestController", urlPatterns = {"/TestController"})
public class TestController extends HttpServlet {

    private static final String ADMIN = "admin.jsp";
    private static final String ERROR = "error.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {
            String realPath = request.getServletContext().getRealPath("/");
            String xmlPath = realPath + PathConstant.CONFIG_XML;

            int totalDomain = PathConstant.CONFIG_XSL.size();
            
            System.out.println("Start crawling...");
            
            for (int i = 0; i < totalDomain; i++) {
                String xslPathCate = realPath + PathConstant.CONFIG_XSL_CATE.get(i);
                String xslPath = realPath + PathConstant.CONFIG_XSL.get(i);
                DOMResult domResult = Crawler.doCrawlFromSingleSite(xmlPath, xslPathCate);
                
                // Get list category
                NodeList listHref = domResult.getNode().getChildNodes().item(0).getChildNodes();
                
                for (int j = 0; j < listHref.getLength(); j++) {
                    ArrayList<DOMResult> domResults = (ArrayList<DOMResult>) Crawler
                            .doCrawlFromPaginatedSite(xmlPath, xslPath, PathConstant.CONFIG_HREF.get(i), listHref.item(j).getTextContent());
                    
                    DataResolver dataResolver = new DataResolver();
                    for (DOMResult dom : domResults) {
                        dataResolver.saveDomResultToDatabase(dom, realPath);
                    }
                }
            }
            System.out.println("Crawl successfully!");
        } catch (Exception e) {
            e.printStackTrace();
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
