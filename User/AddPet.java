/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import Admin.*;
import config.Session;
import config.dbConnector;
import java.awt.Color;
import static java.awt.Color.pink;
import static java.awt.Color.red;
import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;
import petcare.loginForm;



/**
 *
 * @author Yisha
 */
public class AddPet extends javax.swing.JFrame {

    /**
     * Creates new form adminDashboard
     */
    public AddPet() {
        initComponents();
        displayData();
        displayPetData();
        loadPets();
        loadOwners();
    }
    
public void displayData(){
    try{
        dbConnector dbc = new dbConnector();
        ResultSet rs = dbc.getData("SELECT owner_id, owner_fname, owner_lname, owner_contact FROM tbl_owner");
        oTable.setModel(DbUtils.resultSetToTableModel(rs));
        rs.close();
    } catch(SQLException ex){
        System.out.println("Errors: " + ex.getMessage());
    }
}



        
        
        public void displayPetData(){
        try{
            dbConnector dbc = new dbConnector();
            ResultSet rs = dbc.getData("SELECT pet_id, owner_id, pet_name, pet_color, pet_breed, pet_age FROM tbl_pet");
            pTable.setModel(DbUtils.resultSetToTableModel(rs));
             rs.close();
        }catch(SQLException ex){
            System.out.println("Errors: "+ex.getMessage());

        }    
        

    }
        
        

        
        
        
        private void ownerTableMouseClicked(java.awt.event.MouseEvent evt) {
    int row = oTable.getSelectedRow();
    String selectedOwnerID = oTable.getValueAt(row, 0).toString(); // assuming ID is column 0
    oid.setText(selectedOwnerID); // sets the text field with the selected owner ID
}
        
