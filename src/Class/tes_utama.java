/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;

/**
 *
 * @author LENOVO
 */
public class tes_utama {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DBO test = new DBO();
        //konek()
        test.makeConnect(); 
        
        // test-2 simpan data
        ttrs_penjualan_detail test_2 = new ttrs_penjualan_detail();
        test_2.faktur = "F0029";
        test_2.prd_id = "f_b_folio";
        test_2.qty = 1000.0;
        test_2.diskon = 12.0;
//        test_2.simpandata();
//        
        //hapus data
//        test_2.hapusdata(test_2.faktur, test_2.prd_id);

        // cari data
        test_2.Cari(test_2.faktur, test_2.prd_id);

        // update data
//        test_2.updatedata();

        // simpan or update
//        test_2.simpanORUpdate();
    }
    
}
