import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
public class LandLordDatabase extends DormManagement {

    private JPanel contentPane;
    private JTable tenantsTable;
    private JTable visitorsTable;
    private DefaultTableModel tenantsModel;
    private DefaultTableModel visitorsModel;

    public LandLordDatabase(Boolean received) {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Tenants Text file
        String tenantsFilePath = "C:\\Users\\gio4g\\OneDrive\\Desktop\\TenantsData.txt";
        File file1 = new File(tenantsFilePath);

        // Visitors Text file
        String visitorsFilePath = "C:\\Users\\gio4g\\OneDrive\\Desktop\\VisitorsData.txt";
        File file2 = new File(visitorsFilePath);
        
        //Table models
        

        // Tenants JTable Panel
        JPanel tenantsPanel = new JPanel();
        tenantsPanel.setBounds(0, 104, 1194, 659);
        contentPane.add(tenantsPanel);
        tenantsPanel.setVisible(false);
        tenantsPanel.setLayout(null);

        // Tenants table ScrollPane
        JScrollPane tenantScrollPane = new JScrollPane();
        tenantScrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tenantScrollPane.setBounds(110, 30, 975, 551);
        tenantsPanel.add(tenantScrollPane);

        // Tenants Table
        tenantsTable = new JTable();
        tenantsModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Room no.", "Name", "Contact", "Due date", "Rent status", "In", "Out"}
        );
        tenantsTable.setModel(tenantsModel);
        tenantScrollPane.setViewportView(tenantsTable);
        
        tenantsModel = (DefaultTableModel) tenantsTable.getModel();

