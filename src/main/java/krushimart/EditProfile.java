package krushimart;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/editprofile")
public class EditProfile extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		String email = req.getParameter("email");
		Long phone = Long.parseLong(req.getParameter("phone"));
		String address = req.getParameter("address");
		
		
		HttpSession session = req.getSession();
		String email1 = (String)session.getAttribute("email");
		String role = (String) session.getAttribute("role");
		User user = new User();
		
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setAddress(address);
		user.setEmail(email);
		user.setPhone(phone);
		user.setRole(role);
		
        UserCrud userCrud = new UserCrud();
		PrintWriter printWriter = resp.getWriter();
		RequestDispatcher rDispatcher ;
		
		try {
			int result = userCrud.updateUser(user, email1);
			if (result!=0) {
				
				session.setAttribute("email", email);
				if (user.getRole().equalsIgnoreCase("farmer")) {
					
					printWriter.print("<h1>Profile Updated Successfully</h1>");
					rDispatcher = req.getRequestDispatcher("FarmerProfile.jsp");
					rDispatcher.include(req, resp);
				} else if (user.getRole().equalsIgnoreCase("buyer")) {
					printWriter.print("<h1>Profile Updated Successfully</h1>");
					rDispatcher = req.getRequestDispatcher("BuyerProfile.jsp");
					rDispatcher.include(req, resp);
				} 
			
			} else {
				printWriter.print("<h1>Something went wrong</h1>");
				
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		

		
		
	}
	
}
