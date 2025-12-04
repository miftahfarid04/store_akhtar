/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mstr_user;

import Class.DBO;
import Class.mstr_user;
import java.awt.Dimension;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

/**
 *
 * @author LENOVO
 */
public class _List extends javax.swing.JInternalFrame {

    private boolean append;
    private DefaultTableModel _tempttrs_mstr_customer;
    Connection _Cnn;
    String _User;
    
    /**
     * Creates new form _List
     */
    public _List() {
        initComponents();
        
        String[] kolomtbl = {"USER ID", "NAMA", "JENIS KELAMIN", "TEMPAT LAHIR", "TGL LAHIR", "ALAMAT", "KODE"};
        _tempttrs_mstr_customer = new DefaultTableModel(null, kolomtbl) {
            Class[] types = new Class[]{
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int row, int col) {
                int kolom = _tempttrs_mstr_customer.getColumnCount();
                return (col < kolom) ? false : true; 
            }
        };

        // Mengatur model tabel GUI Anda
        tbl_mstr_user.setModel(_tempttrs_mstr_customer); 

        loadData();
    }
    
    public void hapusRowData() {
        int row = _tempttrs_mstr_customer.getColumnCount();
//        for (int i = 0; i < row; i++) {
//            _tempttrs_mstr_customer.removeRow(0);
//        }
        while (_tempttrs_mstr_customer.getRowCount() > 0) {
            _tempttrs_mstr_customer.setRowCount(0); //removeRow(0);
        }
    }

    private void loadData() {
        String _sql = "";
        DBO vcon = new DBO();
        vcon.makeConnect();
        hapusRowData();
        try {
            _Cnn = vcon.vkoneksi;
            // 1. Logika Pembuatan Query SQL
            if (txtcari.getText().equals("")) {
                _sql = "SELECT `user_id`, `nama`, `jenis_kelamin`, `tempat_lahir`, `tanggal_lahir`, `alamat`, `password` "
                     + "FROM `mstr_user`";
            } else {
                _sql = "SELECT * FROM mstr_user "
                     + "WHERE user_id LIKE '%" + txtcari.getText() + "%'"
                     + "OR nama LIKE '%" + txtcari.getText() + "%'"
                     + "OR alamat LIKE '%" + txtcari.getText() + "%'";
            }
            System.out.println(_sql);
            Statement st = _Cnn.createStatement();
            ResultSet rs = st.executeQuery(_sql);
            while (rs.next()) {
                String user_id = rs.getString(1);
                String nama = rs.getString(2);
                String jenis_kelamin = rs.getString(3);
                String tempat_lahir = rs.getString(4);
                String tanggal_lahir = rs.getString(5);
                String alamat = rs.getString(6);
                String password = rs.getString(7);
                Object[] data = {user_id, nama, jenis_kelamin, tempat_lahir, tanggal_lahir, alamat, password};
                _tempttrs_mstr_customer.addRow(data);
            }
            tbl_mstr_user.getColumnModel().getColumn(0).setPreferredWidth(40);  // USER ID
            tbl_mstr_user.getColumnModel().getColumn(1).setPreferredWidth(10);  // NAMA
            tbl_mstr_user.getColumnModel().getColumn(2).setPreferredWidth(100); // JENIS KELAMIN
            tbl_mstr_user.getColumnModel().getColumn(3).setPreferredWidth(100); // TEMPAT LAHIR
            tbl_mstr_user.getColumnModel().getColumn(4).setPreferredWidth(100); // TGL LAHIR
            tbl_mstr_user.getColumnModel().getColumn(5).setPreferredWidth(100); // ALAMAT
            tbl_mstr_user.getColumnModel().getColumn(6).setPreferredWidth(100); // PASSWORD
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error" + e);
        }
    }
    
    ActionListener actionlist;
    private javax.swing.Timer timer = new javax.swing.Timer(100, actionlist);
    
