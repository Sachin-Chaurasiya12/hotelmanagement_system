import java.io.*;
import java.util.*;

public class Managerec {

    static Scanner sc = new Scanner(System.in);

    public void manage() {
        int input = safeIntInput("Enter your task\n1 -> Update Records\n2 -> Delete Records\n3 -> Manage Status\n --> ");

        if (input == 1) {
            update();
        } else if (input == 2) {
            delete();
        } else if (input == 3) {
            statusrecords();
        } else {
            System.out.println("Invalid Input!");
        }
    }

    static void update() {
        try {
            updaterec ur = new updaterec();
            ur.update();
        } catch (Exception e) {
            System.out.println("Error while updating records: " + e.getMessage());
        }
    }

    static void delete() {
        try {
            deleterec dr = new deleterec();
            dr.delete();
        } catch (Exception e) {
            System.out.println("Error while deleting records: " + e.getMessage());
        }
    }

    static void statusrecords() {
        int roomnumber = safeIntInput("Enter the room number : ");

        ArrayList<String> lists = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        File file = new File("statusfile.txt");

        if (!file.exists()) {
            System.out.println("Error: status file not found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {
                lists.add(line);
                String[] parts = line.split("\\s+");
                if (parts.length >= 4) {
                    try {
                        int roomno = Integer.parseInt(parts[1]);
                        map.put(roomno, index);
                    } catch (NumberFormatException e) {
                        System.out.println("Warning: Skipping invalid room number format in file.");
                    }
                }
                index++;
            }

            if (map.containsKey(roomnumber)) {
                int lastindex = map.get(roomnumber);
                System.out.println("Current Status --> " + lists.get(lastindex));

                System.out.print("Mark Status (Occupied/Vacant) : ");
                String ov = sc.nextLine().trim();

                lists.set(lastindex, "Room " + roomnumber + " : " + ov);

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                    for (String l : lists) {
                        bw.write(l);
                        bw.newLine();
                    }
                }

                System.out.println("Status Updated Successfully");
            } else {
                System.out.println("No record found for room number " + roomnumber);
            }

        } catch (IOException e) {
            System.out.println("Error reading or writing file: " + e.getMessage());
        }
    }

    // Safe integer input helper method
    static int safeIntInput(String message) {
        while (true) {
            System.out.print(message);
            if (sc.hasNextInt()) {
                int val = sc.nextInt();
                sc.nextLine(); // clear newline
                return val;
            } else {
                System.out.println("Error: Please enter a valid number!");
                sc.nextLine(); // discard bad input
            }
        }
    }
}
