import java.io.*;
import java.util.HashMap;

public class Trackrecords {
    static HashMap<Integer,String> tracker = new HashMap<>();

    public void track(){
        final String filename = "statusfile.txt";

        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(" ");
                if(parts.length >= 3){
                    int roomnumber = Integer.parseInt(parts[1]);
                    tracker.put(roomnumber, line);
                }
            }
        } catch (Exception e) {
        }

        for(String records : tracker.values()){
            System.out.println(records);
        }
    }
}
