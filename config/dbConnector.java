/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

/**
 *
 * @author Yisha
 */
public class dbConnector {
    
    private Connection connect;

       // constructor to connect to our database
        public dbConnector(){
            try{
                connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/petcaresystem", "root", "");
            }catch(SQLException ex){
                    System.out.println("Can't connect to database: "+ex.getMessage());
            }
        }
    

    
         
         
        //Function to retrieve data
        public ResultSet getData(String sql) throws SQLException{
            Statement stmt = connect.createStatement();
            ResultSet rst = stmt.executeQuery(sql);
            return rst;
        }
        
        //Function to save data
        public int insertData(String sql){
            int result;
            try{
                PreparedStatement pst = connect.prepareStatement(sql);
                pst.executeUpdate();
                System.out.println("Inserted Successfully!");
                pst.close();
                result =1;
            }catch(SQLException ex){
                System.out.println("Connection Error: "+ex);
                result =0;
            }
            return result;
        }
        

         //Function to update data
        public void updateData(String sql){
            try{
                PreparedStatement pst = connect.prepareStatement(sql);
                    int rowsUpdated = pst.executeUpdate();
                        if(rowsUpdated > 0){
                            JOptionPane.showMessageDialog(null, "Data Updated Successfully!");
                        }else{
                            System.out.println("Data Update Failed!");
                        }
                        pst.close();
            }catch(SQLException ex){
                System.out.println("Connection Error: "+ex);
            }
        
        }
        
         // New method: Insert log into tbl_logs
    public int insertLog(int userId, String action) {
        int result = 0;
        try {
            // Prepare current timestamp (optional if your table has default CURRENT_TIMESTAMP)
            LocalDateTime now = LocalDateTime.now();
            String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            
            String sql = "INSERT INTO tbl_logs (u_id, actions, date_time) VALUES (?, ?, ?)";
            PreparedStatement pst = connect.prepareStatement(sql);
            pst.setInt(1, userId);
            pst.setString(2, action);
            pst.setString(3, timestamp);
            
            result = pst.executeUpdate();
            pst.close();
            
            if(result > 0) {
                System.out.println("Log inserted successfully.");
            }
        } catch(SQLException ex) {
            System.out.println("Error inserting log: " + ex.getMessage());
        }
        return result;
    }
        
    public int insertAndGetId(String sql) {
    int generatedId = -1;
    try {
        PreparedStatement pst = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pst.executeUpdate();
        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            generatedId = rs.getInt(1); // mao ni ang new user_id
        }
        pst.close();
    } catch (SQLException ex) {
        System.out.println("Insert with ID Error: " + ex);
    }
    return generatedId;
}
    
}