        public void loadOwners() {
    dbConnector dbc = new dbConnector();

    try {
        String query = "SELECT owner_id, owner_fname, owner_lname, owner_contact, owner_address FROM tbl_owner";
        ResultSet rs = dbc.getData(query);

        DefaultTableModel model = (DefaultTableModel) oTable.getModel();
        model.setRowCount(0); // Clear table rows

        while (rs.next()) {
            Object[] row = {
                rs.getString("owner_id"),
                rs.getString("owner_fname"),
                rs.getString("owner_lname"),
                rs.getString("owner_contact"),
                rs.getString("owner_address")
            };
            model.addRow(row);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error loading owners: " + e.getMessage());
    }
}

        
        
        
        public void loadPets() {
    dbConnector dbc = new dbConnector();

    try {
        String query = "SELECT pet_id, owner_id, pet_name, pet_breed, pet_age FROM tbl_pet";
        ResultSet rs = dbc.getData(query);

        DefaultTableModel model = (DefaultTableModel) pTable.getModel();
        model.setRowCount(0); // clear existing data

        while (rs.next()) {
            Object[] row = {
                rs.getString("pet_id"),
                rs.getString("owner_id"),
                rs.getString("pet_name"),
                rs.getString("pet_breed"),
                rs.getString("pet_age")
            };
            model.addRow(row);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error loading pets: " + e.getMessage());
    }
}


        
        
    boolean checkadd = true;
    
    public String destination = "";
    File selectedFile;
    public String oldpath;
    public String path;
    
    
    
public int FileExistenceChecker(String path){
        File file = new File(path);
        String fileName = file.getName();
        
        Path filePath = Paths.get("src/petImage", fileName);
        boolean fileExists = Files.exists(filePath);
        
        if (fileExists) {
            return 1;
        } else {
            return 0;
        }
    
    }


//---------------------------------------------------------------------------

public static int getHeightFromWidth(String imagePath, int desiredWidth) {
        try {
            // Read the image file
            File imageFile = new File(imagePath);
            BufferedImage image = ImageIO.read(imageFile);
            
            // Get the original width and height of the image
            int originalWidth = image.getWidth();
            int originalHeight = image.getHeight();
            
            // Calculate the new height based on the desired width and the aspect ratio
            int newHeight = (int) ((double) desiredWidth / originalWidth * originalHeight);
            
            return newHeight;
        } catch (IOException ex) {
            System.out.println("No image found!");
        }
        
        return -1;
    }

//--------------------------------------------------------------------------------------

public  ImageIcon ResizeImage(String ImagePath, byte[] pic, JLabel label) {
    ImageIcon MyImage = null;
        if(ImagePath !=null){
            MyImage = new ImageIcon(ImagePath);
        }else{
            MyImage = new ImageIcon(pic);
        }
        
    int newHeight = getHeightFromWidth(ImagePath, label.getWidth());

    Image img = MyImage.getImage();
    Image newImg = img.getScaledInstance(label.getWidth(), newHeight, Image.SCALE_SMOOTH);
    ImageIcon image = new ImageIcon(newImg);
    return image;
}

//----------------------------------------------------------------------------------------

    public void imageUpdater(String existingFilePath, String newFilePath){
        File existingFile = new File(existingFilePath);
        if (existingFile.exists()) {
            String parentDirectory = existingFile.getParent();
            File newFile = new File(newFilePath);
            String newFileName = newFile.getName();
            File updatedFile = new File(parentDirectory, newFileName);
            existingFile.delete();
            try {
                Files.copy(newFile.toPath(), updatedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image updated successfully.");
            } catch (IOException e) {
                System.out.println("Error occurred while updating the image: "+e);
            }
        } else {
            try{
                Files.copy(selectedFile.toPath(), new File(destination).toPath(), StandardCopyOption.REPLACE_EXISTING);
            }catch(IOException e){
                System.out.println("Error on update!");
            }
        }
   }
    



    Color navcolor = new Color(0,0,0);
    Color hovercolor = new Color(51,51,51);
    

    
    public boolean duplicateOwnerCheck() {
    dbConnector dbc = new dbConnector();
    boolean isDuplicate = false;

    String query = "SELECT * FROM tbl_owner WHERE owner_fname = '" + oid.getText() +
                   "' AND owner_lname = '" + pname.getText() +
                   "' AND owner_contact = '" + pbreed.getText() + "'";

    try {
        ResultSet rs = dbc.getData(query);
        if (rs.next()) {
            isDuplicate = true;
        }
    } catch (SQLException ex) {
        System.out.println("Duplicate check error: " + ex.getMessage());
    }

    return isDuplicate;
}
    
    
    
    public void clearFields() {
    oid.setText("");
    pname.setText("");
    pbreed.setText("");
    pcolor.setText("");
    page.setText("");
}


public void displayPetByOwner(String ownerId){
    try {
        dbConnector dbc = new dbConnector();
        ResultSet rs = dbc.getData("SELECT pet_id, owner_id, pet_name, pet_color, pet_breed, pet_age FROM tbl_pet WHERE owner_id = '" + ownerId + "'");
        pTable.setModel(DbUtils.resultSetToTableModel(rs));
        rs.close();
    } catch(SQLException ex){
        System.out.println("Errors: " + ex.getMessage());
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

        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        oTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        acc_id = new javax.swing.JLabel();
        acc_name = new javax.swing.JLabel();
        u_add = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        clear = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        update = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        add = new javax.swing.JPanel();
        addlabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        page = new javax.swing.JTextField();
        pbreed = new javax.swing.JTextField();
        pname = new javax.swing.JTextField();
        oid = new javax.swing.JTextField();
        pid = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        pTable = new javax.swing.JTable();
        pcolor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        oTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        oTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                oTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(oTable);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, 430, 200));

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        jLabel1.setText("PET RECORDS");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 400, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Back_Clipart_Vector__Vector_Back_Icon__Back_Icons__Arrow__Back_PNG_Image_For_Free_Download-removebg-preview (1).png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1430, 0, 50, 60));

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        acc_id.setBackground(new java.awt.Color(255, 204, 204));
        acc_id.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        acc_id.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        acc_id.setText("ID");
        jPanel1.add(acc_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 180, 30));

