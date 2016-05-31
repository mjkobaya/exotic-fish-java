
//import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jonathan
 */
public class Products {
    
    public String name;
    public String price;
    public String img;
    public String fish_type;
    public String sci_name;
    
    
    /**
     * takes in product name, and integer to concatenate the image source 
     * for database
     * @param name
     * @param num
     * @return 
     */
    public static String imgSrc(String name, int num) {
        return "img/" + name.toLowerCase().replaceAll("\\s+","") + 
                Integer.toString(num) +".jpg";
        
    }
    
    public static void printHeader(PrintWriter out) throws SQLException{
        out.print("<!DOCTYPE html>");
        out.print("<html>");
        out.print("<head>");
        out.print("<title>Exotic Fish Emporium</title>");
        out.print("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.print("<link rel='stylesheet' href='css/main.css' type='text/css'>");
        out.print("<link href='https://fonts.googleapis.com/css?family=Playfair+Display:400,700' rel='stylesheet' type='text/css'>");
        out.print("</head>");
        out.print("<style type='text/css'>");
        out.print(" html{");
        out.print("background: url('img/altBackground.png');");
        out.print("background-color:black; background-size:100% auto;"
                + "background-repeat:no-repeat;background-attachment: fixed;"
                + "}  tr.spaceUnder1 > th{ padding-bottom:1em;"
                + "} tr.spaceUnder2 > td { padding-bottom:2em;"
                + "} .formatTable { width: 275px; height:275px; } "
                + "#titleP{ color:#c0fff4;} div.home { color:white; "
                + "padding:20px; font-size:500%; text-align: center; } "
                + "img { border-radius: 200px; height:auto;\n" +
"            max-width:100%;\n" +
"            width:245px;\n" +
"            height:245px;\n" +
"            transition: all 0.5s;\n" +
"            -webkit-transition-timing-function: ease-in-out;\n" +
"            transition-timing-function: ease-in-out;\n" +
"            transform-origin: 0.2 0.5;} img:hover \n" +
"        {\n" +
"            transform:scale(1.3,1.3);\n" +
"           \n" +
"            cursor:pointer;\n" +
"        }\n" +
"        #p1 \n" +
"        {\n" +
"            background-color: hsla(0, 0%, 0%, 1);\n" +
"        }\n" +
"        body\n" +
"        {\n" +
"            background-color: hsla(0, 0%, 0%, 0.5);\n" +
"        }\n" +
"       \n" +
"        td\n" +
"        {\n" +
"            font-size:24px;\n" +
"        }\n" +
"        .pricebackground\n" +
"        {\n" +
"            background-color: hsla(0, 0%, 0%, 0.7);\n" +
"        }\n" +
"        </style>\n" 
        + "<body class=\"body\"> \n" +
"            <section id=\"top-navbar\">\n" +
"            <ul>\n" +
"                <li><a href=\"home.html\">Home</a></li>\n" +
"                <li><a href=\"home.html#about\">About Us</a></li>\n" +
"                <li><a href=\"start.java\">Store</a></li>\n" +
"                <li id=\"logo-img-li\"><a href =\"home.html\"><img class =\"rotate\" id=\"logo-img\" src=\"img/fish-logo.png\"/></a></li>\n" +
                 "<li id= 'titleP' class=\"right\"><h1>Fish Forever</h1></li>\n" +
"            </ul>  \n"
        
        );
    }
   public static void printLastFive(PrintWriter out, lastViewed lastV) throws SQLException{
//        Products p = new Products();
//        if(lastV.fiveProducts.size() > 0){
        out.print("<table style=' margin-top:2em;color:white; text-align:center; font-family:Arial,Helvetica, sans-serif; width:100%'>" 
                    + "<tr style='font-size:250% '>");
            out.print("<div id = 'top-navbar' class='right' style='text-align:center; font-family:Arial,Helvetica, sans-serif; width:100%'>"
                         + "Recently Viewed </div> ");
             for (Products i: lastV.fiveProducts) {
                 out.print("<td ><a href=" + "'" + "php/" + i.name  + ".php"+ "'" +"><img src=" + "'" + Products.imgSrc(i.name,1) + "'" + " alt ="
                        + "'" + i.name + "1'" + "/></a></td>");
             }
             out.print("</tr>");
             out.print("<tr class ='spaceUnder2 '>");
              for (Products i: lastV.fiveProducts) {
                  out.print("<td style= 'height:30px'>" + i.fish_type+ 
                          "<br> Price: $" + i.price+ " </td>");  
              }
              out.print("</tr>  </table> ");
         //}
            //      print each item p.name, p.price
            // }
   }
    public static void createTable(PrintWriter out) throws SQLException {
        Connection connection = null;
        ResultSet rs = null;
        try {
            DatabaseConnection dbc = new DatabaseConnection();
            connection = dbc.jonathansConnect();
            Statement stmt = null;

            stmt = connection.createStatement();
            String sql = "SELECT name, fish_type, price, sci_name FROM products";
            rs = stmt.executeQuery(sql);
        } catch(Exception e) {}
                
        ArrayList<Products> saltwater = new ArrayList<Products>();
        ArrayList<Products> freshwater = new ArrayList<Products>();
        ArrayList<Products> coral = new ArrayList<Products>();
        ArrayList<Products> invert = new ArrayList<Products>();
        ArrayList<ArrayList<Products>> list = new ArrayList<ArrayList<Products>>();
        
        list.add(saltwater);
        list.add(freshwater);
        list.add(coral);
        list.add(invert);
        
        
       
        
        
        out.print("</section> <h2 id=\"titleP\">Fish Forever</h2>\n" +
"    \n" +
"</div>\n" +
"<table border=\"0\" style=\"color:white; text-align: center; font-family: Arial, Helvetica, sans-serif; width:100%\">\n" +
"    <tr class=\"spaceUnder1\" style=\"font-size:200%;\">\n" +
"        <th ><strong>Saltwater Fish</strong></th>\n" +
"        <th ><strong> Freshwater Fish</strong></th>\n" +
"        <th ><strong>Coral</strong></th>\n" +
"        <th ><strong>Invertebrate</strong></th>\n" +
"    </tr>");
        int i = 0;
        while(rs.next()) {
            Products p = new Products();
            p.name = rs.getString("name");
            p.price = rs.getString("price");
            p.fish_type = rs.getString("fish_type");
            p.img = Products.imgSrc(p.name, 1);
            p.sci_name = rs.getString("sci_name");
            list.get(i%4).add(p);
            i++;
        }

        // end while loop
                
        for (i = 0; i < 3; i++) {
            
            out.print("<tr style='font-size:150%;'>");
            for (int j = 0 ; j < 4; j++) {
                
                Products fish = list.get(j).get(i);
                out.print("<td>" + fish.name + "</td>");
                
                // print fish 

            }
            out.print("</tr>");
            // close </tr>
            
            out.print("<tr style='font-size:250% '>");
            // <tr style = fontsie 250">

            for (int j = 0 ; j < 4; j++) {

                Products fish = list.get(j).get(i);
                // print fish with <ahref ="">
               out.print("<td ><a href=" + "'" + "php/" + fish.name  + ".php"+ "'" +"><img src=" + "'" + Products.imgSrc(fish.name,1) + "'" + " alt ="
                        + "'" + fish.name + "1'" + "/></a></td>");
            }
            // </tr>
            out.print("</tr>");

            //<tr>
            out.print("<tr class ='spaceUnder2 '>");
            
            for (int j = 0 ; j < 4; j++) {

                Products fish = list.get(j).get(i);
                out.print("<td style= 'height:30px'>" + fish.sci_name + " <br> Price: $"
                        + fish.price+ "</td>");
            }
            out.print("</tr>");
            //</tr>

        }
        out.print("</table> </body>\n" +
"</html>");
        
        connection.close();
    }
    
    
//    public static void test(HttpServletRequest request, HttpServletResponse response)
//         throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try {
//            PrintWriter out = response.getWriter();
////            DatabaseConnection dbc = new DatabaseConnection(request, response);
////            Connection connection = dbc.connect();
////            Statement stmt = null;
////            try {
////                stmt = connection.createStatement();
////                String sql = "SELECT name, fish_type, price FROM products";
////                ResultSet rs = stmt.executeQuery(sql);
////                
//                int i = 0;
//                while(rs.next()){
//                    //Retrieve by column name
//                    String name = rs.getString("name");
//                    String price = rs.getString("price");
//                    String fish_type = rs.getString("fish_type");
//                    String src;
//                    int srcNum;
//                    out.println("<p>Name is: " + name + "</p>");
//                    out.println("<p>Price is: " + price + "</p>");
//                    out.println("<p> Fish type: " + fish_type +"</p>");
//                    
//                    if(i%4 == 0){
//                        src = "pipfish";
//                        out.println(imgSrc("pipefish", 1));
//                    }
//                    out.println(" ");
//                    
//                }
//                
//                connection.close();
//            }
//            catch (SQLException e) {
//                out.println("SQLException thrown");
//            }
//        } catch(Exception e) {}
//        
//    }
}
