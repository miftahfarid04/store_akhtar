/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;

import java.sql . * ;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author LENOVO
 */
public class mstr_user {
    Connection _Cnn;
    public String user_id, nama, jenis_kelamin, tempat_lahir, tanggal_lahir, alamat, password;

    public String _Akses = "";
    
    // Metode hapusdata
    public void hapusdata(String kode) {
        System.out.println("pesan kode" + kode);
        this.user_id = kode;
        try {
            _Cnn = DBO.getConnection();

            String _sql = "DELETE FROM `store_akhtar`.`mstr_user` WHERE `mstr_user`.`user_id` = '" + user_id + "';";

            System.out.println(_sql);

            PreparedStatement prs = _Cnn.prepareStatement(_sql);
            prs.executeUpdate();

            System.out.println(_sql);
            prs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DBO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void simpandata() {
        DBO v = new DBO();
        try {
            String _sql = "";
            _Cnn = v.getConnection();

            _sql = "INSERT INTO `store_akhtar`.`mstr_user` (`user_id`,`nama`,`jenis_kelamin`,"
                    + "`tempat_lahir`,`tanggal_lahir`,`alamat`,`password`)VALUES"
                    + " ('" + String.valueOf(user_id) + "',"
                    + " '" + String.valueOf(nama) + "',"
                    + " '" + String.valueOf(jenis_kelamin) + "',"
                    + " '" + String.valueOf(tempat_lahir) + "',"
                    + " '" + String.valueOf(tanggal_lahir) + "',"
                    + " '" + String.valueOf(alamat) + "',"
                    + " '" + String.valueOf(password) + "')";

            System.out.println(_sql);
            int status = v.executestatement(_sql);

            if (status == 1) {
                System.out.println(_sql);
            }

        } catch (Exception err) {
            System.out.println("_simpan data error_" + err);
        }
    }
    
    public void Cari(String _user_id) {
        String pilih_user_id = _user_id;
        try {
            _Akses = "";
            _Cnn = DBO.getConnection();

            String SQL = "SELECT * FROM `mstr_user` WHERE `user_id` = '" 
                         + pilih_user_id + "';";

            Statement st = _Cnn.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            while (rs.next()) {
                _Akses = "-";
                this.user_id = rs.getString(1);
                this.nama = rs.getString(2);
                this.jenis_kelamin = rs.getString(3);
                this.tempat_lahir = rs.getString(4);
                this.tanggal_lahir = rs.getString(5);
                this.alamat = rs.getString(6);
                this.password = rs.getString(7);
                System.out.println(SQL);
                _Cnn = DBO.getConnection();
            }

            st.close();
            System.out.println("" + st.toString());

        } catch (Exception err) {
            System.out.println("_ERROR" + err.toString());
        }
    }
    
    public void updatedata() {
        boolean result = false;
        String _sql = "";
        try {
            _Cnn = DBO.getConnection();
            Statement st = _Cnn.createStatement();

            _sql = "UPDATE `store_akhtar`.`mstr_user` SET "
                    + "`nama` = '" + String.valueOf(nama) + "',"
                    + "`jenis_kelamin` = '" + String.valueOf(jenis_kelamin) + "',"
                    + "`tempat_lahir` = '" + String.valueOf(tempat_lahir) + "',"
                    + "`tanggal_lahir` = '" + (Date.valueOf(tanggal_lahir)).toString() + "',"
                    + "`alamat` = '" + String.valueOf(alamat) + "',"
                    + "`password` = '" + String.valueOf(password) + "'"
                    + " WHERE `mstr_user`.`user_id` = '"
                    + String.valueOf(user_id) + "';";

            int status = DBO.executestatement(_sql);
            PreparedStatement prs = _Cnn.prepareStatement(_sql);

            if (status == 1) {
                prs.setString(1, this.user_id);
                prs.setString(2, this.nama);
                prs.setString(3, this.jenis_kelamin);
                prs.setString(4, this.tempat_lahir);
                prs.setString(5, this.tanggal_lahir);
                prs.setString(6, this.alamat);
                prs.setString(7, this.password);
                prs.executeUpdate();

                // Bagian dari gambar kedua (image_d181e2.png)
                System.out.println(_sql);
                _Cnn = DBO.getConnection();
                prs.close();
            }

        } catch (Exception err) {
            // Bagian dari gambar kedua (image_d181e2.png)
            System.out.println("ERROR" + err + "" + _Cnn + "" + _sql);
        }
    }
    
    public void simpanORupdate() {
        try {
            String _sql;
            _Cnn = DBO.getConnection();

            if (_Akses.equals("")) {
                _sql = "INSERT INTO `store_akhtar`.`mstr_user` (`user_id`, `nama`, `jenis_kelamin`, "
                        + "`tempat_lahir`, `tanggal_lahir`, `alamat`, `password`)"
                        + "VALUES ('?', '?', '?', '?', '?', '?', '?')";
                System.out.println(_sql);
            } else {
                _sql = "UPDATE `store_akhtar`.`mstr_user` SET "
                        + "`nama` = '" + String.valueOf(nama) + "',"
                        + "`jenis_kelamin` = '" + String.valueOf(jenis_kelamin) + "',"
                        + "`tempat_lahir` = '" + String.valueOf(tempat_lahir) + "',"
                        + "`tanggal_lahir` = '" + (Date.valueOf(tanggal_lahir)).toString() + "',"
                        + "`alamat` = '" + String.valueOf(alamat) + "',"
                        + "`password` = '" + String.valueOf(password) + "'"
                        + " WHERE `mstr_user`.`user_id` = '" + String.valueOf(user_id) + "';";
                System.out.println(_sql);
            }

            PreparedStatement prs = _Cnn.prepareStatement(_sql);

            prs.setString(1, this.user_id);
            prs.setString(2, this.nama);
            prs.setString(3, this.jenis_kelamin);
            prs.setString(4, this.tempat_lahir);
            prs.setString(5, this.tanggal_lahir);
            prs.setString(6, this.alamat);
            prs.setString(7, this.password);
            prs.executeUpdate();

            System.out.println(_sql);
            prs.close();

        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, "BB ERROR : " + err);
        }
    }
}