        acc_name.setBackground(new java.awt.Color(255, 204, 204));
        acc_name.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        acc_name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        acc_name.setText("User");
        jPanel1.add(acc_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 180, 30));

        u_add.setBackground(new java.awt.Color(255, 204, 204));
        u_add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                u_addMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                u_addMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                u_addMouseExited(evt);
            }
        });
        u_add.setLayout(null);

        jLabel15.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("ADD");
        u_add.add(jLabel15);
        jLabel15.setBounds(20, 10, 170, 30);

        jPanel1.add(u_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        clear.setBackground(new java.awt.Color(204, 204, 204));
        clear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clearMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                clearMouseExited(evt);
            }
        });
        clear.setLayout(null);

        jLabel17.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("CLEAR");
        clear.add(jLabel17);
        jLabel17.setBounds(20, 10, 140, 30);

        jPanel1.add(clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 580, 180, 50));

        update.setBackground(new java.awt.Color(204, 204, 204));
        update.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                updateMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                updateMouseExited(evt);
            }
        });
        update.setLayout(null);

        jLabel19.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("UPDATE");
        update.add(jLabel19);
        jLabel19.setBounds(20, 10, 140, 30);

        jPanel1.add(update, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 520, 180, 50));

        add.setBackground(new java.awt.Color(204, 204, 204));
        add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addMouseExited(evt);
            }
        });
        add.setLayout(null);

        addlabel.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        addlabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addlabel.setText("ADD");
        add.add(addlabel);
        addlabel.setBounds(20, 10, 140, 30);

        jPanel1.add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 460, 180, 50));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userimages/Screenshot 2026-03-03 165337.png"))); // NOI18N
        jLabel6.setText("jLabel6");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 180, 170));

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 680));

        page.setBackground(new java.awt.Color(204, 204, 255));
        page.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        page.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Pet Age:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 0, 18))); // NOI18N
        page.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pageActionPerformed(evt);
            }
        });
        jPanel3.add(page, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 550, 330, 70));

        pbreed.setBackground(new java.awt.Color(204, 204, 255));
        pbreed.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        pbreed.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Pet Breed:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 0, 18))); // NOI18N
        pbreed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pbreedActionPerformed(evt);
            }
        });
        jPanel3.add(pbreed, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 380, 330, 70));

        pname.setBackground(new java.awt.Color(204, 204, 255));
        pname.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        pname.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Pet Name:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 0, 18))); // NOI18N
        pname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pnameActionPerformed(evt);
            }
        });
        jPanel3.add(pname, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 280, 330, 70));

        oid.setBackground(new java.awt.Color(204, 204, 255));
        oid.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        oid.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Owner ID:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 0, 18))); // NOI18N
        oid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oidActionPerformed(evt);
            }
        });
        jPanel3.add(oid, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 180, 330, 70));

        pid.setBackground(new java.awt.Color(204, 204, 255));
        pid.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        pid.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Pet ID:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 0, 18))); // NOI18N
        pid.setEnabled(false);
        pid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pidActionPerformed(evt);
            }
        });
        jPanel3.add(pid, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 80, 330, 70));

        pTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        pTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(pTable);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 440, 430, 200));

        pcolor.setBackground(new java.awt.Color(204, 204, 255));
        pcolor.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        pcolor.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Pet Color:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 0, 18))); // NOI18N
        pcolor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pcolorActionPerformed(evt);
            }
        });
        jPanel3.add(pcolor, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 460, 330, 70));

        jLabel4.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        jLabel4.setText("OWNER RECORDS");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Back_Clipart_Vector__Vector_Back_Icon__Back_Icons__Arrow__Back_PNG_Image_For_Free_Download-removebg-preview (1).png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 0, 60, 60));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
       Session sess = Session.getInstance();
       if(sess.getUid()==0){
           JOptionPane.showMessageDialog(null, "No account, Login First!");
           loginForm lf = new loginForm();
           lf.setVisible(true);
           this.dispose();           
       }else{
       acc_name.setText(""+sess.getFname());
       acc_lname.setText(""+sess.getLname());
       acc_id.setText(""+sess.getUid());
           
       }  
           
    }//GEN-LAST:event_formWindowActivated

    private void oTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_oTableMouseClicked
     
