import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;


public class deleterec {
    static Scanner sc = new Scanner(System.in);
    public void delete(){
        System.out.println("Enter Your task");
        System.out.println("1 -> Delete Guest data");
        System.out.println("2 -> Delete Alloted room data");
        int deleted = sc.nextInt();

        if(deleted == 1){
            deleteguestdata();
        }else if(deleted == 2){
            deleteroomalloting();
        }
    }
    static void deleteguestdata(){

        File file = new File("datafile.txt");
        List<guestdata> guestlist = new LinkedList<>();
        HashMap<Integer,guestdata> map = new HashMap<>();

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(",");
                if(parts.length >= 5){
                    int guestid = Integer.parseInt(parts[0]);
                    String name = parts[1].trim();
                    String roompartner  = parts[2].trim();
                    int Age  = Integer.parseInt(parts[3].trim());
                    String idproof  = parts[4].trim();
                    guestdata gd = new guestdata(guestid, name, roompartner, Age, idproof);
                    guestlist.add(gd);
                    map.put(guestid, gd);
                }
            }
        }catch(Exception e){
            System.out.println("Error Occured");
        }

        System.out.println("Enter the Guest id : ");
        int guestid = sc.nextInt();

        if(!map.containsKey(guestid)){
            System.out.println("Entered guestid doesnt exist!");
            return;
        }

        guestlist.remove(map.get(guestid));
        map.remove(guestid);

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for(guestdata g : guestlist){
                bw.write(g.tofileString());
                bw.newLine();
            }
        } catch (Exception e) {
        }

        System.out.println("Guest deleted Successfully !");

    }
    static void deleteroomalloting() {
    File file = new File("allotfile.txt");
    List<String> guestlist = new LinkedList<>();
    HashMap<Integer, Integer> map = new HashMap<>();

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        int index = 0;
        while ((line = br.readLine()) != null) {
            guestlist.add(line);
            String[] parts = line.split("\\s+");
            if (parts.length >= 8) { // assuming 3rd element is room number
                try {
                    int roomnumber = Integer.parseInt(parts[2]);
                    map.put(roomnumber, index);
                } catch (NumberFormatException e) {
                    // skip invalid lines
                }
            }
            index++;
        }
    } catch (Exception e) {
        System.out.println("Error reading file: " + e.getMessage());
        return;
    }

    System.out.print("Enter your room number: ");
    int roomnumber = sc.nextInt();
    sc.nextLine();

    if (!map.containsKey(roomnumber)) {
        System.out.println("Room number does not exist!");
        return;
    }

    int lineIndex = map.get(roomnumber);
    guestlist.remove(lineIndex);

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
        for (String g : guestlist) {
            bw.write(g);
            bw.newLine();
        }
    } catch (Exception e) {
        System.out.println("Error writing file: " + e.getMessage());
    }

    System.out.println("Room allotment for room " + roomnumber + " deleted successfully.");
}

}
