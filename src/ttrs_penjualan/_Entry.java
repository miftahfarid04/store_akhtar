/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttrs_penjualan;

import mstr_customer.*;
import Class.DBO;
import Class.mstr_customer;
import Class.ttrs_penjualan;
import Class.ttrs_penjualan_detail;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author LENOVO
 */
public class _Entry extends javax.swing.JFrame {

    Connection _Cnn;
    private boolean append;
    DBO k = new DBO() ;
    private Dimension dmn =
    Toolkit.getDefaultToolkit().getScreenSize();
    private DefaultTableModel
    temp_tbl_mstr_penjualan_detail;
    SimpleDateFormat t2 = new SimpleDateFormat("yyyyMM-dd") ;
    
    String _PID;
    String _Status;
    String _User;
    /**
     * Creates new form _Entry
     */
//    public _Entry() {
//        initComponents();  
//    }
    
    public _Entry(String user, String pid, String status) {
        initComponents();
        _PID = pid;
        if (status.equals("Edit")) {
            System.out.println("kode dipilih " + pid);
            txt_faktur.setText(_PID);
            b_simpan.setText("Update");
        }
        _Status = status;
        _User = user;
        listuser();
        ListProduct();
        listcustomer();

        String[] kolomtbl = {"FAKTUR", "PRODUK", "HARGA", "QTY", "DISKON", "SUB TOTAL"};
        temp_tbl_mstr_penjualan_detail = new DefaultTableModel(null, kolomtbl) {
            Class[] types = new Class[]{
                java.lang.String.class, // faktur
                java.lang.String.class, // prd_id
                java.lang.Double.class, // harga
                java.lang.Double.class, // qty
                java.lang.Double.class, // diskon
                java.lang.Double.class // subtotal
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

        tb_ttrs_mstr_penjualan_detail.setModel(temp_tbl_mstr_penjualan_detail);
        loadData();

        tb_ttrs_mstr_penjualan_detail.getColumnModel().getColumn(0).setPreferredWidth(90);
        tb_ttrs_mstr_penjualan_detail.getColumnModel().getColumn(1).setPreferredWidth(100);
        tb_ttrs_mstr_penjualan_detail.getColumnModel().getColumn(2).setPreferredWidth(100);
        tb_ttrs_mstr_penjualan_detail.getColumnModel().getColumn(3).setPreferredWidth(100);
        tb_ttrs_mstr_penjualan_detail.getColumnModel().getColumn(4).setPreferredWidth(110);
        tb_ttrs_mstr_penjualan_detail.getColumnModel().getColumn(5).setPreferredWidth(110);
    }

    public _Entry() {
    }

    public void hapusRowData() {
        int row = temp_tbl_mstr_penjualan_detail.getColumnCount();
        while (temp_tbl_mstr_penjualan_detail.getRowCount() > 0) {
            temp_tbl_mstr_penjualan_detail.setRowCount(0);//removeRow(0);
        }
    }
    
    ActionListener actionlist;
    private javax.swing.Timer timer = new javax.swing.Timer(10, actionlist);

    public void refresh() {
        hapusRowData();
        try {
            ActionListener acl = new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    timer.stop();
                }
            };

            timer = new javax.swing.Timer(10, acl);
            timer.start();

        } catch (Exception e) {
            System.out.println("ttrs_penjualan_Entry.refresh ERROR" + e);
        }
    }
    
