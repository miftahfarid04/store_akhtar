/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;

import java.sql . * ;
import java.util.logging.Level ;
import java.util.logging.Logger ;
import javax.swing.JOptionPane ;
/**
 *
 * @author LENOVO
 */
public class ttrs_penjualan {
    Connection _Cnn;
    public String cust_id, nama, alamat, telp, faktur, tanggal, user_id;
    public String _Akses = "";
    
    public void hapusdata(String kode) {
        System.out.println("pesan kode" + kode);
        this.cust_id = kode;
        try {
            _Cnn = DBO.getConnection();
            String _sql = "DELETE FROM `store_akhtar`.`mstr_customer` WHERE "
                    + "`mstr_customer`.`cust_id` = '" + cust_id + "';";
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
        try {
            String _sql = "";
            _Cnn = DBO.getConnection();
            _sql = "INSERT INTO `mstr_customer` (`cust_id`, `nama`, `alamat`, `telp`)" + " VALUES (?,?,?,?)";
            PreparedStatement prs = _Cnn.prepareStatement(_sql);
            prs.setString(1, this.cust_id);
            prs.setString(2, this.nama);
            prs.setString(3, this.alamat);
            prs.setString(4, this.telp);
            prs.executeUpdate();
            System.out.println(_sql);
            _Cnn = DBO.getConnection();
            prs.close();
        } catch (Exception err) {
            JOptionPane.showInternalMessageDialog(null, err);
        }
    }
    
    public void Cari(String cust_id) {
        String pilih_cust_id = cust_id;
        try {
            _Akses = "";
            _Cnn = DBO.getConnection();
            String SQL = "SELECT * "
                       + "FROM `mstr_customer` "
                       + "WHERE `cust_id` = '" 
                       + pilih_cust_id + "';";
            Statement st = _Cnn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {
                _Akses = "-";
                this.cust_id = rs.getString(1);
                this.nama = rs.getString(2);
                this.alamat = rs.getString(3);
                this.telp = rs.getString(4);
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
            _sql = "UPDATE `store_akhtar`.`mstr_customer` SET "
                    + "`nama` = '" + String.valueOf(nama) + "',"
                    + "`alamat` = '" + String.valueOf(alamat) + "',"
                    + "`telp` = '" + String.valueOf(telp) + "'"
                    + " WHERE `mstr_customer`.`cust_id` = '" + String.valueOf(cust_id) + "';";
            int status = DBO.executestatement(_sql);
            PreparedStatement prs = _Cnn.prepareStatement(_sql);
            if (status == 1) {
                prs.setString(1, this.cust_id);
                prs.setString(2, this.nama);
                prs.setString(3, this.alamat);
                prs.setString(4, this.telp);
                prs.executeUpdate();

                System.out.println(_sql);
                _Cnn = DBO.getConnection();
                prs.close();
            }
        } catch (Exception err) {
            System.out.println("ERROR" + err + "" + _Cnn + "" + _sql);
        }
    }
    
    public void simpanORupdate() {
        try {
            String _sql;
            _Cnn = DBO.getConnection();
            if (_Akses.equals("")) {
                _sql = "INSERT INTO `store_akhtar`.`mstr_customer` (`cust_id`, `nama`, `alamat`, `telp`)"
                        + " VALUES ('?', '?', '?', '?')";
                System.out.println(_sql);
            } else {
                _sql = "UPDATE `store_akhtar`.`mstr_customer` SET `cust_id` = '" + cust_id + "',"
                        + "`nama` = '" + nama + "',"
                        + "`alamat` = '" + alamat + "',"
                        + "`telp` = '" + telp + "'"
                        + " WHERE `mstr_customer`.`cust_id` = '" + cust_id + "';";
                System.out.println(_sql);
            }
            PreparedStatement prs = _Cnn.prepareStatement(_sql);
            prs.setString(1, this.cust_id);
            prs.setString(2, this.nama);
            prs.setString(3, this.alamat);
            prs.setString(4, this.telp);
            prs.executeUpdate();
            System.out.println(_sql);
            prs.close();
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, "BB ERROR : " + err);
        }
    }
    
    
}
