import java.io.*;
import java.util.*;
public class statuschecker {
    Scanner sc = new Scanner(System.in);
    public void status(){
        System.out.println("1. Want to print status by roomid");
        System.out.println("2. Want to print all status record");
        int status = sc.nextInt();

        if(status == 1){
            singlestatus();
        }else if(status == 2){
            allstatus();
        }
    }
    public void singlestatus(){
        try {
        System.out.print("Enter the roomid");
        int input = sc.nextInt();

        File file = new File("roomstatus.txt");
        
        BufferedReader bw = new BufferedReader(new FileReader(file));
        boolean found = false;
        String line;
        while((line = bw.readLine()) != null){
            if(line.startsWith("Room " + input + " status")){
                System.out.print(line);
                found = true;
                break;
            }
        }

        if(!found){
            System.out.println("The Room is not exist!");
        }

        } catch (Exception e) {
            System.out.println("Error Occured");
        }
        File file = new File("roomstatus.txt");

    }
    static void allstatus(){
    File file = new File("roomstatus.txt");
    if (!file.exists()) {
        System.out.println("No room status data available.");
        return;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        System.out.println("\n------ Room Status ------");
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println("-------------------------\n");
    }catch (Exception e) {
        System.out.println("Error reading room status: " + e.getMessage());
    }
}
}