    public void loadData() {
        refresh();
        try {
            _Cnn = DBO.getConnection();
            hapusRowData();
            String _sql = "";

            // Query 1: Memuat detail transaksi
            _sql = "SELECT pd.faktur, pd.prd_id, prd.harga, pd.qty, pd.diskon,((prd.harga * pd.qty)- ((prd.harga * pd.qty) * (pd.diskon/100))) AS bayar FROM "
                 + "ttrs_penjualan_detail pd, mstr_product prd WHERE pd.prd_id = prd.prd_id AND pd.faktur='"
                 + txt_faktur.getText() + "'"; // _PID jj

            System.out.println(_sql);
            Statement st = _Cnn.createStatement();
            ResultSet rst = st.executeQuery(_sql);

            while (rst.next()) {
                String faktur = rst.getString(1);
                String prd_id = rst.getString(2);
                Double harga = rst.getDouble(3);
                Double qty = rst.getDouble(4);
                Double diskon = rst.getDouble(5);
                Double subtotal = rst.getDouble(6);

                Object[] data = {faktur, prd_id, harga, qty, diskon, subtotal};
                temp_tbl_mstr_penjualan_detail.addRow(data);
            }

            // Query 2: Menghitung total pembayaran
            _Cnn = null;
            _Cnn = DBO.getConnection();
            _sql = "SELECT SUM(((prd.harga*pd.qty)-((prd.harga * pd.qty)*(pd.diskon/100)))) "
                 + "AS bayar FROM ttrs_penjualan_detail pd, mstr_product prd "
                 + "WHERE pd.prd_id = prd.prd_id AND pd.faktur='" + txt_faktur.getText() + "'";

            System.out.println(_sql);
            st = _Cnn.createStatement();
            rst = st.executeQuery(_sql);

            while (rst.next()) {
                txt_total.setText(Double.toString(rst.getDouble(1)));
                System.out.println("Bayar = " + rst.getDouble(1));
            }

        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    String[] KeyProduct;
    
    private void ListProduct() {
        try {
            _Cnn = DBO.getConnection();
            String SQL = "SELECT prd_id,nama,harga "
                       + "FROM `mstr_product`";
            Statement st = _Cnn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            txt_prd.removeAllItems();
            int i = 0;

            while (rs.next()) {
                txt_prd.addItem(rs.getString(2));
                i++;
            }

            rs.first();
            KeyProduct = new String[i + 1];
            for (Integer x = 0; x < i; x++) {
                KeyProduct[x] = rs.getString(1);
                rs.next();
            }
            rs.first();

            txt_harga.setText(Double.toString(rs.getDouble(3)));

        } catch (Exception e) {
        }
    }

    String[] KeyCustomer;
    
    private void listcustomer() {
        try {
            _Cnn = DBO.getConnection();
            String SQL = "SELECT cust_id,nama "
                       + "FROM `mstr_customer`";
            Statement st = _Cnn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            txt_cust.removeAllItems();
            int i = 0;

            while (rs.next()) {
                txt_cust.addItem(rs.getString(2));
                i++;
            }

            rs.first();

            KeyCustomer = new String[i + 1];
            for (Integer x = 0; x < i; x++) {
                KeyCustomer[x] = rs.getString(1);
                rs.next();
            }
            // rs.first(); // Baris ini tidak ada di gambar kedua, jadi tidak dimasukkan di sini

        } catch (Exception e) {
        }
    }
    
    String[] KeyUser;
    
    private void listuser() {
        try {
            _Cnn = DBO.getConnection();
            String SQL = "SELECT `user_id` FROM `mstr_user`";
            Statement st = _Cnn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            txt_user.removeAllItems();
            int i = 0;

            while (rs.next()) {
                txt_user.addItem(rs.getString(1));
                i++;
            }
            rs.first();
            KeyUser = new String[i + 1];
            for (Integer x = 0; x < i; x++) {
                KeyUser[x] = rs.getString(1);
                rs.next();
            }
        } catch (Exception e) {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txt_faktur = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_user = new javax.swing.JComboBox<>();
        txt_cust = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txt_prd = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txt_harga = new javax.swing.JTextField();
        txt_qty = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_diskon = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        b_simpan = new javax.swing.JButton();
        bHapus = new javax.swing.JButton();
        bCetak = new javax.swing.JButton();
        b_keluar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_ttrs_mstr_penjualan_detail = new javax.swing.JTable();
        txt_total = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        l_SetUser = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Faktur");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 21, -1, -1));
        getContentPane().add(txt_faktur, new org.netbeans.lib.awtextra.AbsoluteConstraints(76, 21, 130, -1));

        jLabel2.setText("user :");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 24, -1, -1));

        jLabel3.setText("Customer");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 90, -1, -1));

