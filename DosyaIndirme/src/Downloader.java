
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Bu sınıf txt dosyasından linkleri okuyarak linklerin sırayla indirilmesi
 * ve indirme durumlarını göstermek için yapılmıştır.
 * 
 * @author Ahmet ALBAYRAK <ahmetalbayrak92@hotmail.com>
 */
public class Downloader extends javax.swing.JFrame {
    DefaultTableModel dtm ;
    
    /**     
     * Uygulama açıldığında txt dosyasından linkler okunur ve durumları 
     * tabloya bastılır.
     *      
     * @throws Exception 
     */
    public Downloader() throws Exception
    {
        initComponents();
        Object[] str = null;
        dtm = (DefaultTableModel)jTable1.getModel();
        File f = new File("text.txt");
        Scanner read = new Scanner(f);
        while(read.hasNextLine())
        {
            str=read.nextLine().split(";");
            dtm.addRow(str);
        }  
        indir();
    }
    
    /**
     * Aynı anda hem dosya indirme işlemi hem de ekranda ne kadarının indiğini göstermek
     * için yeni bir thread oluşturulur.
     * Dosyanın okunduğu kadarı belirtilen yere yazılır ve ne kadar okudduğuda
     * ekranda gösterilir.
     */
    public void indir()
    {
        new Thread()
        {
            public void run()
            {
                for (int i = 0; i<dtm.getRowCount(); i++)
                {
                    try
                    {
                        URL u = new URL((String) jTable1.getValueAt(i, 1));
                        URLConnection uc = u.openConnection();
                        long dosyaBoyutu = uc.getContentLength();
                        pb.setMaximum((int) dosyaBoyutu);
                        double yuzde=0;

                        File f2 = new File(""+jTable1.getValueAt(i, 0));
                        System.out.println(f2.getAbsolutePath());
                        FileOutputStream fos = new FileOutputStream(f2);
                        InputStream is = uc.getInputStream();

                        long toplamOkunan=0, anlikOkunan=0;
                        byte[] tmp = new byte[4096];
                        dtm.setValueAt("İndiriliyor", i, 2);
                        while((anlikOkunan = is.read(tmp)) !=-1)
                        {
                            toplamOkunan+=anlikOkunan;
                            fos.write(tmp, 0,(int) anlikOkunan);
                            pb.setValue((int) toplamOkunan);
                            lblToplam.setText(String.format("%d KB / %d KB", toplamOkunan,dosyaBoyutu));
                            yuzde=(toplamOkunan * 100.0 )/dosyaBoyutu;
                            lblInen.setText(String.format("%.2f", yuzde));
                        }
                        fos.close();
                        is.close();
                        dtm.setValueAt("İndirildi", i, 2);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
                JOptionPane.showMessageDialog(Downloader.this, "İndirme İşlemleri Tamamlandı");
            }
        }.start();
    }
     
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        pb = new javax.swing.JProgressBar();
        lblToplam = new javax.swing.JLabel();
        lblInen = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "İndirilen", "Link", "Durumu"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        lblToplam.setText("jLabel1");

        lblInen.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
                    .addComponent(pb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblToplam)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblInen)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pb, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblToplam)
                    .addComponent(lblInen))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[])  {
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
            java.util.logging.Logger.getLogger(Downloader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Downloader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Downloader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Downloader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Downloader().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblInen;
    private javax.swing.JLabel lblToplam;
    private javax.swing.JProgressBar pb;
    // End of variables declaration//GEN-END:variables
}
