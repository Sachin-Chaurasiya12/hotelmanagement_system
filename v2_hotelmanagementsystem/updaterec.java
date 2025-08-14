import java.io.*;
import java.util.*;

public class updaterec {
    static Scanner sc = new Scanner(System.in);

    public void update() {
        int input;
        while (true) {
            System.out.println("What you want to Update : ");
            System.out.println("1 --> Update data");
            System.out.println("2 --> Update Roomallocation");
            System.out.print(" --> ");

            if (sc.hasNextInt()) {
                input = sc.nextInt();
                sc.nextLine(); // clear newline
                break;
            } else {
                System.out.println("Error: Please enter only numbers (1 or 2).");
                sc.nextLine(); // clear invalid input
            }
        }

        switch (input) {
            case 1 -> dataupdate();
            case 2 -> updateroomallot();
            default -> System.out.println("Invalid input!");
        }
    }

    static void dataupdate() {
        List<guestdata> list = new ArrayList<>();
        HashMap<Integer, guestdata> map = new HashMap<>();
        File file = new File("datafile.txt");

        // Step 1: Read all guest data
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    try {
                        int guestid = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        String roompartner = parts[2].trim();
                        int age = Integer.parseInt(parts[3].trim());
                        String idproof = parts[4].trim();

                        guestdata gd = new guestdata(guestid, name, roompartner, age, idproof);
                        list.add(gd);
                        map.put(guestid, gd);
                    } catch (Exception ignored) {
                        // Skip invalid line
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Data file not found or unreadable.");
            return;
        }

        // Step 2: Validate Guest ID
        int guestid;
        while (true) {
            System.out.print("Enter the Guest ID to update: ");
            if (sc.hasNextInt()) {
                guestid = sc.nextInt();
                sc.nextLine();
                break;
            } else {
                System.out.println("Error: Only numbers are allowed.");
                sc.nextLine();
            }
        }

        if (!map.containsKey(guestid)) {
            System.out.println("Guest ID does not exist!");
            return;
        }

        // Step 3: Input new details
        System.out.print("Enter new name: ");
        String name = sc.nextLine();

        System.out.print("Room partner? (yes/no): ");
        String roompartner = null;
        String partnerAns = sc.nextLine();
        if (partnerAns.equalsIgnoreCase("yes")) {
            System.out.print("Enter partner name: ");
            roompartner = sc.nextLine();
        }

        int age;
        while (true) {
            System.out.print("Enter new age: ");
            if (sc.hasNextInt()) {
                age = sc.nextInt();
                sc.nextLine();
                break;
            } else {
                System.out.println("Error: Only numbers are allowed.");
                sc.nextLine();
            }
        }

        System.out.print("Enter new ID proof: ");
        String idproof = sc.nextLine();

        // Step 4: Update in list
        guestdata updated = new guestdata(guestid, name, roompartner, age, idproof);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getid() == guestid) {
                list.set(i, updated);
                break;
            }
        }

        // Step 5: Write back all records
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (guestdata g : list) {
                bw.write(g.tofileString());
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error writing file: " + e.getMessage());
        }

        System.out.println("Guest data updated successfully!");
    }

    static void updateroomallot() {
        int roomnumber;
        while (true) {
            System.out.print("Enter the room number : ");
            if (sc.hasNextInt()) {
                roomnumber = sc.nextInt();
                sc.nextLine();
                break;
            } else {
                System.out.println("Error: Only numbers are allowed for Room Number.");
                sc.nextLine();
            }
        }

        int guestid;
        while (true) {
            System.out.print("Enter the guestid : ");
            if (sc.hasNextInt()) {
                guestid = sc.nextInt();
                sc.nextLine();
                break;
            } else {
                System.out.println("Error: Only numbers are allowed for Guest ID.");
                sc.nextLine();
            }
        }

        File file = new File("allotfile.txt");
        ArrayList<String> lists = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {
                lists.add(line);
                String[] parts = line.split(" ");
                if (parts.length >= 8) {
                    try {
                        int room = Integer.parseInt(parts[2]);
                        map.put(room, index);
                    } catch (NumberFormatException ignored) {}
                }
                index++;
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        if (!map.containsKey(roomnumber)) {
            System.out.println("The room number does not exist");
            return;
        }

        int lineIndex = map.get(roomnumber);
        String oldLine = lists.get(lineIndex);
        String[] parts = oldLine.split(" ");
        if (parts.length >= 2) {
            parts[parts.length - 1] = String.valueOf(guestid);
            String updatedLine = String.join(" ", parts);
            lists.set(lineIndex, updatedLine);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String l : lists) {
                bw.write(l);
                bw.newLine();
            }
            System.out.println("Guest ID updated successfully.");
        } catch (Exception e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
