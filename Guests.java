
public class Guests {
        int roomId;
        private String name;
        private String roompartner;
        private int age;
        private String idproof;
        private int roomid;
        public Guests(int roomId, String name, String roompartner, int age, String idproof) {
            this.roomId = roomId;
            this.name = name;
            this.roompartner = roompartner;
            this.age = age;
            this.idproof = idproof;
        }

        public int getId() {
            return roomId;
        }

        public String getName() {
            return name;
        }

        public String toFileString() {
            return roomId + "," + name + "," + roompartner + "," + age + "," + idproof;
        }

        public static Guests fromfilestring(String line) {
            try {
                if (line.trim().isEmpty())
                    return null;
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int roomId = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String roompartner = parts[2].trim();
                    int age = Integer.parseInt(parts[3].trim());
                    String idproof = parts[4].trim();
                    return new Guests(roomId, name, roompartner, age, idproof);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid data line: " + line);
            }
            return null;
        }

        @Override
        public String toString() {
            return roomId + ": name: " + name + " | roompartner: " + roompartner + " | Age: " + age + " | Id proof: " + idproof;
        }
    }