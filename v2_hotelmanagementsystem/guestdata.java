import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class guestdata {
    private int guestid;
    private String name;
    private String roompartner;
    private int Age;
    private String idproof;

    public guestdata(int guestid,String name, String roompartner, int Age, String idproof){
        this.guestid = guestid;
        this.name = name;
        this.roompartner = roompartner;
        this.Age = Age;
        this.idproof = idproof;
    }

    public int getid(){
        return guestid;
    }

    public String tofileString(){
        return guestid + "," + name + "," + roompartner + "," + Age + "," + idproof;
    }

    public static List<guestdata> fromfilestring(String filename) throws IOException {
        List<guestdata> list = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",", -1);
                if (parts.length == 5) {
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        String roompartner = parts[2].trim();
                        int age = Integer.parseInt(parts[3].trim());
                        String idproof = parts[4].trim();
                        guestdata g = new guestdata(id, name, roompartner, age, idproof);
                        list.add(g);
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid guest record: " + line);
                    }
                }
            }
        }
        return list;
    }
    @Override
    public String toString(){
        return name + " | " + roompartner + " | " + Age + " | " + idproof;
    }
}