//    oid.setEnabled(false);
//    pid.setEnabled(false);
//    pname.setEnabled(false);
//    pbreed.setEnabled(false);
//    pcolor.setEnabled(false);
//
//    int rowIndex = oTable.getSelectedRow();
//    if(rowIndex < 0){
//        JOptionPane.showMessageDialog(null, "Please select an item!");         
//    } else {
//        try {
//            dbConnector dbc = new dbConnector();
//            TableModel tbl = oTable.getModel();
//            String selectedOwnerId = tbl.getValueAt(rowIndex, 0).toString();
//            ResultSet rs = dbc.getData("SELECT * FROM tbl_owner WHERE owner_id ='" + selectedOwnerId + "'");
//
//            if(rs.next()){
//                oid.setText(rs.getString("owner_id"));
//
//                add.setEnabled(false);
//                addlabel.setForeground(red);
//                checkadd = false;
//
//                // 👇 Call this to show only pets of the selected owner
//                displayPetByOwner(selectedOwnerId);
//            }
//
//            rs.close();
//
//        } catch(SQLException ex){
//            System.out.println("" + ex);
//        }
//    } 
    oid.setEnabled(false);
    int rowIndex = oTable.getSelectedRow();
    if(rowIndex < 0){
        JOptionPane.showMessageDialog(null, "Please select an owner first!");         
    } else {
        try {
            dbConnector dbc = new dbConnector();
            TableModel tbl = oTable.getModel();
            String selectedOwnerId = tbl.getValueAt(rowIndex, 0).toString();
            ResultSet rs = dbc.getData("SELECT * FROM tbl_owner WHERE owner_id ='" + selectedOwnerId + "'");

            if(rs.next()){
                oid.setText(rs.getString("owner_id"));

                // Disable Add mode
                add.setEnabled(true);
                
                checkadd = true;

                // Show only pets of selected owner
                displayPetByOwner(selectedOwnerId);

                // 👇 CLEAR pet input fields
                pid.setText("");
                pname.setText("");
                pbreed.setText("");
                pcolor.setText("");
                page.setText("");
            }

            rs.close();

        } catch(SQLException ex){
            System.out.println("" + ex);
        }
    } 



    }//GEN-LAST:event_oTableMouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        Appointments app = new Appointments();
        app.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void u_addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_u_addMouseClicked
        createUserForm crf = new createUserForm();
        crf.setVisible(true);
        //      crf.update.setEnabled(false);
        crf.update.setEnabled(false);
        for (Component c : crf.update.getComponents()) {
            c.setEnabled(false);
        }
        crf.remove.setEnabled(false);
        crf.select.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_u_addMouseClicked

    private void u_addMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_u_addMouseEntered
        u_add.setBackground(hovercolor);
    }//GEN-LAST:event_u_addMouseEntered

    private void u_addMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_u_addMouseExited
        u_add.setBackground(navcolor);
    }//GEN-LAST:event_u_addMouseExited

    private void addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseClicked
    if (oid.getText().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please select an Owner first!");
        return; // stop the code from continuing
    }

    if (checkadd) {
        if (pname.getText().isEmpty() || pbreed.getText().isEmpty() || page.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required!");
        } else {
            dbConnector dbc = new dbConnector();

            String query = "INSERT INTO tbl_pet (owner_id, pet_name, pet_breed, pet_age, pet_color) VALUES ('" +
                            oid.getText() + "', '" + pname.getText() + "', '" +
                            pbreed.getText() + "', '" + page.getText() + "', '"+pcolor.getText()+"')";

            int newPetId = dbc.insertAndGetId(query);  // custom method to insert and get new ID

            if (newPetId > 0) {
                JOptionPane.showMessageDialog(null, "Pet added successfully!");

                // Log with the pet ID
                Session sess = Session.getInstance();
                String logMsg = "Added a pet with pet ID: " + newPetId;
                dbc.insertLog(sess.getUid(), logMsg);

                clearFields();
                loadPets();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add pet.");
            }
        }
    }
    }//GEN-LAST:event_addMouseClicked

    private void addMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseEntered
       
    }//GEN-LAST:event_addMouseEntered

    private void addMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseExited

    }//GEN-LAST:event_addMouseExited

    private void clearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearMouseClicked

    oid.setEnabled(true);
    pname.setEnabled(true);
    pid.setEnabled(false);
    page.setEnabled(true);
    pbreed.setEnabled(true);
    pcolor.setEnabled(true);
    checkadd = true;
    addlabel.setForeground(pink);

    // Clear text fields
    pid.setText("");
    oid.setText("");
    pname.setText("");
    pbreed.setText("");
    pcolor.setText("");
    page.setText("");

    // 👉 Refresh both tables
    loadOwners(); // load all owner records
    loadPets();   // load all pet records

    }//GEN-LAST:event_clearMouseClicked

    private void clearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearMouseEntered
        
    }//GEN-LAST:event_clearMouseEntered

    private void clearMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearMouseExited
        
    }//GEN-LAST:event_clearMouseExited

    private void updateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateMouseClicked
     // Check if a row is selected in the pTable
    int selectedRow = pTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(null, "Cannot update data without selecting a pet first!");
        return;
    }

    // Check if pet age field is empty
    if (page.getText().isEmpty()) {
        JOptionPane.showMessageDialog(null, "All fields are required!");
        return;
    }

    // Get pet_id from the selected row in the table (assuming column 0 has pet_id)
    String selectedPetId = pTable.getValueAt(selectedRow, 0).toString(); 

    dbConnector dbc = new dbConnector();

    try {
        dbc.updateData("UPDATE tbl_pet SET pet_age = '" + page.getText() + "' WHERE pet_id = '" + selectedPetId + "'");

        // TODO: refresh your table or form here if needed
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Failed to update pet: " + e.getMessage());
    }
    }//GEN-LAST:event_updateMouseClicked

    private void updateMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateMouseEntered
       
    }//GEN-LAST:event_updateMouseEntered

    private void updateMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateMouseExited
        
    }//GEN-LAST:event_updateMouseExited

    private void pageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pageActionPerformed

    private void pbreedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pbreedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pbreedActionPerformed

    private void pnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pnameActionPerformed

    private void oidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_oidActionPerformed

    private void pidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pidActionPerformed

    private void pTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pTableMouseClicked
    
        oid.setEnabled(false);
        pid.setEnabled(false);
        pname.setEnabled(false);
        pbreed.setEnabled(false);
        pcolor.setEnabled(false);
        int rowIndex = pTable.getSelectedRow();
      if(rowIndex < 0){
          JOptionPane.showMessageDialog(null, "Please select an owner first!");         
      }else{
          try{
              dbConnector dbc = new dbConnector();
              TableModel tbl = pTable.getModel();
              ResultSet rs = dbc.getData("SELECT * FROM tbl_pet WHERE pet_id ='"+tbl.getValueAt(rowIndex, 0)+"'");
              if(rs.next()){
                  createUserForm crf = new createUserForm();
                  pid.setText(""+rs.getInt("pet_id"));
                  oid.setText(""+rs.getString("owner_id"));
                  pname.setText(""+rs.getString("pet_name"));
                  pbreed.setText(""+rs.getString("pet_breed"));
                  page.setText(""+rs.getString("pet_age"));
                  pcolor.setText(""+rs.getString("pet_color"));

   

                  add.setEnabled(false);
                  addlabel.setForeground(red);
                  checkadd = false;
                  
                  
              }
          }catch(SQLException ex){
              System.out.println(""+ex);
          }
          
      } 
    }//GEN-LAST:event_pTableMouseClicked

    private void pcolorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pcolorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pcolorActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        Appointments app = new Appointments();
        app.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel5MouseClicked

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
            java.util.logging.Logger.getLogger(AddPet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddPet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddPet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddPet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddPet().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel acc_id;
    private javax.swing.JLabel acc_name;
    private javax.swing.JPanel add;
    private javax.swing.JLabel addlabel;
    private javax.swing.JPanel clear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable oTable;
    public javax.swing.JTextField oid;
    private javax.swing.JTable pTable;
    private javax.swing.JTextField page;
    private javax.swing.JTextField pbreed;
    private javax.swing.JTextField pcolor;
    private javax.swing.JTextField pid;
    public javax.swing.JTextField pname;
    private javax.swing.JPanel u_add;
    private javax.swing.JPanel update;
    // End of variables declaration//GEN-END:variables
}