        txt_user.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "U1" }));
        getContentPane().add(txt_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(268, 48, 140, -1));

        txt_cust.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aan" }));
        getContentPane().add(txt_cust, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 87, 330, -1));

        jLabel4.setText("Product");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 116, -1, -1));

        txt_prd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DVD Blank" }));
        txt_prd.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                txt_prdItemStateChanged(evt);
            }
        });
        getContentPane().add(txt_prd, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 113, 330, -1));

        jLabel5.setText("Harga");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 142, -1, -1));
        getContentPane().add(txt_harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 139, 160, -1));
        getContentPane().add(txt_qty, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 165, 160, -1));

        jLabel6.setText("QTY");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 168, -1, -1));
        getContentPane().add(txt_diskon, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 191, 160, -1));

        jLabel7.setText("Diskon");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 194, -1, -1));

        b_simpan.setText("Simpan");
        b_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_simpanActionPerformed(evt);
            }
        });
        getContentPane().add(b_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 222, -1, -1));

        bHapus.setText("Hapus");
        bHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusActionPerformed(evt);
            }
        });
        getContentPane().add(bHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 222, -1, -1));

        bCetak.setText("Cetak");
        getContentPane().add(bCetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 222, -1, -1));

        b_keluar.setText("Keluar");
        b_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_keluarActionPerformed(evt);
            }
        });
        getContentPane().add(b_keluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(229, 222, -1, -1));

        tb_ttrs_mstr_penjualan_detail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PRODUK", "HARGA", "QTY", "DISKON", "SUB TOTAL"
            }
        ));
        jScrollPane1.setViewportView(tb_ttrs_mstr_penjualan_detail);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 252, 390, 117));

        txt_total.setText("0.0");
        getContentPane().add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 375, 90, -1));

        jLabel8.setText("Total");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 378, -1, -1));
        getContentPane().add(jXDatePicker1, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 47, 120, -1));

        l_SetUser.setText("xxxxxx");
        getContentPane().add(l_SetUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(344, 24, 60, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_prdItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_txt_prdItemStateChanged
        try {
            _Cnn = null;
            _Cnn = DBO.getConnection();
            String SQL = "SELECT harga "
                       + "FROM `mstr_product` "
                       + "WHERE `prd_id` = '"
                       + KeyProduct[txt_prd.getSelectedIndex()] + "'";
            Statement st = _Cnn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {
                txt_harga.setText(rs.getString(1).toString());
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txt_prdItemStateChanged

    private void b_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_keluarActionPerformed
        append = false;
        this.setVisible(append);
    }//GEN-LAST:event_b_keluarActionPerformed

    private void b_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_simpanActionPerformed
        try {
            ttrs_penjualan tp = new ttrs_penjualan();
            tp.Cari(txt_faktur.getText());
            tp.faktur = txt_faktur.getText();
            tp.tanggal = t2.format(jXDatePicker1.getDate());
            tp.cust_id = KeyCustomer[txt_cust.getSelectedIndex()];
            tp.user_id = _User;
            if (tp._Akses.equals("-")) {
                tp.updatedata();
                txt_faktur.setEditable(false);
            } else {
                tp.simpandata();
                txt_faktur.setEditable(false);
            }
            ttrs_penjualan_detail tpd = new ttrs_penjualan_detail();
            String produk = KeyProduct[txt_prd.getSelectedIndex()];
            tpd.Cari(txt_faktur.getText(), produk);
            tpd.faktur = txt_faktur.getText();
            tpd.prd_id = produk;
            tpd.qty = Double.parseDouble(txt_qty.getText().toString());
            tpd.diskon = Double.parseDouble(txt_diskon.getText().toString());
            tpd.simpanORupdate();
            loadData();

        } catch (Exception e) {
            System.err.println("ERROR simpan di tpd");
        }
    }//GEN-LAST:event_b_simpanActionPerformed

    private void bHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusActionPerformed
        try {
            String kode = tb_ttrs_mstr_penjualan_detail.getValueAt(tb_ttrs_mstr_penjualan_detail.getSelectedRow(), 1).toString();
            int pilih = JOptionPane.showConfirmDialog(this, "Hapus data user : " + kode, "KONFIRMASI", JOptionPane.YES_NO_OPTION);
            if (pilih == JOptionPane.YES_OPTION) {
                ttrs_penjualan_detail x = new ttrs_penjualan_detail();
                x.hapusdata(_PID, kode);
                refresh();
                loadData();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_bHapusActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(_Entry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(_Entry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(_Entry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(_Entry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new _Entry().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCetak;
    public javax.swing.JButton bHapus;
    public javax.swing.JButton b_keluar;
    public javax.swing.JButton b_simpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private javax.swing.JLabel l_SetUser;
    private javax.swing.JTable tb_ttrs_mstr_penjualan_detail;
    private javax.swing.JComboBox<String> txt_cust;
    private javax.swing.JTextField txt_diskon;
    private javax.swing.JTextField txt_faktur;
    private javax.swing.JTextField txt_harga;
    private javax.swing.JComboBox<String> txt_prd;
    private javax.swing.JTextField txt_qty;
    private javax.swing.JTextField txt_total;
    private javax.swing.JComboBox<String> txt_user;
    // End of variables declaration//GEN-END:variables
}
