/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;

import java.sql . * ;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author LENOVO
 */
public class mstr_product {
    Connection _Cnn;
    public String prd_id, nama, satuan, harga;
    public String _Akses = "";

    // Metode hapusdata
    public void hapusdata(String kode) {
        System.out.println("pesan kode" + kode);
        this.prd_id = kode;
        try {
            _Cnn = DBO.getConnection();

            String _sql = "DELETE FROM `store_akhtar`.`mstr_product` WHERE "
                    + "`mstr_product`.`prd_id` = '" + prd_id + "';";

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

            _sql = "INSERT INTO `mstr_product` (`prd_id`, `nama`, `satuan`, `harga`)"
                    + " VALUES (?,?,?,?)";

            PreparedStatement prs = _Cnn.prepareStatement(_sql);
            prs.setString(1, this.prd_id);
            prs.setString(2, this.nama);
            prs.setString(3, this.satuan);
            prs.setString(4, this.harga); // Perhatikan: Harga biasanya Double/Float, tapi diset String di kode asli.
            prs.executeUpdate();

            System.out.println(_sql);
            _Cnn = DBO.getConnection();
            prs.close();

        } catch (Exception err) {
            System.out.println("" + err);
        }
    }
    
    public void Cari(String prd_id) {
        String pilih_prod_id = prd_id;
        try {
            _Akses = "";
            _Cnn = DBO.getConnection();

            String SQL = "SELECT * "
                    + "FROM `mstr_product` "
                    + "WHERE `prd_id` = '" + pilih_prod_id + "';";

            Statement st = _Cnn.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            while (rs.next()) {
                _Akses = "-";
                this.prd_id = rs.getString(1);
                this.nama = rs.getString(2);
                this.satuan = rs.getString(3);
                this.harga = rs.getString(4);
                System.out.println(SQL);
                _Cnn = DBO.getConnection();
            }

            // Bagian dari gambar kedua (image_d1ebb6.png)
            st.close();
            System.out.println("" + st.toString());

        } catch (Exception err) {
            // Bagian dari gambar kedua (image_d1ebb6.png)
            System.out.println("_ERROR" + err.toString());
        }
    }
    
    public void updatedata() {
        boolean result = false;
        String _sql = "";
        try {
            _Cnn = DBO.getConnection();
            Statement st = _Cnn.createStatement();

            _sql = "UPDATE `store_akhtar`.`mstr_product` SET "
                    + "`nama` = '" + String.valueOf(nama) + "',"
                    + "`satuan` = '" + String.valueOf(satuan) + "',"
                    + "`harga` = '" + String.valueOf(harga) + "'"
                    + " WHERE `mstr_product`.`prd_id` = '" + String.valueOf(prd_id) + "';";

            int status = DBO.executestatement(_sql);
            PreparedStatement prs = _Cnn.prepareStatement(_sql);

            if (status == 1) {
                prs.setString(1, this.prd_id);
                prs.setString(2, this.nama);
                prs.setString(3, this.satuan);
                prs.setString(4, this.harga);
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
                _sql = "INSERT INTO `store_akhtar`.`mstr_product` (`prd_id`, `nama`, `satuan`, `harga`)"
                        + " VALUES ('?', '?', '?', '?')";
                System.out.println(_sql);
            } else {
                _sql = "UPDATE `store_akhtar`.`mstr_product` SET `prd_id` = '" + prd_id + "', `nama` = '"
                        + nama + "', `satuan` = '" + satuan + "', `harga` = '" + harga + "'"
                        + " WHERE `mstr_product`.`prd_id` = '" + prd_id + "';";
                System.out.println(_sql);
            }

            PreparedStatement prs = _Cnn.prepareStatement(_sql);

            prs.setString(1, this.prd_id);
            prs.setString(2, this.nama);
            prs.setString(3, this.satuan);
            prs.setString(4, this.harga);
            prs.executeUpdate();

            System.out.println(_sql);
            prs.close();

        } catch (Exception err) {
            System.out.println("BB ERROR : " + err);
        }
    }
    
    
}
