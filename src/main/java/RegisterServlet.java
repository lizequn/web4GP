import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import uk.ac.ncl.cs.group1.clientapi.*;
import uk.ac.ncl.cs.group1.clientapi.core.KeyPairStore;
import uk.ac.ncl.cs.group1.clientapi.core.RegisterImpl;

/**
 * @Auther: Li Zequn
 * Date: 25/03/14
 */
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String toAddress = req.getParameter("toAddress");
        System.out.println(email);
        KeyPairStore keyPairStore;
        try{
            keyPairStore = KeyPairStore.getFromFile(email,new File(email+".puk"),new File(email+".pik"));
        } catch (IllegalArgumentException e){
            Register register = new RegisterImpl();
            keyPairStore = register.register(email);
            keyPairStore.store2File(new File(email),new File(email+".puk"),new File(email+".pik"));
        }
        req.getSession().setAttribute("key",keyPairStore);
        req.getSession().setAttribute("toAddress",toAddress);
        resp.sendRedirect("/uploadfile.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
