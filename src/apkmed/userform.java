package apkmed;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


/**
 *
 * @author Lukas
 */
public class userform extends javax.swing.JFrame {

        private static final String USERNAME = "root";
        private static final String PASSWORD = "";
        private static final String CONN_STRING = "jdbc:mysql://localhost:3306/hurtownia?useUnicode=yes&characterEncoding=UTF-8";
        private int option = 0;

        /**
         * Creates new form userform
         */
        public userform() throws SQLException {
            initComponents();
            jComboBox1.setVisible(false);
            jLabel5.setVisible(false);
            jButton1.setEnabled(false);
            jTable1.setAutoCreateRowSorter(true);
            GetData();

        }
        public static boolean isNumeric(String strNum) {
            if (strNum == null) {
                return false;
            }
            try {
                double d = Double.parseDouble(strNum);
            } catch (NumberFormatException nfe) {
                return false;
            }
            return true;
        }

        public void adjustComboBox() {
            jComboBox1.removeAllItems();
            Connection connection = getConnection();
            String query = "";
            if (option == 1) query = "SELECT * FROM `przychodnia` ";
            else if (option == 2) query = "SELECT * FROM `lekarz` ";
            Statement st;
            ResultSet rs;
            if (option == 1 || option == 2) {
                try {
                    st = connection.createStatement();
                    rs = st.executeQuery(query);
                    Dataclass dc;
                    while (rs.next()) {
                        jComboBox1.addItem(rs.getInt("ID"));
                    }

                } catch (Exception e) {
                    System.err.print(e);
                }

            }
        }

        // get the connection
        public Connection getConnection() {

            com.mysql.jdbc.Connection conn = null;
            try {
                conn = (com.mysql.jdbc.Connection) DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
                return conn;
            } catch (SQLException e) {
                System.err.print(e);
                return null;
            }
        }

        // get a list of users from mysql database
        public ArrayList < Dataclass > getData() {
            ArrayList < Dataclass > dataList = new ArrayList < Dataclass > ();
            Connection connection = getConnection();
            String query = "SELECT * FROM `przychodnia` ";
            if (option == 1) query = "SELECT * FROM `lekarz` ";
            if (option == 2) query = "SELECT * FROM `wizyta` ";
            if (option == 3) query = "SELECT * FROM `badanie` ";
            Statement st;
            ResultSet rs;

            try {
                st = connection.createStatement();
                rs = st.executeQuery(query);
                Dataclass dc;
                while (rs.next()) {
                    if (option == 1) dc = new Dataclass(rs.getInt("ID"), rs.getString("Imię"), rs.getString("Nazwisko"), rs.getString("Specjalizacja"), rs.getInt("ID_Przychodni"));
                    else if (option == 2) dc = new Dataclass(rs.getInt("ID"), rs.getString("Data"), rs.getString("Opis Badania"), rs.getString("Imię i nazwisko pacjenta"), rs.getInt("ID_Lekarza"));
                    else if (option == 3) dc = new Dataclass(rs.getInt("ID"), rs.getString("Data"), rs.getBlob("Załącznik"));
                    else dc = new Dataclass(rs.getInt("ID"), rs.getString("Nazwa"), rs.getString("Adres"), rs.getString("Miasto"));
                    dataList.add(dc);
                }
            } catch (Exception e) {
                System.err.print(e);
            }
            return dataList;
        }

        // Display Data In JTable

        public void GetData() throws SQLException {
            ArrayList < Dataclass > list = getData();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            int size = 4;
            if (option == 1 || option == 2) size = 5;
            if (option == 3) size = 4;
            adjustComboBox();
            Object[] row = new Object[size];
            ArrayList < Blob > blobbs = new ArrayList < Blob > ();
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getField1();
                row[1] = list.get(i).getField2();
                row[2] = list.get(i).getField3();
                if (option == 3) {
                    row[2] = list.get(i).getBlob().length();
                    blobbs.add(list.get(i).getBlob());
                }
                if (option != 3) row[3] = list.get(i).getField4();
                if (option == 1 || option == 2) row[4] = list.get(i).getField5();
                model.addRow(row);
            }

