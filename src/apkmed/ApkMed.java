/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apkmed;

import com.mysql.jdbc.Connection;
import java.awt.EventQueue;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Lukas
 */
public class ApkMed {

    
    public static void main(String[] args) {
      
        EventQueue.invokeLater(new Runnable() {
 			@Override
 			public void run() {
 				 userform uf = new userform();
                                 uf.setVisible(true);
 			}
 		});
    }
    
}
