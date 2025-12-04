/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ttrs_penjualan._Entry;

/**
 *
 * @author LENOVO
 */
public class ttrs_penjualan_detail {
    Connection _Cnn;
    public String faktur, prd_id;
    public Double qty, diskon;
    public String _Akses = "";
    
    public void hapusdata(String faktur, String prd_id) {
        System.out.println("pesan kode" + faktur);
        try {
            _Cnn = DBO.getConnection();
            String _sql = "DELETE FROM `ttrs_penjualan_detail` WHERE `faktur` = '" + faktur + "' AND `prd_id` = '" + prd_id + "';";
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
            _sql = "INSERT INTO ttrs_penjualan_detail"
                    + " VALUES"
                    + " ('" + faktur + "',"
                    + " '" + prd_id + "',"
                    + " '" + qty + "',"
                    + " '" + diskon + "')";
            System.out.println(_sql + " ttrs simpan");
            int status = DBO.executestatement(_sql);
            PreparedStatement prs = _Cnn.prepareStatement(_sql);
            if (status == 1) {
                prs.setString(1, this.faktur);
                prs.setString(2, this.prd_id);
                prs.setDouble(3, this.qty);
                prs.setDouble(4, this.diskon);
                prs.executeUpdate();
                System.out.println(_sql);
                _Cnn = DBO.getConnection();
            }
            prs.close();
        } catch (Exception err) {
            System.err.println ( " simpan or update Penjualan Detail ERROR :" + err) ;
        }
    }
    
    public void Cari(String _faktur, String _prd_id) {
        this.faktur = _faktur;
        this.prd_id = _prd_id;
        try {
            _Akses = "";
            _Cnn = DBO.getConnection();
            String SQL = "SELECT * FROM `ttrs_penjualan_detail`"
                    + " WHERE `faktur` = '" + _faktur + "' AND prd_id = '" + _prd_id + "';";
            Statement st = _Cnn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {
                _Akses = "-";
                this.faktur = rs.getString(1);
                this.prd_id = rs.getString(2);
                this.qty = rs.getDouble(3);
                this.diskon = rs.getDouble(4);
                System.out.println(SQL);
                System.out.println(this.faktur);
                System.out.println(this.prd_id);
                System.out.println(this.qty);
                System.out.println(this.diskon);
                _Cnn = DBO.getConnection();
            }
            st.close();
        } catch (Exception e) {
        }
    }
    
    public boolean updatedata() {
        boolean result = false;
        String _sql = "";
        try {
            _Cnn = DBO.getConnection();
            Statement st = _Cnn.createStatement();
            _sql = "UPDATE ttrs_penjualan_detail SET"
                + " `faktur` = '" + String.valueOf(faktur) + "',"
                + "`prd_id` = '" + String.valueOf(prd_id) + "',"
                + "`qty` = '" + String.valueOf(qty) + "',"
                + "`diskon` = '" + String.valueOf(diskon) + "'"
                + " WHERE `faktur` = '" + String.valueOf(faktur) + "' AND `prd_id`='"
                + String.valueOf(prd_id) + "';";
            int status = DBO.executestatement(_sql);
            PreparedStatement prs = _Cnn.prepareStatement(_sql);
            if (status == 1) {
                prs.setString(1, this.faktur);
                prs.setString(2, this.prd_id);
                prs.setDouble(3, this.qty);
                prs.setDouble(4, this.diskon);
                prs.executeUpdate();
                System.out.println(_sql);
                _Cnn = DBO.getConnection();
                prs.close();
            }
        } catch (Exception err) {
            System.err.println("ERROR" + err + "" + _Cnn + "" + _sql);
        }
        return result;
    }
    
    public void simpanORupdate() {
        try {
            String _sql = "";
            _Cnn = DBO.getConnection();
            if (_Akses.equals("")) {
                _sql = "INSERT INTO `store_akhtar`.`ttrs_penjualan_detail` ('faktur', 'prd_id', "
                    + "'qty', 'diskon') VALUES('" + faktur + "','" + prd_id + "','" + qty + "','"
                    + diskon + "')";
                System.out.println(_sql);
            } else if (_Akses.equals("-")) {
                _sql = "UPDATE ttrs_penjualan_detail SET"
                    + "'faktur' = '" + faktur + "',"
                    + "'prd_id' = '" + prd_id + "',"
                    + "'qty' = '" + qty + "',"
                    + "'diskon' = '" + diskon + "'"
                    + " WHERE 'faktur' = '" + faktur + "' AND prd_id='" + prd_id + "';";
                System.out.println(_sql + " simpan > update ttrs p detail");
            }
            int status = DBO.executestatement(_sql);
            PreparedStatement prs = _Cnn.prepareStatement(_sql);
        
            if (status == 1) {
                prs.setString(1, this.faktur);
                prs.setString(2, this.prd_id);
                prs.setDouble(3, this.qty);
                prs.setDouble(4, this.diskon);
                prs.executeUpdate();
                System.out.println(_sql);
                prs.close();
                _Cnn = DBO.getConnection();
            }
        } catch (Exception err) {
            System.err.println("simpan or update Penjualan Detail ERROR : " + err);
        }
    }
}