        // Add Button Functions and Declaration
        JButton tenantsAddBtn = new JButton("Add");
        tenantsAddBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int rowCount = tenantsTable.getRowCount();
                    AddFrame addPage = new AddFrame(rowCount);
                    addPage.setVisible(true);
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Update Button Functions and Declaration
        JButton tenantsUpdateBtn = new JButton("Update");
        tenantsUpdateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int selectedRow = tenantsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to update.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                       
                String dueDate = JOptionPane.showInputDialog("Enter Due Date: " );
                String rentStatus = JOptionPane.showInputDialog("Enter time out: ");
                tenantsModel.setValueAt(dueDate, selectedRow, 3); 
                tenantsModel.setValueAt(rentStatus, selectedRow, 4); 
                
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file1, true));
                        BufferedReader br = new BufferedReader(new FileReader(file1))) {

                       // Read the lines of the text file into a List
                       List<String> lines = new ArrayList<>();
                       String line;
                       while ((line = br.readLine()) != null) {
                           lines.add(line);
                       }

                       // Get the line to be updated
                       String lineToUpdate = lines.get(selectedRow);   
    
                       // Update the values in the line
                       String[] columns = lineToUpdate.split("/");
                       if (columns.length >= 7) { // Check if the array has enough elements
                           columns[3] = dueDate; 
                           columns[4] = rentStatus; 
                       }   

                       // Join the columns back into a line
                       String updatedLine = String.join("/", columns);
                       
                       // Update the line in the List
                       lines.set(selectedRow, updatedLine);
                       
                       // Write the updated lines back to the text file
                       try (BufferedWriter writer = new BufferedWriter(new FileWriter(file1))) {
                           for (String updatedFileLine : lines) {
                               writer.write(updatedFileLine);
                               writer.newLine();
                           }
                       }

                   } catch (Exception ex) {
                       ex.printStackTrace();
                   }
            }
        });

        // Delete Button Functions and Declaration
        JButton tenantsDeleteBtn = new JButton("Delete");
        tenantsDeleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform delete action here

                int selectedRow = tenantsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Remove the selected row from the table model
                tenantsModel.removeRow(selectedRow);
                
                

            	
            }
        });

        // Add, Update, Delete Buttons Settings
        JButton tenantsBackBtn = new JButton("Back");
        tenantsBackBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle back button action here
            }
        });
        
      //Add,Update,Delete Buttons Settings
      		
        tenantsAddBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));    		
        tenantsAddBtn.setBorder(new LineBorder(new Color(0, 0, 0)));    		
        tenantsAddBtn.setFont(new Font("Tahoma", Font.PLAIN, 15));     		
        tenantsAddBtn.setForeground(Color.WHITE);     		
        tenantsAddBtn.setBackground(new Color(33, 150, 243));      		
        tenantsAddBtn.setBounds(633, 591, 144, 35);      		
        tenantsPanel.add(tenantsAddBtn);      			
      		
        tenantsUpdateBtn.setFont(new Font("Tahoma", Font.PLAIN, 15));      		
        tenantsUpdateBtn.setForeground(Color.WHITE); 		
        tenantsUpdateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));  		
        tenantsUpdateBtn.setBorder(new LineBorder(new Color(0, 0, 0)));   		
        tenantsUpdateBtn.setBackground(new Color(33, 150, 243));   		
        tenantsUpdateBtn.setBounds(787, 591, 144, 35);     		
        tenantsPanel.add(tenantsUpdateBtn);
      				     		
        tenantsDeleteBtn.setFont(new Font("SansSerif", Font.PLAIN, 15));      		
        tenantsDeleteBtn.setForeground(Color.WHITE);     		
        tenantsDeleteBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));    		
        tenantsDeleteBtn.setBorder(new LineBorder(new Color(0, 0, 0)));     		
        tenantsDeleteBtn.setBackground(new Color(204, 25, 25));      		
        tenantsDeleteBtn.setBounds(941, 591, 144, 35);      		
        tenantsPanel.add(tenantsDeleteBtn);
        
        JButton TenantsBackBtn = new JButton("Back");     		
        TenantsBackBtn.addActionListener(new ActionListener() {      			
        	public void actionPerformed(ActionEvent e) {
        		DormManagement back = new DormManagement();
        		back.setVisible(true);
        		dispose();
        	}     		
        });      		
        TenantsBackBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));      		
        TenantsBackBtn.setForeground(Color.WHITE);      		
        TenantsBackBtn.setFont(new Font("Tahoma", Font.PLAIN, 15));   		
        TenantsBackBtn.setBorder(new LineBorder(new Color(0, 0, 0)));    		
        TenantsBackBtn.setBackground(new Color(33, 150, 243));     		
        TenantsBackBtn.setBounds(110, 591, 144, 35);      		
        tenantsPanel.add(TenantsBackBtn);
       

        // Visitors JTable Panel
        JPanel visitorsPanel = new JPanel();
        visitorsPanel.setBounds(0, 104, 1194, 659);
        contentPane.add(visitorsPanel);
        visitorsPanel.setVisible(false);
        visitorsPanel.setLayout(null);

        // Visitors Table ScrollPane
        JScrollPane visitorsScrollPane = new JScrollPane();
        visitorsScrollPane.setBounds(110, 25, 975, 551);
        visitorsPanel.add(visitorsScrollPane);

        // Visitors Table
        visitorsTable = new JTable();
        visitorsModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Name", "Temperature", "Date", "Time in", "Time out"}
        );
        visitorsTable.setModel(visitorsModel);
        visitorsScrollPane.setViewportView(visitorsTable);

        visitorsModel = (DefaultTableModel) visitorsTable.getModel();
        
        JButton backBtn = new JButton("Back");        
        backBtn .addActionListener(new ActionListener() {      			
        	public void actionPerformed(ActionEvent e) {
        		DormManagement back = new DormManagement();
        		back.setVisible(true);
        		dispose();
        	}     		
        }); 
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Tahoma", Font.PLAIN, 15));
        backBtn.setBorder(new LineBorder(new Color(0, 0, 0)));
        backBtn.setBackground(new Color(33, 150, 243));
        backBtn.setBounds(110, 586, 134, 36);
        visitorsPanel.add(backBtn);
        
        
 
        JButton updateBtn = new JButton("Update");
        updateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int selectedRow = visitorsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to update.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String timeOut = JOptionPane.showInputDialog("Enter time out: ");
                visitorsModel.setValueAt(timeOut, selectedRow, 4); // Update column 5 (index 4)

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file2, true));
                     BufferedReader br = new BufferedReader(new FileReader(file2))) {

                    // Read the lines of the text file into a List
                    List<String> lines = new ArrayList<>();
                    String line;
                    while ((line = br.readLine()) != null) {
                        lines.add(line);
                    }

                    // Get the line to be updated
                    String lineToUpdate = lines.get(selectedRow);   
 
                    // Update the values in the line
                    String[] columns = lineToUpdate.split("/");
                    if (columns.length >= 5) { // Check if the array has enough elements
                        columns[4] = timeOut; // Update column 5 (index 4)
                        
                    } 

                    // Join the columns back into a line
                    String updatedLine = String.join("/", columns);
                    
                    // Update the line in the List
                    lines.set(selectedRow, updatedLine);
                   
                    // Write the updated lines back to the text file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file2))) {
                        for (String updatedFileLine : lines) {
                            writer.write(updatedFileLine);
                            writer.newLine();
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

   
        updateBtn.setForeground(Color.WHITE);
        updateBtn.setFont(new Font("Tahoma", Font.PLAIN, 15));
        updateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        updateBtn.setBorder(new LineBorder(new Color(0, 0, 0)));
        updateBtn.setBackground(new Color(33, 150, 243));
        updateBtn.setBounds(807, 586, 134, 36);
        visitorsPanel.add(updateBtn);
        
        JButton visitorsDeleteBtn = new JButton("Delete");
        visitorsDeleteBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
              int selectedRow = visitorsTable.getSelectedRow();
              if (selectedRow == -1) {
                  JOptionPane.showMessageDialog(null, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                  return;
              }

              // Remove the selected row from the table model
              visitorsModel.removeRow(selectedRow);
        	}
        });
        visitorsDeleteBtn.setForeground(Color.WHITE);
        visitorsDeleteBtn.setFont(new Font("Tahoma", Font.PLAIN, 15));
        visitorsDeleteBtn.setBorder(new LineBorder(new Color(0, 0, 0)));
        visitorsDeleteBtn.setBackground(new Color(204, 25, 25));
        visitorsDeleteBtn.setBounds(951, 586, 134, 36);
        visitorsPanel.add(visitorsDeleteBtn);

        JButton tenantsDatabaseBtn = new JButton("");
        tenantsDatabaseBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tenantsDatabaseBtn.setBorder(null);
        tenantsDatabaseBtn.setBounds(0, 0, 600, 105);
        tenantsDatabaseBtn.setContentAreaFilled(false);
        contentPane.add(tenantsDatabaseBtn);
        tenantsDatabaseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tenantsPanel.setVisible(true);
                visitorsPanel.setVisible(false);
            }
        });
        
        JButton visitorsDatabaseBtn = new JButton("");
        visitorsDatabaseBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        visitorsDatabaseBtn.setContentAreaFilled(false);
        visitorsDatabaseBtn.setBorder(null);
        visitorsDatabaseBtn.setBounds(603, 0, 600, 105);
        contentPane.add(visitorsDatabaseBtn);
        visitorsDatabaseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              
				visitorsPanel.setVisible(true);
                tenantsPanel.setVisible(false);
            }
        });

        // Read tenants text file and display
        if (received) {
            try {
          
                BufferedReader br = new BufferedReader(new FileReader(file1));
                Object[] tenantsTableLines = br.lines().toArray();
                for (int i = 0; i < tenantsTableLines.length; i++) {
                    String line = tenantsTableLines[i].toString().trim();
                    String[] dataRow = line.split("/");
                    tenantsModel.addRow(dataRow);
                }
                br.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Read visitors text file and display
        if (received) {
            try {
                
                BufferedReader br = new BufferedReader(new FileReader(file2));
                Object[] visitorsTableLines = br.lines().toArray();
                for (int i = 0; i < visitorsTableLines.length; i++) {
                    String line = visitorsTableLines[i].toString().trim();
                    String[] dataRow = line.split("/");
                    visitorsModel.addRow(dataRow);
                }
                br.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        JLabel background = new JLabel("");
        background.setIcon(new ImageIcon("C:\\Users\\gio4g\\OneDrive\\Desktop\\A\\B\\dashboard.png"));
        background.setBounds(0, 0, 1258, 812);
        contentPane.add(background);
    }
    
    
}


