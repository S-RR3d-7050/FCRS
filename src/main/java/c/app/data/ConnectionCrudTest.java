package c.app.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.sql.SQLDataException;


public class ConnectionCrudTest {


    public Connection connect(){
        Connection con = null;

        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database/FCR.db");
            // verify by text + verify INSERT;
            if (con != null){
                System.out.println("Connected");
            }
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return con;
    }

    /*
    public Client LogIN(String email,String pass ) {

        Connection con = connect();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        // SQL SELECT
        //String email = "rtx@a.com";
        //String pass = "1234567";
        try {
            String sql = "SELECT * FROM client WHERE Email = ? AND Pass = ?";


            stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, pass);

            rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("Num");
                String Nom = rs.getString("Nom");
                String Prenom = rs.getString("Prenom");
                String Email = rs.getString("Email");
                String Pass = rs.getString("Pass");
                //String Nom = rs.getString("Nom");
                // Client T = new Client(id,Nom,Prenom,Email,Pass);
                // System.out.println("ID : "+id+" Nom : "+Nom+" Prenom : "+Prenom+" Email : "+Email+" Pass : "+Pass);
                return new Client(id,Nom,Prenom,Email,Pass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

     */

    public void register(String Nom, String Prenom, String Email , String Pass){
        Connection con = connect();
        PreparedStatement stmt = null;


        try {
            String sql = "INSERT INTO client(Nom,Prenom,Email,Pass)"+"VALUES (?,?,?,?)";


            stmt = con.prepareStatement(sql);

            // REPLACE THINGS IN THE INSERT REQ
            stmt.setString(1,Nom);
            stmt.setString(2,Prenom);
            stmt.setString(3,Email);
            stmt.setString(4,Pass);

            int x = stmt.executeUpdate();

            // int x = stmt.executeUpdate();
            if (x > 0) {
                System.out.println("INSERT OF " + Nom + " IS DONE !");
            }else {
                System.out.println("ERROR .");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //Client T = new Client(1,"Shedi","Jounta","rtx@a.com","1234567");
        ConnectionCrudTest T = new ConnectionCrudTest();
        /*
        Client X = T.LogIN("rtx@a.com","1234567");
        String s = X.toString();
        System.out.println(s);
         */
        T.register("Ali","zoghlemri","zebez@disc.com","56zkgnie2");
        T.register("Mhadhby","Yassin","lemarid@google.com","CA454e920");


    }

    }







