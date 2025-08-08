import java.util.*;
public class Viewrecords{
    public void Execution(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Option you Want to view");
        System.out.println("1 --> Guest Records");
        System.out.println("2 --> Alloted room records Records");
        System.out.println("3 --> Track room Records");
        System.out.print("--> ");
        int input = sc.nextInt();

        switch (input) {
            case 1:
                Guestrecord();
                break;
            default:
                System.out.println("invalid Input");
        }
    }
    static void Guestrecord(){
        Scanner sc = new Scanner(System.in);
    HashMap<Integer, guestdata> hm = new HashMap<>();
    List<guestdata> list = new ArrayList<>();

    System.out.println("1 --> Individual Records");
    System.out.println("2 --> All Records");
    int record = sc.nextInt();
    sc.nextLine();

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
        System.out.print("Enter the Guest id : ");
        int guestid = sc.nextInt();

        if (hm.containsKey(guestid)) {
            System.out.println(hm.get(guestid));
        } else {
            System.out.println("Guest ID not found.");
        }

    } else if (record == 2) {
        for (guestdata g : list) {
            System.out.println(g);  // Assumes guestdata.toString() is properly overridden
        }

    } else {
        System.out.println("Invalid Input");
    }
    }
}

