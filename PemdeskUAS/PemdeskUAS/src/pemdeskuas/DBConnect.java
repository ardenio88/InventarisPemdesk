/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pemdeskuas;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author WINDOWS 11
 */
public class DBConnect {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    public DBConnect() {
        String dbName = "b@tiek";
        String url = "jdbc:mysql://localhost:3306/" + dbName + "?characterEncoding=utf8";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "";
        try {
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url, userName, password);
            st = (Statement) con.createStatement();
            System.out.println("Koneksi Sukses");
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

    public String getUname() {
        String uName = null;
        try {
            String q = "SELECT  username FROM `user` WHERE role = 'admin';";
            rs = st.executeQuery(q);
            rs.next();
            uName = rs.getString("username");

        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        return uName;
    }

    public String getPass() {
        String pass = null;
        try {
            String q = "SELECT  password FROM `user` WHERE role = 'admin';";
            rs = st.executeQuery(q);
            rs.next();
            pass = rs.getString("password");
            return pass;
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        return pass;
    }

    public Object[][] getDataKemeja() {
        Object[][] row = new Object[1000][4];
        try {
            String query = "select id,nama,harga,sisa from ref_pakaian where tipe = 'kemeja';";
            rs = st.executeQuery(query);
            int i = 0;
            while (rs.next()) {
                row[i][0] = rs.getInt("id");
                row[i][1] = rs.getString("nama");
                row[i][2] = rs.getInt("harga");
                row[i][3] = rs.getInt("sisa");
                i++;
            }
        } catch (Exception ex) {
            System.out.println("Error getDataKemeja: " + ex);
        }
        return row;
    }

    public int getCountKemeja() {
        int rowCount = 0;
        try {
            String q = "select count(*) as jum from ref_pakaian where tipe = 'kemeja' ";
            rs = st.executeQuery(q);
            rs.next();
            rowCount = rs.getInt("jum");
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        return rowCount;
    }

    public int getCountPakaian(String categories, String size) {
        int rowCount = 0;
        try {
            String q = "select count(*) as jum from ref_pakaian where tipe = '" + categories + "' ";
            if (!size.isEmpty()) {
            String[] sizes = size.split(",");
            q += " AND (";
            for (int i = 0; i < sizes.length; i++) {
                q += "size = '" + sizes[i] + "'";
                if (i < sizes.length - 1) {
                    q += " OR ";
                }
            }
            q += ")";
        }
            rs = st.executeQuery(q);
            rs.next();
            rowCount = rs.getInt("jum");
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        return rowCount;
    }

    public Object[][] getDataPakaian(String categories, String size, String sortBy, boolean isDescending) {
    Object[][] row = new Object[1000][5];
    try {
        String query = "SELECT id, nama, size, harga, sisa FROM ref_pakaian WHERE tipe = '" + categories + "'";
        if (!size.isEmpty()) {
            String[] sizes = size.split(",");
            query += " AND (";
            for (int i = 0; i < sizes.length; i++) {
                query += "size = '" + sizes[i] + "'";
                if (i < sizes.length - 1) {
                    query += " OR ";
                }
            }
            query += ")";
        }
        if (sortBy.equals("size")) {
            query += " ORDER BY CASE size "
                    + "WHEN 'S' THEN 1 "
                    + "WHEN 'M' THEN 2 "
                    + "WHEN 'L' THEN 3 "
                    + "WHEN 'XL' THEN 4 "
                    + "END";
        } else {
            query += " ORDER BY " + sortBy; // Tambahkan pengurutan berdasarkan kolom yang dipilih
        }
        if (isDescending) {
            query += " DESC";
        }
        System.out.println(query);
        rs = st.executeQuery(query);
        int i = 0;
        while (rs.next()) {
            row[i][0] = rs.getInt("id");
            row[i][1] = rs.getString("nama");
            row[i][2] = rs.getString("size");
            row[i][3] = rs.getInt("harga");
            row[i][4] = rs.getInt("sisa");
            i++;
        }
    } catch (Exception ex) {
        System.out.println("Error getDataPakaian: " + ex);
    }
    return row;
}

    public int getCountDress() {
        int rowCount = 0;
        try {
            String q = "select count(*) as jum from ref_pakaian where tipe = 'Dress'";
            rs = st.executeQuery(q);
            rs.next();
            rowCount = rs.getInt("jum");
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        return rowCount;
    }

    public Object[][] getDataTunic() {
        Object[][] row = new Object[1000][4];
        try {
            String query = "select id,nama,harga,sisa from ref_pakaian where tipe = 'tunic';";
            rs = st.executeQuery(query);
            int i = 0;
            while (rs.next()) {
                row[i][0] = rs.getInt("id");
                row[i][1] = rs.getString("nama");
                row[i][2] = rs.getInt("harga");
                row[i][3] = rs.getInt("sisa");
                i++;
            }
        } catch (Exception ex) {
            System.out.println("Error getDataKemeja: " + ex);
        }
        return row;
    }

    public int getCountTunic() {
        int rowCount = 0;
        try {
            String q = "select count(*) as jum from ref_pakaian where tipe = 'tunic'";
            rs = st.executeQuery(q);
            rs.next();
            rowCount = rs.getInt("jum");
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        return rowCount;
    }

    public Object[][] getDataAll() {
        Object[][] row = new Object[1000][5];
        try {
            String query = "select id,nama,size ,harga,sisa from ref_pakaian;";
            rs = st.executeQuery(query);
            int i = 0;
            while (rs.next()) {
                row[i][0] = rs.getInt("id");
                row[i][1] = rs.getString("nama");
                row[i][2] = rs.getString("size");
                row[i][3] = rs.getInt("harga");
                row[i][4] = rs.getInt("sisa");
                i++;
            };
        } catch (Exception ex) {
            System.out.println("Error getDataAll: " + ex);
        }
        return row;
    }

    public int getCountAll() {
        int rowCount = 0;
        try {
            String q = "select count(*) as jum from ref_pakaian";
            rs = st.executeQuery(q);
            rs.next();
            rowCount = rs.getInt("jum");
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
        return rowCount;
    }

    public void setDataPakaian(String nama, String size, Integer harga, Integer sisa, String tipe) {
        try {
            String q = "INSERT INTO ref_pakaian (nama, size, harga, sisa, tipe) " + "VALUES (?, ?, ?, ?, ?)";
//            int rowCount = rs.getInt("maks");
//            rowCount = rowCount + 1;
//            Calendar calendar = Calendar.getInstance();
//            java.sql.Timestamp lastUpdate = new java.sql.Timestamp(calendar.getTime().getTime());
//            String query = " insert into ref_bank(id, kode, nama, deskripsi, lastUpdate, userUpdate)" + " values (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(q);
            preparedStmt.setString(1, nama);
            preparedStmt.setString(2, size);
            preparedStmt.setInt(3, harga);
            preparedStmt.setInt(4, sisa);
            preparedStmt.setString(5, tipe);
            preparedStmt.execute();
            preparedStmt.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void updatePakaian(String nama, String size, Integer harga, Integer sisa, String tipe, String id) {
        try {
            String query = " update ref_pakaian set nama = ?, size = ?, harga = ? ,sisa =  ? ,tipe =  ? where id = ?";
            PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
            preparedStmt.setString(1, nama);
            preparedStmt.setString(2, size);
            preparedStmt.setInt(3, harga);
            preparedStmt.setInt(4, sisa);
            preparedStmt.setString(5, tipe);
            preparedStmt.setString(6, id);
            preparedStmt.execute();
            preparedStmt.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void deletePakaian(String id) {
        try {
            String query = " delete from ref_pakaian where id = ?";
            PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
            preparedStmt.setString(1, id);
            preparedStmt.execute();
            preparedStmt.close();
        } catch (Exception ex) {

            System.out.println("Error: " + ex);
        }
    }

    public String getKategori(String Id) {
        String kategori = "";
        try {
            String query = "Select tipe from ref_pakaian where Id = " + Id + ";";
            rs = st.executeQuery(query);
            System.out.println(query);
            rs.next();
            kategori = rs.getString("tipe");
        } catch (Exception e) {
            System.out.println("Error getKategori: " + e);
        }
        return kategori;
    }

    public String getSize(String Id) {
        String size = "";
        try {
            String query = "Select size from ref_pakaian where Id = " + Id + ";";
//            System.out.println(query);
            rs = st.executeQuery(query);
            rs.next();
            size = rs.getString("size");
        } catch (Exception e) {
            System.out.println("Error getSize: " + e);
        }
        return size;
    }
}
