import java.io.*;
import java.util.*;

public class v1_Main {
    public class Version {
        public static final String CURRENT_VERSION = "v1.3.1";
    }

    static final String filename = "data.txt";
    static final String alloting = "allotroom.txt";
    static final String roomstatus = "roomstatus.txt";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int command;
        System.out.println();
        System.out.println("Hotel Management System - Version " + Version.CURRENT_VERSION);
        do {
            System.out.println("\n------------- Welcome to our hotel -------------");
            System.out.println("1. Book your room");
            System.out.println("2. Room allotting");
            System.out.println("3. View alloted room");
            System.out.println("4. View roomstatus");
            System.out.println("5. Manage Rooms");
            System.out.println("6. Exit the Program");
            System.out.print("Enter your task: ");
            command = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (command) {
                case 1 -> bookingroom();
                case 2 -> roomalloting();
                case 3 -> Viewallotedroom();
                case 4 -> roomstatus();
                case 5 -> managebooking();
                case 6 -> exitProgram();
                default -> System.out.println("Invalid option");
            }
        } while (command != 6);
    }

    static void bookingroom() {
        try {
            System.out.print("Enter the roomId: ");
            int roomid = sc.nextInt();
            sc.nextLine();

            if (isExist(roomid)) {
                System.out.println("Room ID already exists. Try another one.");
                return;
            }

            System.out.print("Enter the name of the guest: ");
            String name = sc.nextLine();

            System.out.print("Is there any room partner (yes/no)? ");
            String roomcmd = sc.nextLine();
            String roompartner = roomcmd.equalsIgnoreCase("yes") ? sc.nextLine() : "none";

            System.out.print("Enter the Age of the guest: ");
            int age = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter the id proof: ");
            String idproof = sc.nextLine();

            Guests g = new Guests(roomid, name, roompartner, age, idproof);

            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                if (file.length() > 0)
                    bw.newLine();
                bw.write(g.toFileString());
            }

            System.out.println("Guest record added successfully.\n");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static boolean isExist(int roomid) {
        File file = new File(filename);
        if (!file.exists()) {
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Guests g = Guests.fromfilestring(line);
                if (g != null && g.getId() == roomid)
                    return true;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return false;
    }

    static void Viewallotedroom(){
        alloting alot = new alloting();
        alot.option();
    }

    static void managebooking(){
        managebooking m = new managebooking();
        m.manage();
    }
    static void exitProgram() {
        System.out.print("Exiting the program");
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(500);
                System.out.print(".");
            } catch (Exception e) {
                // ignore
            }
        }
        System.out.println("\nProgram ended.");
    }
    static void roomstatus(){
        statuschecker check = new statuschecker();
        check.status();
    }
}