package mypackage;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class CarRentalList extends HttpServlet {

  int cont = 0;

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	String nombre = req.getParameter("userid");
    cont ++;

	JSONParser parser = new JSONParser();
	try (Reader reader = new FileReader("/pti/apache-tomcat-9.0.5/webapps/my_webapp/rental_list.json")) {
		JSONArray rental_list = (JSONArray) parser.parse(reader);

    	out.println("<html><big>Hola Amigo "+ nombre + "</big><br>"+
        cont + " Accesos desde su carga.<br><br>");
        // loop array
        Iterator<JSONObject> iterator = rental_list.iterator();
        while (iterator.hasNext()) {
        	JSONObject obj = iterator.next();
	   		out.println("Car Model: " + obj.get("model_vehicle") +
	        "<br>Engine: " + obj.get("sub_model_vehicle") +
	        "<br>Number of days: " + obj.get("dies_lloguer") +
	        "<br>Number of units: " + obj.get("num_vehicles") +
	        "<br>Discount(%): " + obj.get("descompte") + "<br><br>");
        }
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ParseException e) {
        e.printStackTrace();
    }

    out.println("</html>");
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    doGet(req, res);
  }
}
