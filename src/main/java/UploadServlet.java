import java.io.*;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.*;
import uk.ac.ncl.cs.group1.clientapi.DocSender;
import uk.ac.ncl.cs.group1.clientapi.core.DocSenderImpl;
import uk.ac.ncl.cs.group1.clientapi.core.KeyPairStore;

public class UploadServlet extends HttpServlet {

    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 5000 * 1024;
    private int maxMemSize = 400 * 1024;
    private File file ;

    public void init( ){

    }
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, java.io.IOException {
        if(request.getSession().getAttribute("key") == null){
            response.sendRedirect("/error.jsp");
        }
        // Check that we have a file upload request
        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            File file1 = null;
            for (FileItem item : items) {
                if (item.isFormField()) {
                } else {
                    String fieldname = item.getFieldName();
                    String filename = FilenameUtils.getName(item.getName());
                    System.out.println(filename);
                    InputStream filecontent = item.getInputStream();
                    OutputStream outputStream =
                            new FileOutputStream(new File(filename));

                    int read = 0;
                    byte[] bytes = new byte[1024];

                    while ((read = filecontent.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, read);
                    }
                    file1 = new File(filename);
                }
            }
            System.out.println(file1.length());
            KeyPairStore keyPairStore = (KeyPairStore) request.getSession().getAttribute("key");
            String toAddress = (String) request.getSession().getAttribute("toAddress");
            List<UUID> list = new ArrayList<>();
            DocSender sender = new DocSenderImpl(keyPairStore);
            UUID uuid = sender.sendDoc(file1,toAddress,true);
            list.add(uuid);
            request.getSession().setAttribute("uuid",list);
            response.sendRedirect("getreceipt.jsp");
        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        }
    }
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, java.io.IOException {

        throw new ServletException("GET method used with " +
                getClass( ).getName( )+": POST method required.");
    }
}
