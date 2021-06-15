/*
 * Saya Ahmad Izzuddin mengerjakan evaluasi Tugas Masa Depan dalam mata kuliah
 * Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya
 * tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Fauzan
 */
public class dbConnection {

    public static Connection con;
    public static Statement stm;

    public void connect() {//untuk membuka koneksi ke database
        try {
            String url = "jdbc:mysql://localhost/highestlevel";
            String user = "root";
            String pass = "";
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            stm = con.createStatement();
            System.out.println("database connected;");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("connection failed" + e.getMessage());
        }
    }

    public void updateHighScore(String username, int score, int fail) {
        try {
            if (con == null) {
                connect();
            }
            PreparedStatement pstmt = dbConnection.con.prepareStatement("SELECT * FROM highscore WHERE username=?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            pstmt.clearParameters();
            if (rs.next()) {
                pstmt = dbConnection.con.prepareStatement("UPDATE highscore SET score=?, fail=? WHERE id=?");
                pstmt.setInt(1, rs.getInt("score") + score);
                pstmt.setInt(2, rs.getInt("fail") + fail);
                pstmt.setInt(3, rs.getInt("id"));
                pstmt.executeUpdate();
                pstmt.clearParameters();
            } else {
                pstmt = dbConnection.con.prepareStatement("INSERT INTO highscore (username, score, fail) VALUES (?, ?, ?)");
                pstmt.setString(1, username);
                pstmt.setInt(2, score);
                pstmt.setInt(3, fail);
                pstmt.executeUpdate();
                pstmt.clearParameters();
            }
        } catch (SQLException e) {
            System.err.println("update failed" + e.getMessage());
        }
    }

    public DefaultTableModel readTable() {
        Object[] column = {"No.", "Username", "Fail", "Success"};
        DefaultTableModel dataTabel = new DefaultTableModel(null, column);
        try {
            
            if (con == null || stm == null) {
                connect();
            }
            if (stm != null && con != null) {
                String sql = "SELECT * from highscore ORDER BY score Desc";
                ResultSet res = stm.executeQuery(sql);

                int no = 1;
                while (res.next()) {
                    Object[] hasil = new Object[5];
                    hasil[0] = no;
                    hasil[1] = res.getString("username");
                    hasil[2] = res.getString("fail");
                    hasil[3] = res.getString("score");
                    no++;
                    dataTabel.addRow(hasil);
                }
            } else {
                System.out.println("failed to connect to database");
            }
        } catch (SQLException e) {
            System.err.println("Read failed " + e.getMessage());
        }

        return dataTabel;
    }
}
