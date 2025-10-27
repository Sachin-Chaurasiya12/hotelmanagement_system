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
 *  File : Alloting_g.java
 *  description : handles the client side operation, using the features like 
 *  regestration,alloting room,managing and viewing records of guest and rooms
 *  Author : Sachin Chaurasiya  
 *  Created : August 2025
 */

package Alloting_modules;
/*
 * Returs the components and connection for database connection 
 * and data structures.
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import database_repository.dataconn;

public class Alloting_g {
    /*
     * Stores the the data o room_no,id,roomtype,rate and status if
     * the room is available or not.
     */
    public static class InnerAlloting_g {
        private int room_no;
        private int id;
        private String type;
        private String rate;
        private String status;
        
        public InnerAlloting_g(int room_no,int id,String type,String rate,String status){
            this.room_no = room_no;
            this.id = id;
            this.type = type;
            this.rate = rate;
            this.status = status;
        }
        public int getroom_no(){return room_no;};
        public int getid(){return id;};
        public String gettype(){return type;};
        public String getrate(){return rate;};
        public String getstatus(){return status;};
    }
    static final Scanner sc = new Scanner(System.in);
    /*
     * Structure of the Alloting room interface.
     */
    public void alloting(){
        System.out.println("1. Enter room data");
        System.out.println("2. Allot room");

        int input = sc.nextInt();
        switch (input) {
            case 1:
                room_data();
                break;
            case 2:
                break;
            default:
                break;
        }
    }
    static List<InnerAlloting_g> room_data(){
        List<InnerAlloting_g> list = new ArrayList<>();

        System.out.print("How many guests do you want to register? ");
        int n = sc.nextInt();
        sc.nextLine(); // clear buffer

        for(int i=0;i<n;i++){
        System.out.println("Enter Room no : ");
        int room_no = sc.nextInt();
        sc.nextLine();
        int id;
        while(true){
            System.out.println("Enter id : ");
            id = sc.nextInt();
            sc.nextLine();
            if(isduplicate(id)){
                System.out.println("Id exist! try different one.");
            }else{
                break;
            }
        }
        System.out.println("Enter room type : ");
        String type = sc.nextLine();
        System.out.println("Enter room rate : ");
        String rate = sc.nextLine();
        System.out.println("Enter room status : ");
        String status = sc.nextLine();

        list.add(new InnerAlloting_g(room_no, id, type, rate, status));
        }
        try {
            try(Connection con = dataconn.getConnection()){
                String sql = "Insert into allot_g_records(room_no,id,type,rate,status) values(?,?,?,?,?)";
                con.setAutoCommit(false);
                PreparedStatement pstmt = con.prepareStatement(sql);
                    for(InnerAlloting_g g:list){
                        pstmt.setInt(1, g.getroom_no());
                        pstmt.setInt(2,g.getid());
                        pstmt.setString(3,g.gettype());
                        pstmt.setString(4,g.getrate());
                        pstmt.setString(5,g.getstatus());
                        pstmt.addBatch();
                    }
                    pstmt.executeBatch();
                    con.commit();
                System.out.println("Inserted successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static boolean isduplicate(int id){
            boolean duplicate = false;
        try(Connection con = dataconn.getConnection()){
            PreparedStatement pstemt = con.prepareStatement("select Count(*) from allot_g_records where id = ?");
            pstemt.setInt(1, id);
            ResultSet rs = pstemt.executeQuery();
            if(rs.next()){
                if(rs.getInt(1) > 0){
                    duplicate = true;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return duplicate;
    }
}
