
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Scanner;

import java.lang.*;
import java.util.*;

public class LZ77 extends javax.swing.JFrame {

    public LZ77() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Encryption ");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setText("Decryption ");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("MS PGothic", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 0, 0));
        jLabel1.setText("  Please Enter your choice");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 153));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(71, 71, 71))
            .addGroup(layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    Scanner sc;

    public void open_file(String FileName) {
        try {
            sc = new Scanner(new File(FileName));
        } catch (Exception e) {
            jLabel2.setText("Can not find File ");
        }
    }

    public void close_file() {
        sc.close();
    }

    Formatter out; 

    public void openfile(String pass) {
        try {
            out = new Formatter(pass);
        } catch (Exception e) {
            jLabel2.setText("Can not find File ");
        }

    }

    public void closefile() {
        out.close();
    }

    public void write_tag(int pointer, int length, char next_sym) {
      
        String s = pointer + "," + length + "," + next_sym;
        out.format("%s", s);
        out.format("%n");
        out.flush(); 

    }

    public void encrypt(String data) {
       
        int indx = 0;
        String cur = ""; 
        cur += data.charAt(indx);
        int begin_of_cur = indx; 
        openfile("Compressed Data.txt");
        
        
        while (indx <data.length() )
        {   
            if ( cur.length() == 0) 
            {   
                cur += data.charAt(indx);
                begin_of_cur = indx; 
                
            }

            String substring = data.substring(0, begin_of_cur); 
            int start = substring.lastIndexOf(cur); 
            int pointer = 0, length = 0;
            char next_sym;
            
            
            int length_of_Buffer = begin_of_cur ;
            int last_indx_of = start ; 
            
            while ( (start != -1 ) && (indx < data.length()-1) )
            {   
                last_indx_of = start ;
                cur += data.charAt(++indx); 
                substring = data.substring(0,++length_of_Buffer); 
                start = substring.lastIndexOf(cur); 
                
            }
       
            {  
                if (cur.length()== 1) 
                {   
                    next_sym = cur.charAt(0);
                    pointer = length = 0;
                    indx++;
                    cur =""; 
                    write_tag(pointer, length, next_sym);
                } 
                
                else
                {
                    next_sym = cur.charAt(cur.length() - 1); 
                    pointer = begin_of_cur - last_indx_of ; 
                    length = cur.length() - 1; 
                    indx++; 
                    cur = ""; 
                    write_tag(pointer, length, next_sym);
                    
                }
            
            }

        }
        
           closefile();
   }
    

    public void read_file(String FileName){ // read data file
       
        open_file(FileName);
        String data = "";

        while (sc.hasNext()) // while it's not the end of file 
        {
            String cur = sc.next(); // read line then append " ignore white spaces "
            data += cur;
        }

        encrypt(data);
        close_file();

    }

    String res = "";

    public void decrypt(int pointer, int length, char next_sym) {

        if (pointer == 0) 
        {
            res += next_sym;
        }
        else
        {
            int start = res.length() - pointer;  
            int rest = res.length() - start ;  
            String part = "";
            int indx = start;

            if (length > rest) // handling the recursion case 
            {   
                String rest_word = res.substring(start); 
                int j = 0 ; 

                for (int i=1 ; i<= length ; i++) 
                {
                    res+=rest_word.charAt(j); 
                    j++;
                   
                    if (j >= rest_word.length()) 
                    {
                        j=0;
                    }
                }
                
                res+=next_sym; 
            }
            else // it is not recursion case
            {
                for (int i = 1; i <= length; i++) 
                {
                    part += res.charAt(indx); 
                    indx++;
                }

                   res += part; 
                   res += next_sym;
            
            }

        }

    }

    public void write_res() {
       
        openfile("Original Data.txt"); 
        out.format("%s", res);
        out.flush();
        closefile();

    }

    public void read_compressed_data(String pass) 
    {
        open_file(pass);

        while (sc.hasNext()) // while it's not the end of file 
        {
            String line = sc.next();

            int pointer = Integer.parseInt(line.substring(0, line.indexOf(","))); 
            line = line.substring(line.indexOf(",") + 1, line.length());

            int length = Integer.parseInt(line.substring(0, line.indexOf(",")));
            line = line.substring(line.indexOf(",") + 1, line.length());

            char next_sym = line.charAt(line.length() - 1); 

            decrypt( pointer, length, next_sym);

        }

        write_res();
        close_file();

    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        read_file("Original Data.txt"); // compress data

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        read_compressed_data("Compressed Data.txt"); // decompress data
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(LZ77.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LZ77.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LZ77.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LZ77.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LZ77().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