            if (option == 3) {
                jTable1.getColumn("Pobierz").setCellRenderer(new ButtonRenderer());
                jTable1.getColumn("Pobierz").setCellEditor(
                    new ButtonEditor(new JCheckBox(), blobbs));
            }


        }

        // Execute The Insert Update And Delete Querys
        public void executeSQlQuery(String query, String message) {
            Connection con = getConnection();
            Statement st;
            try {
                st = con.createStatement();
                if ((st.executeUpdate(query)) == 1) {
                    // refresh jtable data
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    model.setRowCount(0);
                    GetData();

                    JOptionPane.showMessageDialog(null, message);
                } else {
                    JOptionPane.showMessageDialog(null, "Wystapił błąd");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Zostały wprowadzone niepoprawne dane");

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

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jButton1.setBackground(new java.awt.Color(102, 102, 102));
        jButton1.setText("Przychodnie");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(102, 102, 102));
        jButton2.setText("Lekarze");
        jButton2.setFocusPainted(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(102, 102, 102));
        jButton3.setText("Wyniki badań");
        jButton3.setFocusPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(102, 102, 102));
        jButton4.setText("Wizyty");
        jButton4.setFocusPainted(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nazwa", "Adres", "Miasto"
            }
        ){
            @Override
            public boolean isCellEditable(int row, int column) {

                jTable1 = new javax.swing.JTable();

                jTable1.setModel(new javax.swing.table.DefaultTableModel(
                    new Object [][] {

                    },
                    new String [] {
                        "ID", "Nazwa", "Adres", "Miasto"
                    }
                ){
                    @Override
                    public boolean isCellEditable(int row, int column){
                        return false;
                    }
                });

                jTable1.setEditingColumn(0);

                jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        jTable1MouseClicked(evt);
                    }
                });

                jScrollPane1.setViewportView(jTable1);

                //all cells false
                return false;
            }
        });
        jTable1.setEditingColumn(0);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jTextField1.setToolTipText("");
        jTextField1.setEnabled(false);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField3.setToolTipText("");
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jTextField4.setToolTipText("");
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jTextField2.setToolTipText("");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel1.setText("ID");

        jLabel2.setText("Nazwa");

        jLabel3.setText("Adres");

        jLabel4.setText("Miasto");

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/del.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/upd.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel5.setText("Miasto");

        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField5KeyReleased(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/fin.png"))); // NOI18N
        jLabel6.setText("Wyszukaj frazę");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 75, Short.MAX_VALUE)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField4)
                            .addComponent(jTextField2)
                            .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(16, 16, 16)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                    .addComponent(jTextField5)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(2, 2, 2)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        option=0;
        jLabel1.setVisible(true);
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);
        jLabel4.setVisible(true);
        jLabel5.setVisible(true);
        jTextField1.setVisible(true);
        jTextField2.setVisible(true);
        jTextField3.setVisible(true);
        jTextField4.setVisible(true);
        jButton7.setVisible(true);
        jButton8.setVisible(true);
        jButton9.setVisible(true);
        jLabel2.setText("Nazwa");
        jLabel3.setText("Adres");
        jLabel4.setText("Miasto");
        jLabel5.setVisible(false);
        
        jComboBox1.setVisible(false);
        
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
    new Object [][] {

    },
    new String [] {
        "ID", "Nazwa", "Adres", "Miasto"
    }
){
    @Override
    public boolean isCellEditable(int row, int column) {
        //all cells false
        return false;
    }
});
        
        try {
            GetData();
        } catch (SQLException ex) {
            Logger.getLogger(userform.class.getName()).log(Level.SEVERE, null, ex);
        }
        jButton1.setEnabled(false);
        jButton2.setEnabled(true);
        jButton4.setEnabled(true);
        jButton3.setEnabled(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        option=1;
        jLabel1.setVisible(true);
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);
        jLabel4.setVisible(true);
        jLabel5.setVisible(true);
        jTextField1.setVisible(true);
        jTextField2.setVisible(true);
        jTextField3.setVisible(true);
        jTextField4.setVisible(true);
        jButton7.setVisible(true);
        jButton8.setVisible(true);
        jButton9.setVisible(true);
        jLabel2.setText("Imię");
        jLabel3.setText("Nazwisko");
        jLabel4.setText("Specjalizacja");
        jLabel5.setText("ID_Przychodni");
        jLabel5.setVisible(true);
        jComboBox1.setVisible(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
    new Object [][] {

    },
    new String [] {
        "ID", "Imię", "Nazwisko", "Specjalizacja", "ID_Przychodni"
    }
){
    @Override
    public boolean isCellEditable(int row, int column) {
        //all cells false
        return false;
    }
});
        try {
            GetData();
        } catch (SQLException ex) {
            Logger.getLogger(userform.class.getName()).log(Level.SEVERE, null, ex);
        }
        jButton2.setEnabled(false);
        jButton1.setEnabled(true);
        jButton3.setEnabled(true);
        jButton4.setEnabled(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
          // Get The Index Of The Slected Row 
        int i = jTable1.getSelectedRow();
        
        TableModel model = jTable1.getModel();
        if(option!=3){
         // Display Slected Row In JTexteFields
        jTextField1.setText(model.getValueAt(i,0).toString());

        jTextField2.setText(model.getValueAt(i,1).toString());

        jTextField3.setText(model.getValueAt(i,2).toString());

        jTextField4.setText(model.getValueAt(i,3).toString());
        
        if(option==1) jComboBox1.setSelectedItem(model.getValueAt(i,4));
       }
    }//GEN-LAST:event_jTable1MouseClicked
    // Button Insert
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
          String query = "INSERT INTO `przychodnia`(`Nazwa`, `Adres`, `Miasto`) VALUES ('"+jTextField2.getText()+"','"+jTextField3.getText()+"','"+jTextField4.getText()+"')";
          if(option==1) query = "INSERT INTO `lekarz`(`Imię`, `Nazwisko`, `Specjalizacja`, `ID_Przychodni`) VALUES ('"+jTextField2.getText()+"','"+jTextField3.getText()+"','"+jTextField4.getText()+"',"+String.valueOf(jComboBox1.getSelectedItem())+")";
          if(option==2) query = "INSERT INTO `wizyta`(`Data`, `Opis badania`, `Imię i nazwisko pacjenta`, `ID_Lekarza`) VALUES ('"+jTextField2.getText()+"','"+jTextField3.getText()+"','"+jTextField4.getText()+"',"+String.valueOf(jComboBox1.getSelectedItem())+")";
          executeSQlQuery(query, "Dodano pomyślnie");
    }//GEN-LAST:event_jButton7ActionPerformed
    // Button Update
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
       if(isNumeric(jTextField1.getText()))
       {        String query = "UPDATE `przychodnia` SET `Nazwa`='"+jTextField2.getText()+"',`Adres`='"+jTextField3.getText()+"',`Miasto`='"+jTextField4.getText()+"' WHERE `ID` = "+jTextField1.getText();
                if(option==1) query = "UPDATE `lekarz` SET `Imię`='"+jTextField2.getText()+"',`Nazwisko`='"+jTextField3.getText()+"',`Specjalizacja`='"+jTextField4.getText()+"', `ID_Przychodni`="+String.valueOf(jComboBox1.getSelectedItem())+" WHERE `ID` = "+jTextField1.getText();
                if(option==2) query = "UPDATE `wizyta` SET `Data`='"+jTextField2.getText()+"',`Opis badania`='"+jTextField3.getText()+"',`Imię i nazwisko pacjenta`='"+jTextField4.getText()+"', `ID_Lekarza`="+String.valueOf(jComboBox1.getSelectedItem())+" WHERE `ID` = "+jTextField1.getText();
                executeSQlQuery(query, "Zaktualizowano pomyślnie");
       }
       else{
           JOptionPane.showMessageDialog(null, "Wprowadź ID występujące w bazie");
       }
    }//GEN-LAST:event_jButton9ActionPerformed
     // Button Delete
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if(isNumeric(jTextField1.getText())){
        String query = "DELETE FROM `przychodnia` WHERE id = "+jTextField1.getText();
        if(option==1) query = "DELETE FROM `lekarz` WHERE id = "+jTextField1.getText();
        if(option==2) query = "DELETE FROM `wizyta` WHERE id = "+jTextField1.getText();
         executeSQlQuery(query, "Usunięto pomyślnie");}
        else{
           JOptionPane.showMessageDialog(null, "Wprowadź ID występujące w bazie");
       }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyReleased
        DefaultTableModel table = (DefaultTableModel)jTable1.getModel();
       String search = jTextField5.getText().toLowerCase();
       TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(table);
       jTable1.setRowSorter(tr);
       tr.setRowFilter(RowFilter.regexFilter(search));
    }//GEN-LAST:event_jTextField5KeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        option=2;
        jLabel1.setVisible(true);
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);
        jLabel4.setVisible(true);
        jLabel5.setVisible(true);
        jTextField1.setVisible(true);
        jTextField2.setVisible(true);
        jTextField3.setVisible(true);
        jTextField4.setVisible(true);
        jButton7.setVisible(true);
        jButton8.setVisible(true);
        jButton9.setVisible(true);
        jLabel2.setText("Data[yyyy-mm-dd]");
        jLabel3.setText("Opis badania");
        jLabel4.setText("Imię i nazwisko pacjenta");
        jLabel5.setText("ID_lekarza");
        jLabel5.setVisible(true);
        jComboBox1.setVisible(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Data", "Opis badania", "Imię i nazwisko pacjenta", "ID_lekarza"
            }
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        });
        try {
            GetData();
        } catch (SQLException ex) {
            Logger.getLogger(userform.class.getName()).log(Level.SEVERE, null, ex);
        }
        jButton4.setEnabled(false);
        jButton2.setEnabled(true);
        jButton1.setEnabled(true);
        jButton3.setEnabled(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        option=3;
        jLabel1.setVisible(false);
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        jLabel5.setVisible(false);
        jTextField1.setVisible(false);
        jTextField2.setVisible(false);
        jTextField3.setVisible(false);
        jTextField4.setVisible(false);
        jButton7.setVisible(false);
        jButton8.setVisible(false);
        jButton9.setVisible(false);
        jComboBox1.setVisible(false);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Data", "Rozmiar", "Pobierz"            }
        ){
            @Override
            public boolean isCellEditable(int row, int column) {

                
                if(column==3) return true;
                else return false;
            }
        });
        try {
            GetData();
        } catch (SQLException ex) {
            Logger.getLogger(userform.class.getName()).log(Level.SEVERE, null, ex);
        }
        jButton4.setEnabled(true);
        jButton3.setEnabled(false);
        jButton2.setEnabled(true);
        jButton1.setEnabled(true);
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(userform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(userform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(userform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(userform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new userform().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(userform.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
