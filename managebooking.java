import java.io.*;
import java.util.*;

public class managebooking {

    public void manage() {
        Scanner sc = new Scanner(System.in);
        int command = -1;

        while (true) {
            try {
                System.out.println("\nEnter your command");
                System.out.println("1 --> Update guest");
                System.out.println("2 --> Delete guest");
                System.out.println("0 --> Exit");
                System.out.print("Your choice: ");
                command = sc.nextInt();
                sc.nextLine(); // consume newline

                if (command == 1) {
                    updateguest();
                } else if (command == 2) {
                    deleteguest();
                } else if (command == 0) {
                    System.out.println("Exiting guest management...");
                    break;
                } else {
                    System.out.println("Invalid command. Try again.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // clear wrong input
            }
        }
    }

    static void updateguest() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Enter the guest room ID to update: ");
            int update = sc.nextInt();
            sc.nextLine(); // consume newline

            File inputFile = new File("data.txt");
            File tempFile = new File("temp.txt");
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));

            boolean found = false;
            String line;

            while ((line = br.readLine()) != null) {
                Guests g = Guests.fromfilestring(line);
                if (g != null && update == g.getId()) {
                    found = true;

                    System.out.print("Enter the name of the guest: ");
                    String name = sc.nextLine();

                    System.out.print("Is there any room partner (yes/no)? ");
                    String roomcmd = sc.nextLine();
                    String roompartner = roomcmd.equalsIgnoreCase("yes") ? sc.nextLine() : "none";

                    System.out.print("Enter the Age of the guest: ");
                    int age = sc.nextInt();
                    sc.nextLine(); // consume newline

                    System.out.print("Enter the ID proof: ");
                    String idproof = sc.nextLine();

                    g = new Guests(g.getId(), name, roompartner, age, idproof);
                    bw.write(g.toFileString());
                } else {
                    bw.write(line);
                }
                bw.newLine();
            }

            br.close();
            bw.close();

            if (found) {
                if (inputFile.delete() && tempFile.renameTo(inputFile)) {
                    System.out.println("Guest updated successfully!");
                } else {
                    System.out.println("Error replacing original file.");
                }
            } else {
                tempFile.delete();
                System.out.println("Guest not found.");
            }

        } catch (Exception e) {
            System.out.println("Error while updating guest: " + e.getMessage());
        }
    }

    static void deleteguest() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Enter the guest room ID to delete: ");
            int deleteId = sc.nextInt();
            sc.nextLine(); // clear buffer

            File inputFile = new File("data.txt");
            File tempFile = new File("temp.txt");

            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));

            boolean found = false;
            String line;

            while ((line = br.readLine()) != null) {
                Guests g = Guests.fromfilestring(line);
                if (g != null && deleteId == g.getId()) {
                    found = true;
                    continue;
                } else {
                    bw.write(line);
                    bw.newLine();
                }
            }

            br.close();
            bw.close();

            if (found) {
                if (inputFile.delete() && tempFile.renameTo(inputFile)) {
                    System.out.println("Guest deleted from data.txt.");
                } else {
                    System.out.println("Error updating guest data.");
                    return;
                }
            } else {
                tempFile.delete();
                System.out.println("Guest not found.");
                return;
            }

            // Update allotroom.txt
            File allotFile = new File("allotroom.txt");
            File tempAllotFile = new File("temp_allot.txt");

            BufferedReader brAllot = new BufferedReader(new FileReader(allotFile));
            BufferedWriter bwAllot = new BufferedWriter(new FileWriter(tempAllotFile));

            String allotLine;
            boolean roomFound = false;

            while ((allotLine = brAllot.readLine()) != null) {
                if (allotLine.contains("ID: " + deleteId)) {
                    roomFound = true;
                    continue;
                }
                bwAllot.write(allotLine);
                bwAllot.newLine();
            }

            brAllot.close();
            bwAllot.close();

            if (allotFile.delete() && tempAllotFile.renameTo(allotFile)) {
                if (roomFound)
                    System.out.println("Room " + deleteId + " is deleted");
            } else {
                System.out.println("Error while deleting");
            }

        } catch (Exception e) {
            System.out.println("Error while deleting guest: " + e.getMessage());
        }
    }
}