    private void refresh() {
        try {
            loadData();
            ActionListener acl = new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    // Menghentikan timer setelah aksi pertama dilakukan
                    timer.stop(); 
                }
            };
            timer = new javax.swing.Timer(100, acl);
            timer.start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e);
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

        jXTitledPanel1 = new org.jdesktop.swingx.JXTitledPanel();
        jLabel1 = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();
        bUbah = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_mstr_user = new javax.swing.JTable();
        bTambah = new javax.swing.JButton();
        bHapus = new javax.swing.JButton();
        bRef = new javax.swing.JButton();
        bKeluar = new javax.swing.JButton();

        setTitle("Form List Data User");

        jXTitledPanel1.setTitle("_List Data User");

        jLabel1.setText("Pencarian");

        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcariKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcariKeyTyped(evt);
            }
        });

        bUbah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/editing.png"))); // NOI18N
        bUbah.setText("Ubah");
        bUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUbahActionPerformed(evt);
            }
        });

        tbl_mstr_user.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tbl_mstr_user);

        bTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add.png"))); // NOI18N
        bTambah.setText("Tambah");
        bTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambahActionPerformed(evt);
            }
        });

        bHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        bHapus.setText("Hapus");
        bHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusActionPerformed(evt);
            }
        });

        bRef.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        bRef.setText("Refresh");
        bRef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefActionPerformed(evt);
            }
        });

        bKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logout.png"))); // NOI18N
        bKeluar.setText("Keluar");
        bKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bKeluarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXTitledPanel1Layout = new javax.swing.GroupLayout(jXTitledPanel1.getContentContainer());
        jXTitledPanel1.getContentContainer().setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bRef, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                    .addComponent(bHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bTambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bUbah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(509, 509, 509))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jXTitledPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 836, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jXTitledPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                        .addComponent(bUbah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bRef)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bKeluar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXTitledPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bKeluarActionPerformed
        this.dispose();
    }//GEN-LAST:event_bKeluarActionPerformed

    private void bUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUbahActionPerformed
        try {
            String kode = tbl_mstr_user.getValueAt(tbl_mstr_user.getSelectedRow(), 0).toString();
            _Entry f = new _Entry("", kode, "Edit");
            Dimension parentSize = this.getSize();
            f.setLocation((parentSize.width - f.getSize().width) / 2, (parentSize.height - f.getSize().height) / 2);
            f.setLocationRelativeTo(null);
            f.setVisible(true);

            f.b_keluar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    loadData();
                }
            });

            f.b_simpan.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    refresh();
                }
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e);
        }
    }//GEN-LAST:event_bUbahActionPerformed

    private void bTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambahActionPerformed
        try {
            _Entry f = new _Entry("", "", "Add");
            Dimension parentSize = this.getSize();
            f.setLocation((parentSize.width) / 2, (parentSize.height) / 2);
            f.setLocationRelativeTo(null); 
            f.setVisible(true);
            f.b_keluar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    refresh();
                }
            });
            f.b_simpan.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    loadData();
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_bTambahActionPerformed

    private void bHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusActionPerformed
        String kode = tbl_mstr_user.getValueAt(tbl_mstr_user.getSelectedRow(), 0).toString();
        int pilih = JOptionPane.showConfirmDialog(this, "Hapus data user: " + kode, "KONFIRMASI", JOptionPane.YES_NO_OPTION);
        if (pilih == JOptionPane.YES_OPTION) {
            mstr_user x = new mstr_user();
            x.hapusdata(kode);
            refresh();
            loadData();
        }
    }//GEN-LAST:event_bHapusActionPerformed

    private void bRefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefActionPerformed
        try {
            refresh();
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_bRefActionPerformed

    private void txtcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyTyped
        try {
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_txtcariKeyTyped

    private void txtcariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyPressed
        try {
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_txtcariKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bHapus;
    private javax.swing.JButton bKeluar;
    private javax.swing.JButton bRef;
    private javax.swing.JButton bTambah;
    private javax.swing.JButton bUbah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTitledPanel jXTitledPanel1;
    private javax.swing.JTable tbl_mstr_user;
    private javax.swing.JTextField txtcari;
    // End of variables declaration//GEN-END:variables
}
