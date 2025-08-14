import java.io.*;
import java.util.*;

public class Viewrecords {

    // Safe integer input method
    private static int getSafeInt(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            if (sc.hasNextInt()) {
                int value = sc.nextInt();
                sc.nextLine(); // clear leftover newline
                return value;
            } else {
                System.out.println("Error: Please enter a number only!");
                sc.nextLine(); // discard invalid input
            }
        }
    }

    public void Execution() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Option you Want to view");
        System.out.println("1 --> Guest Records");
        System.out.println("2 --> Alloted room records");
        System.out.println("3 --> Track room Records");

        int input = getSafeInt(sc, "--> ");

        if (input == 1) {
            Guestrecord();
        } else if (input == 2) {
            viewroomrecords();
        } else if (input == 3) {
            tracking();
        } else {
            System.out.println("Invalid option");
        }
    }

    static void Guestrecord() {
        Scanner sc = new Scanner(System.in);
        HashMap<Integer, guestdata> hm = new HashMap<>();
        List<guestdata> list = new ArrayList<>();

        System.out.println("1 --> Individual Records");
        System.out.println("2 --> All Records");
        int record = getSafeInt(sc, "--> ");

        final String filename = "datafile.txt";
        try {
            list = guestdata.fromfilestring(filename);
            System.out.println("Records loaded: " + list.size());
            for (guestdata g : list) {
                hm.put(g.getid(), g);
            }
        } catch (Exception e) {
            System.out.println("Error Occurred!");
            return;
        }

        if (record == 1) {
            int guestid = getSafeInt(sc, "Enter the Guest id: ");
            if (hm.containsKey(guestid)) {
                System.out.println(hm.get(guestid));
            } else {
                System.out.println("Guest ID not found.");
            }

        } else if (record == 2) {
            for (guestdata g : list) {
                System.out.println(g);
            }
        } else {
            System.out.println("Invalid Input");
        }
    }

    static void viewroomrecords() {
        Scanner sc = new Scanner(System.in);
        HashMap<Integer, String> roommap = new HashMap<>();

        File file = new File("allotfile.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length >= 4) {
                    int roomnumber = Integer.parseInt(parts[2]);
                    roommap.put(roomnumber, line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading room records.");
        }

        System.out.println("1 --> Individual Room Records");
        System.out.println("2 --> All Room Records");
        int record1 = getSafeInt(sc, "--> ");

        if (record1 == 1) {
            int roomid = getSafeInt(sc, "Enter the Room number: ");
            if (roommap.containsKey(roomid)) {
                System.out.println(roommap.get(roomid));
            } else {
                System.out.println("No record found for Room number " + roomid);
            }
        } else if (record1 == 2) {
            System.out.println("All Room Records:");
            for (String record : roommap.values()) {
                System.out.println(record);
            }
        }
    }

    static void tracking() {
        Trackrecords tr = new Trackrecords();
        tr.track();
    }
}
