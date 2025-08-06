
import java.io.*;
import java.util.Scanner;
public class alloting {
    Scanner sc = new Scanner(System.in);
    public void option(){
        System.out.println("Want to see:");
        System.out.println("1. Single record");
        System.out.println("2. All records");
        System.out.print("Enter your choice: ");
        int options = sc.nextInt();
        sc.nextLine(); // consume newline

    if (options == 1) {
        alloted();
    } else if(options == 2) {
        allotall();
    }
    }
    public void alloted(){
        
        int roomid;
        System.out.print("Enter the roomid : ");
        roomid = sc.nextInt();
        sc.nextLine();
        File file = new File("allotroom.txt");
        if(!file.exists()){
            System.out.println("File not found .");
        }
        try {
            Boolean found;
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                found = false;
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains("ID: " + roomid)) {
                        System.out.println(" Allotted Room Info: " + line);
                        found = true;
                    }
                }
            }
            if(!found){
                System.out.println("data does not found");
            }

        } catch (IOException e) {
            System.out.println("Invlid option");
        }
        System.out.println();
    }
    public void allotall(){
        File file = new File("allotroom.txt");
        if(!file.exists()){
            System.out.println("File not found .");
        }
        try {
            Boolean found;
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                found = false;
                String line;
                while ((line = br.readLine()) != null) {
                        System.out.println(" Allotted Room Info: \n" + line);
                        found = true;
                }
            }
            if(!found){
                System.out.println("data does not found");
            }

        } catch (IOException e) {
            System.out.println("Invlid option");
        }
        System.out.println();
    }
}
