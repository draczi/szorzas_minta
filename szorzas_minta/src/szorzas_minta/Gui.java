/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szorzas_minta;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Drácz István
 */
public class Gui extends JFrame{
    private JFrame jf = new JFrame();
    private JPanel one = new JPanel();
    private JPanel two = new JPanel();
    private JPanel three = new JPanel();
    private static JSpinner firstSp = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE , 1));
    private static JSpinner secondSp = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE , 1));
    private static JButton calculateBtn = new JButton("Calculate");
    private static JTabbedPane tp = new JTabbedPane();
    private static JTable table = new JTable();
    private static DefaultTableModel model = new DefaultTableModel();
     private static Object[] columns = {"1. szám", "2. szám", "Eredmény"};
    private static JScrollPane jSPane = new JScrollPane(table);
    static JProgressBar jpb = new JProgressBar(0, 100);
    private static JButton dbbe = new JButton("DB-be tol");
    private static JButton dbki = new JButton("DB-Ből tol");
    private static JButton fileBe = new JButton("File-ba tol");
    private static JButton fileki = new JButton("File-Ből tol");
    private static Connection con = null;

public Gui(){
      //JFrame paraméterei
      jf.setVisible(true);
      jf.setResizable(false);
      jf.setSize(500, 400);
      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //TabbedPane
      jf.add(tp);
      tp.addTab("Calculate", one);
      tp.addTab("Previous", two);
      tp.addTab("About", three);
      //TabbedPane 1. Pane elemeinek paraméterei
      one.setLayout(null);
       firstSp.setBounds(70, 100, 70, 30);
       secondSp.setBounds(190, 100, 70, 30);
       calculateBtn.setBounds(320, 100, 100, 30);
       jpb.setBounds(20, 200, 460, 40);
       // 1. Pane-re felpakolom az elemeket.
       one.add(firstSp);
       one.add(secondSp);
       one.add(calculateBtn);
       one.add(jpb);
      //2. Pane és elemeinek paraméterei
      two.setLayout(null);
       model.setColumnIdentifiers(columns);
       table.setModel(model);
       Font f_table = new Font("",1,16);
       table.setFont(f_table);
       table.setRowHeight(30);
       table.setBackground(Color.WHITE);
       table.setForeground(Color.black);
       table.setEnabled(false);
       jSPane.setBounds(0, 0, 500, 250);
       Font btn_adat = new Font("",1,16);
       dbbe.setBounds(80, 250, 150, 20);
       dbki.setBounds(280, 250, 150, 20);
       dbbe.setBackground(Color.yellow);
       dbki.setBackground(Color.yellow);
       fileBe.setBounds(80, 300, 150, 20);
       fileki.setBounds(280, 300, 150, 20);
       fileBe.setBackground(Color.green);
       fileki.setBackground(Color.green);
       //2.Panere felteszi az elemeket.
       two.add(jSPane);
       two.add(dbbe);
       two.add(dbki);
       two.add(fileBe);
       two.add(fileki);
      //3. Pane About
      three.setLayout(null);
      JLabel nameJLb = new JLabel("Drácz István");
      JLabel emailJLb = new JLabel("istvan.dracz@gmail.com");
      nameJLb.setBounds(70, 100, 200, 20);
      emailJLb.setBounds(70, 150, 200, 20);
      three.add(nameJLb);
      three.add(emailJLb);
      
      
  }
public static void calculate() {
      Object[] row = new Object[3];
        calculateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
           Thread hatterSzal;
             hatterSzal = new Thread(new Runnable() {
                 @Override
                 public void run() {
                     for (int i = 0; i <= 100; i++) {
                         try {
                             Thread.sleep(1);
                             jpb.setStringPainted(true);
                         } catch (InterruptedException ex) {
                             Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                         }
                         final int progressValue = i;
                         SwingUtilities.invokeLater(new Runnable() {
                             @Override
                             public void run() {
                                 jpb.setValue(progressValue);
                             }
                         });
                    }     
                    row[0] = firstSp.getValue().toString();
                    row[1] = secondSp.getValue().toString();
                    row[2] = Szorzas_minta.multiplyNatural(Integer.parseInt(firstSp.getValue().toString()), Integer.parseInt(secondSp.getValue().toString()));
                    model.addRow(row);
                    calculateBtn.setEnabled(true);
                    firstSp.setEnabled(true);
                    secondSp.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "Az eredmeny: " + row[2]);
                }
            });
            calculateBtn.setEnabled(false);
            firstSp.setEnabled(false);
            secondSp.setEnabled(false);
            hatterSzal.start();
        }
    });
  }
  public static void database() {
      dbbe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             PreparedStatement pst = null;
              try {
                   Statement stmt = con.createStatement();
                   stmt.executeUpdate("delete from szorzas.dump");
                  pst = (PreparedStatement) con.prepareStatement("INSERT INTO szorzas.dump" + "(F, S, P)" + " VALUES(?,?,?)"); 
                  for (int i = 0; i < table.getModel().getRowCount(); i++) {
                      pst.setString(1, table.getModel().getValueAt(i, 0).toString());
                      pst.setString(2, table.getModel().getValueAt(i, 1).toString());
                      pst.setString(3, table.getModel().getValueAt(i, 2).toString());
                           pst.executeUpdate();
                  }
             
              } catch (SQLException ex) {
                  Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
              }
              model.setRowCount(0);
              JOptionPane.showMessageDialog(null, "Sikeres mentés!");
          }
      });
      dbki.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PreparedStatement pst = null;
                 model.setRowCount(0);
                try {
                    String sql = "SELECT * FROM szorzas.dump";
                    pst = (PreparedStatement) con.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery(sql);
                    while(rs.next()) {
                        model.addRow(new Object[]{
                          rs.getString(1),rs.getString(2),rs.getString(3)
                  });
                  }
                  table.setModel(model);
                } catch (SQLException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
  }
  private static void file() {
          fileBe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Vector<String> columnNames = new Vector<String>();
                for (int i = 0; i < model.getColumnCount(); i++) {
                    columnNames.add(model.getColumnName(i)); 
                }
                try {
                    FileOutputStream fout = new FileOutputStream("adatbazis.dat");
                   try {
                       ObjectOutputStream oos = new ObjectOutputStream(fout);
                       oos.writeObject(model.getDataVector());
                       oos.writeObject(columnNames);
                       oos.close();
                       fout.close();
                   } catch (IOException ex) {
                       Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                   }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                }
                model.setRowCount(0);
                JOptionPane.showMessageDialog(null, "Sikeres mentés!");
                
            }
        });
          fileki.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if(model != null) {
                        model.setRowCount(0);
                    }
                try {
                   FileInputStream fis = new FileInputStream("adatbazis.dat");
                   try {
                       ObjectInputStream ois = new ObjectInputStream(fis);
                       try {
                           model.setDataVector((Vector)ois.readObject(), (Vector)ois.readObject());
                       } catch (ClassNotFoundException ex) {
                           Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                       }
                   } catch (IOException ex) {
                       Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                   }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                } model.fireTableDataChanged();
            }
        });
      }
  public static void initDatabase() throws SQLException {
      con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root&password=root");
      if(con != null ) {
          con.setAutoCommit(true);
          Statement dbStatement = con.createStatement();
          dbStatement.executeUpdate("CREATE DATABASE if NOT EXISTS SZORZAS");
          dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS szorzas.dump (F integer, S integer, P integer)");
      }
  }
    
  public static void main(String[] args) {
        try {
            initDatabase();
            } catch (SQLException ex) {
            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
        }
            Gui g = new Gui();
            calculate();
            database();
            file();
        
   }
}
