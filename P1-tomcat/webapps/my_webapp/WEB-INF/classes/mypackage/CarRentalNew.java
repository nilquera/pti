package mypackage;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.Reader;
import java.io.IOException;

public class CarRentalNew extends HttpServlet {

  int cont = 0;

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
    String nombre = req.getParameter("name");
    String model_vehicle = req.getParameter("model_vehicle");
    String sub_model_vehicle = req.getParameter("sub_model_vehicle");
    String dies_lloguer = req.getParameter("dies_lloguer");
    String num_vehicles = req.getParameter("num_vehicles");
    String descompte = req.getParameter("descompte");

    cont ++;
    out.println("<html><big>Hola Amigo "+ nombre + "</big><br>"+
                cont + " Accesos desde su carga.<br><br>"+
                "Car Model: " + model_vehicle +
                "<br>Engine: " + sub_model_vehicle +
                "<br>Number of days: " + dies_lloguer +
                "<br>Number of units: " + num_vehicles +
                "<br>Discount(%): " + descompte + "</html>");

    JSONObject new_rental = new JSONObject();
    new_rental.put("nombre", nombre);
    new_rental.put("model_vehicle", model_vehicle);
    new_rental.put("sub_model_vehicle", sub_model_vehicle);
    new_rental.put("dies_lloguer", dies_lloguer);
    new_rental.put("num_vehicles", num_vehicles);
    new_rental.put("descompte", descompte);

    JSONParser parser = new JSONParser();

    try (Reader reader = new FileReader("/pti/apache-tomcat-9.0.5/webapps/my_webapp/rental_list.json")){
    	JSONArray rental_list = (JSONArray) parser.parse(reader);
    	rental_list.add(new_rental);

    	try (FileWriter file = new FileWriter("/pti/apache-tomcat-9.0.5/webapps/my_webapp/rental_list.json")) {
            file.write(rental_list.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    } catch (IOException e) {
        e.printStackTrace();
    } catch (ParseException e) {
        e.printStackTrace();
    }



  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    doGet(req, res);
  }
}
