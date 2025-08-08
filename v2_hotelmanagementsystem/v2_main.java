import java.io.*;
import java.util.*;

public class v2_main {

    public class Version {
        public static final String CURRENT_VERSION = "v2.0.0";
    }
    
    static Scanner sc = new Scanner(System.in);
    static final String datafile = "datafile.txt";
    static final String allotfile = "allotfile.txt";
    static final String statusfile = "statusfile.txt";
    static List<guestdata> guestlist = new ArrayList<>();

    public static void main() throws IOException{
        int command;
        System.out.println("Hotel Management System - Version " + Version.CURRENT_VERSION);
        System.out.println("-------- Welcome to Hotel Management System !--------");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        do { 
        System.out.println("What you Like to do! ");
        System.out.println("1. Book your room");
        System.out.println("2. Room allotting");
        System.out.println("3. View Records");
        System.out.println("4. Manage Rooms");
        System.out.println("5. Exit the Program");
        System.out.print("Enter your task : ");
        command = sc.nextInt();
        System.out.println();

            switch (command) {
                case 1 -> GuestdataEntry();
                case 2 -> roomalloting();
                case 3 -> viewhotelrecords();
               /*  case 4 -> 
                case 5 -> */
                case 5-> exitProgram();
                default -> System.out.println("Invalid option");
            }
        } while (command != 5);
    }

    static void GuestdataEntry(){
        try {
        System.out.print("Enter the guestid :");
        int guestid = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the name of Guest :");
        String name = sc.nextLine();
        System.out.print("Enter the number of roompartner(yes/no) :");
        String roompartner = sc.nextLine();
        System.out.print("Enter the Age of Guest :");
        int Age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the name of Id documnet :");
        String idproof = sc.nextLine();

        guestdata gd = new guestdata(guestid,name,roompartner,Age,idproof);
        guestlist.add(gd);
        File file = new File(datafile);
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file,true))){
            if(file.length() > 0){
                bw.newLine();
            }
            bw.write(gd.tofileString());
        }
        
        System.out.println("Guest record added successfully.\n");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    static void roomalloting() throws IOException{
        try {
            guestlist = guestdata.fromfilestring(datafile);
            System.out.print("Enter the id of Guest : ");
            int guestid = sc.nextInt();
            sc.nextLine();

            boolean found = false;
            File allot = new File(allotfile);
            File status = new File(statusfile);

            for (guestdata gd1 : guestlist) {
            if (guestid == gd1.getid()) {
                found = true;
                System.out.print("Enter the room no. you want to allot : ");
                int alloting = sc.nextInt();

                try(BufferedWriter bw = new BufferedWriter(new FileWriter(allot, true))) {
                    if (new File(allotfile).length() > 0) bw.newLine();
                    bw.write("Room number " + alloting + " is alloted to Guest ID: " + guestid);
                } catch (Exception e) {
                    System.out.println("Error writing to allot file: " + e.getMessage());
                }

                try(BufferedWriter bw1 = new BufferedWriter(new FileWriter(status, true))) {
                    if (new File(statusfile).length() > 0) bw1.newLine();
                    bw1.write("Room " + alloting + " : Occupied");
                } catch (Exception e) {
                    System.out.println("Error writing to status file: " + e.getMessage());
                }

                System.out.println("Room alloted successfully!");
                break;
            }
        }
        if(!found){
                System.out.println("Room id does not exist");
            }
        } catch (Exception e) {
            System.out.println("Error occoured!");
        }
    }

    static void viewhotelrecords(){
        Viewrecords vr = new Viewrecords();
        vr.Execution();
    }
    static void exitProgram(){
        System.out.print("exiting program");
        for(int i=0; i<3 ; i++){
            try {
                Thread.sleep(1000);
                System.out.print(".");
            } catch (Exception e) {
                //ignore   
            }
        }
        System.out.println("Exited");
    }
}
