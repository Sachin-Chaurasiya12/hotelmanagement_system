/*
 *  Copyright 2025 Sachin chaurasiya.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  ---------------------------------------------------------------------------
 *  File : Mainfile.java
 *  description : handles the client side operation, using the features like 
 *  regestration,alloting room,managing and viewing records of guest and rooms
 *  Author : Sachin Chaurasiya  
 *  Created : August 2025
 */

package HMS_module;

/* 
 * These are all the imports which are required to connect all the other classes 
 * and files of this Hotel Management System program. 
 */
import java.util.Scanner;
import Alloting_modules.Alloting_g;
import java.sql.*;
import database_repository.*;
import register_model.classes.src.java.records.guest_rec;
/*
 * Returns the version of this program and consist the global scanner
 * for this project.
 */
public class Mainfile {
    static final Scanner sc = new Scanner(System.in);
    public class Version {
        public static final String CURRENT_VERSION = "v2.0.0";
    }
    public static void main(String[] args) {

        try { 
            dataconn.initDataSource();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Hotel Management System - Version " + Version.CURRENT_VERSION);
        System.out.println("-------- Welcome to Hotel Management System !--------");

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
              Thread.currentThread().interrupt();
            System.out.println("Thread was interrupted, stopping operation...");
        }

        int command;
        do {
            System.out.println("What you Like to do! ");
            System.out.println("1. Register guest details");
            System.out.println("2. Alloted room");
            System.out.println("3. View Records");
            System.out.println("4. Manage Records");
            System.out.println("5. Exit");
            System.out.print("Enter your task : ");
            command = sc.nextInt();
            sc.nextLine();

            switch (command) {
            case 1:
                register();
                break;
            case 2:
                alloting();
                break;
            case 5:
                System.out.print("Exiting");
                try {
                    for (int i = 0; i <= 2; i++) {
                        System.out.print(".");
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                      Thread.currentThread().interrupt();
                      System.out.println("Thread was interrupted, stopping operation...");
                }
                System.out.println("\nThank you for using Hotel Management System!");
                break;

            default:
                System.out.println("Invalid command! Please try again.");
                break;
        }
        } while (command != 5);
    }
    /*
     * register() collects guest data from another class and efficiently
     *  inserts all guest records into the SQL database in one go, using batch
     *  processing for performance.
     */
    static void register() {

        try (Connection con = dataconn.getConnection()) {
            guest_rec gr = new guest_rec();
            var guests =  gr.register_guest();
            String sql = "INSERT INTO guest_records (id, name, address, partners, phone, checkin, checkout) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            /*
             * Values are accepted from the input field and through 
             * guest class it store value in database.
             */
            for (var guest : guests) {
                pstmt.setInt(1, guest.getId());
                pstmt.setString(2, guest.getName());
                pstmt.setString(3, guest.getAddress());
                pstmt.setInt(4, guest.getNumberofpartners());
                pstmt.setString(5, guest.getPhone());
                pstmt.setDate(6, guest.getCheckDate());
                pstmt.setDate(7, guest.getCheckout());
                pstmt.addBatch();
            }

            int[] row = pstmt.executeBatch();
            if(row.length > 0){
                System.out.println("Data inserted successfully! (" + row.length + " rows)");
            } else {
                System.out.println("No data inserted.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
     * Returs the value of alloting and store in the database.
     */
    static void alloting(){
        Alloting_g ag = new Alloting_g();
        ag.alloting();
    }
}

