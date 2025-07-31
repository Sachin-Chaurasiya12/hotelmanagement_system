import java.io.*;
import java.util.*;

public class v1_Main {

    public static class Guests {
        private int roomId;
        private String name;
        private String roompartner;
        private int age;
        private String idproof;

        public Guests(int roomId, String name, String roompartner, int age, String idproof) {
            this.roomId = roomId;
            this.name = name;
            this.roompartner = roompartner;
            this.age = age;
            this.idproof = idproof;
        }

        public int getId() {
            return roomId;
        }

        public String getName() {
            return name;
        }

        public String toFileString() {
            return roomId + "," + name + "," + roompartner + "," + age + "," + idproof;
        }

        public static Guests fromfilestring(String line) {
            try {
                if (line.trim().isEmpty())
                    return null;
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int roomId = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String roompartner = parts[2].trim();
                    int age = Integer.parseInt(parts[3].trim());
                    String idproof = parts[4].trim();
                    return new Guests(roomId, name, roompartner, age, idproof);
                }
            } catch (Exception e) {
                System.out.println("Invalid data line: " + line);
            }
            return null;
        }

        @Override
        public String toString() {
            return roomId + ": name: " + name + " | roompartner: " + roompartner + " | Age: " + age + " | Id proof: " + idproof;
        }
    }

    static final String filename = "data.txt";
    static final String alloting = "allotroom.txt";
    static final String roomstatus = "roomstatus.txt";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int command;
        do {
            System.out.println("\n------------- Welcome to our hotel -------------");
            System.out.println("1. Book your room");
            System.out.println("2. Room allotting");
            System.out.println("3. Exit the Program");
            System.out.print("Enter your task: ");
            command = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (command) {
                case 1 -> bookingroom();
                case 2 -> roomalloting();
                case 3 -> exitProgram();
                default -> System.out.println("Invalid option");
            }
        } while (command != 3);
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

    static void roomalloting() {
        try {
            System.out.print("Enter the guest's room ID to allot: ");
            int id = sc.nextInt();
            sc.nextLine(); // consume newline

            File file = new File(filename);
            if (!file.exists()) {
                System.out.println("No guest data available.");
                return;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    Guests g = Guests.fromfilestring(line);
                    if (g != null && g.getId() == id) {
                        System.out.print("Allot guest to which room number (e.g.,101, 102): ");
                        String customRoomNo = sc.nextLine().trim();

                        try (
                            BufferedWriter bw1 = new BufferedWriter(new FileWriter(alloting, true));
                            BufferedWriter bw2 = new BufferedWriter(new FileWriter(roomstatus, true))
                        ) {
                            if (new File(alloting).length() > 0)
                                bw1.newLine();
                            if (new File(roomstatus).length() > 0)
                                bw2.newLine();

                            bw1.write("Guest " + g.getName() + " (ID: " + g.getId() + ") allotted to Room " + customRoomNo);
                            bw2.write("Room " + customRoomNo + " status: Occupied by Guest ID " + g.roomId);
                        }

                        System.out.println("Room successfully allotted to room number: " + customRoomNo);
                        return;
                    }
                }
            }

            System.out.println("Room ID not found.");
        } catch (Exception e) {
            System.out.println("Error during room allotment: " + e.getMessage());
        }
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
}
