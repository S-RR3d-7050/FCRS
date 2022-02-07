package c.app.data;

import M.Client;
import M.Compte;

import java.sql.*;

public class CustomerDbUtil {

    private Compte compte;

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    // this method definitely works
    public Connection connect(){
        Connection con = null;

        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database/FCR.db");


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    // this method definitely works
    public Client logIn(String email, String password) {

        Connection con=connect();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // create sql query - ERROR WHEN USING '?'
            //String sql = "SELECT Nom,Prenom,Email,Pass,C.NumC,Solde FROM client Cl , compte C WHERE C.NumC = Cl.NumC AND Email = ? AND Pass = ?";
            String sql = "SELECT * FROM client WHERE Email = ? AND Pass = ?";

            // prep statement
            stmt = con.prepareStatement(sql);

            stmt.setString(1,email);
            stmt.setString(2,password);

            rs = stmt.executeQuery();

            if (rs.next()){
                int id = rs.getInt("Num");
                String Nom = rs.getString("Nom");
                String Prenom = rs.getString("Prenom");
                String Email = rs.getString("Email");
                String Pass = rs.getString("Pass");
                int Cref = rs.getInt("NumC");

                /*
                PreparedStatement stmtC = null;
                ResultSet reC = null;

                try {
                    String sql2 = "SELECT * FROM client Cl , compte C WHERE ? = Cl.NumC;";

                    stmtC = con.prepareStatement(sql2);
                    stmtC.setString(1, String.valueOf(Cref));
                    reC = stmtC.executeQuery();

                    if ()



                    return new Client(id,Nom,Prenom,Email,Pass,compte);
                }
                 catch (Exception e){
                    e.printStackTrace();
                 }
                */

                return new Client(id,Nom,Prenom,Email,Pass,Cref);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            close(con,stmt,rs);
    }
        return null;
}

    public void register(String Nom, String Prenom, String Email , String Pass) {
        Connection con = connect();
        PreparedStatement stmt = null;

        try {
            String sql = "INSERT INTO client(Nom,Prenom,Email,Pass)"+"VALUES (?,?,?,?)";

            stmt = con.prepareStatement(sql);

            stmt.setString(1,Nom);
            stmt.setString(2,Prenom);
            stmt.setString(3,Email);
            stmt.setString(4,Pass);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close(con,stmt,null);
        }
    }

    // update balance - used for both deposit and withdrawal
    public void updateBalance(float newBalance, int id){
        Connection con = connect();
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE compte SET Solde = ? WHERE NumC = ? ";

            stmt = con.prepareStatement(sql);

            stmt.setFloat(1, newBalance);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            close(con,stmt,null);
        }
    }

    public void receiveTransfer(float moneyToAdd, int id){
        Connection con = connect();
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE customer SET balance = balance + ? WHERE id = ?";
            stmt = con.prepareStatement(sql);

            stmt.setFloat(1, moneyToAdd);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            close(con,stmt,null);
        }
    }

    // query that checks if a user exists
    public boolean customerExists(int id) {

        Connection con=connect();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // create sql query - ERROR WHEN USING '?'
            String sql = "SELECT * FROM customer WHERE id = ?";

            // prep statement
            stmt = con.prepareStatement(sql);

            stmt.setInt(1,id);


            rs = stmt.executeQuery();

            if (rs.next()){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            close(con,stmt,rs);
        }
        return false;
    }


    private void close(Connection con, Statement stmt, ResultSet rs){
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null){
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
