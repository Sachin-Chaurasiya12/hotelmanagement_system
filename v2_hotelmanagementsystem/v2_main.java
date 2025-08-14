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

    public static void main(String[] args) {
        int command = -1;
        System.out.println("Hotel Management System - Version " + Version.CURRENT_VERSION);
        System.out.println("-------- Welcome to Hotel Management System !--------");

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }

        while (command != 5) {
            try {
                System.out.println("What you Like to do! ");
                System.out.println("1. Book your room");
                System.out.println("2. Room allotting");
                System.out.println("3. View Records");
                System.out.println("4. Manage Rooms");
                System.out.println("5. Exit the Program");
                System.out.print("Enter your task : ");
                command = readInt();
                System.out.println();

                switch (command) {
                    case 1 -> GuestdataEntry();
                    case 2 -> roomalloting();
                    case 3 -> viewhotelrecords();
                    case 4 -> managerecord();
                    case 5 -> exitProgram();
                    default -> System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                sc.nextLine(); // clear buffer
            }
        }
    }

    static void GuestdataEntry() {
        try {
            int guestid;
            while (true) {
                System.out.print("Enter the guestid : ");
                guestid = readInt();
                if (!isGuestIdExists(guestid)) break;
                System.out.println("Error: Guest ID already exists. Please enter a unique ID.");
            }

            sc.nextLine(); // clear buffer
            System.out.print("Enter the name of Guest : ");
            String name = sc.nextLine();

            System.out.print("Enter the number of roompartner(yes/no) : ");
            String partner = sc.nextLine();
            String roompartner = null;
            if (partner.equalsIgnoreCase("yes")) {
                System.out.print("Enter number of partners: ");
                roompartner = sc.nextLine();
            }

            System.out.print("Enter the Age of Guest : ");
            int Age = readInt();
            sc.nextLine();

            System.out.print("Enter the name of Id document : ");
            String idproof = sc.nextLine();

            guestdata gd = new guestdata(guestid, name, roompartner, Age, idproof);
            guestlist.add(gd);

            File file = new File(datafile);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                if (file.length() > 0) bw.newLine();
                bw.write(gd.tofileString());
            }

            System.out.println("Guest record added successfully.\n");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void roomalloting() {
        try {
            guestlist = guestdata.fromfilestring(datafile);
            File allot = new File(allotfile);
            File status = new File(statusfile);

            System.out.print("Enter the id of Guest : ");
            int guestid = readInt();
            sc.nextLine();

            boolean found = false;

            for (guestdata gd1 : guestlist) {
                if (guestid == gd1.getid()) {
                    found = true;
                    System.out.print("Enter the room no. you want to allot : ");
                    int alloting = readInt();

                    if (isRoomOccupied(alloting, status)) {
                        System.out.println("The room is already occupied! Try again");
                        return;
                    }

                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(allot, true))) {
                        if (allot.length() > 0) bw.newLine();
                        bw.write("Room number " + alloting + " is alloted to Guest ID: " + guestid);
                    }

                    try (BufferedWriter bw1 = new BufferedWriter(new FileWriter(status, true))) {
                        if (status.length() > 0) bw1.newLine();
                        bw1.write("Room " + alloting + " : Occupied");
                    }

                    System.out.println("Room alloted successfully!");
                    break;
                }
            }

            if (!found) {
                System.out.println("Guest ID does not exist");
            }
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    static void viewhotelrecords() {
        try {
            Viewrecords vr = new Viewrecords();
            vr.Execution();
        } catch (Exception e) {
            System.out.println("Error displaying records: " + e.getMessage());
        }
    }

    static void managerecord() {
        try {
            Managerec mr = new Managerec();
            mr.manage();
        } catch (Exception e) {
            System.out.println("Error managing records: " + e.getMessage());
        }
    }

    static void exitProgram() {
        System.out.print("Exiting program");
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(1000);
                System.out.print(".");
            } catch (Exception e) {
            }
        }
        System.out.println("Exited");
    }

    // Helper methods

    static int readInt() {
        while (true) {
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Invalid number, try again: ");
                sc.nextLine();
            }
        }
    }

    static boolean isGuestIdExists(int guestid) throws IOException {
        File file = new File(datafile);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length > 0 && Integer.parseInt(parts[0]) == guestid) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    static boolean isRoomOccupied(int roomNum, File statusFile) throws IOException {
        if (statusFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(statusFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(" : ");
                    if (parts.length == 2) {
                        int rn = Integer.parseInt(parts[0].replace("Room", "").trim());
                        if (rn == roomNum && parts[1].trim().equalsIgnoreCase("Occupied")) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
