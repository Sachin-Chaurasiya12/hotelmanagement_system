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
 *  File : guest_rec.java
 *  description : handles the client side operation, using the features like 
 *  regestration,alloting room,managing and viewing records of guest and rooms
 *  Author : Sachin Chaurasiya  
 *  Created : August 2025
 */
package register_model.classes.src.java.records;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import database_repository.dataconn;

public class guest_rec {

    public class Innerguest_rec {
        private int id;
        private String name;
        private String address;
        private int numberofpartners;
        private String phone;
        private Date checkDate;
        private Date checkout;

        public Innerguest_rec(int id, String name, String address, int numberofpartners, String phone, Date checkDate, Date checkout) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.numberofpartners = numberofpartners;
            this.phone = phone;
            this.checkDate = checkDate;
            this.checkout = checkout;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public String getAddress() { return address; }
        public int getNumberofpartners() { return numberofpartners; }
        public String getPhone() { return phone; }
        public Date getCheckDate() { return checkDate; }
        public Date getCheckout() { return checkout; }

        @Override
        public String toString() {
            return "Guest ID: " + id + ", Name: " + name + ", Address: " + address +
                   ", Partners: " + numberofpartners + ", Phone: " + phone +
                   ", Check-in: " + checkDate + ", Checkout: " + checkout;
        }
    }
     public List<Innerguest_rec> register_guest() {
        Scanner sc = new Scanner(System.in);
        List<Innerguest_rec> list = new ArrayList<>();

        System.out.print("How many guests do you want to register? ");
        int n = sc.nextInt();
        sc.nextLine(); // clear buffer

        for (int i = 0; i < n; i++) {
            System.out.println("\nGuest #" + (i + 1));

            int id;
            while(true){
                System.out.print("Enter guest id : ");
                id = sc.nextInt();
                sc.nextLine();
                if(isduplicate(id)){
                    System.out.println("duplicate id! try again.");
                }else{
                    break;
                }
            }

            System.out.print("Enter name: ");
            String name = sc.nextLine();

            System.out.print("Enter address: ");
            String address = sc.nextLine();

            System.out.print("Number of partners: ");
            int partners = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter phone number: ");
            String phone = sc.nextLine();

            System.out.print("Enter check-in date (YYYY-MM-DD): ");
            Date checkin = Date.valueOf(LocalDate.parse(sc.nextLine()));

            System.out.print("Enter checkout date (YYYY-MM-DD): ");
            Date checkout = Date.valueOf(LocalDate.parse(sc.nextLine()));

            list.add(new Innerguest_rec(id, name, address, partners, phone, checkin, checkout));
            System.out.println(" Guest registered successfully!");
        }

        return list;
    }
    public static boolean isduplicate(int id){
            boolean duplicate = false;
        try(Connection ncon = dataconn.getConnection()){
            PreparedStatement pstemt = ncon.prepareStatement("select Count(*) from guest_records where id = ?");
            pstemt.setInt(1, id);
            ResultSet rs = pstemt.executeQuery();
            if(rs.next()){
                if(rs.getInt(1) > 0){
                    duplicate = true;
                }
            }
            pstemt.close();
            rs.close();
            ncon.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return duplicate;
    }
}
