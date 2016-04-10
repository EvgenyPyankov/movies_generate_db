public class Main {
    public static void main(String[] args) {
        final int N = 10000;

        Generator generator = new Generator();
        generator.generate();

//        Gen generator = new Gen(N);
//        generator.run();


//
//        try {
//
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//
//        } catch (ClassNotFoundException e) {
//
//            System.out.println("Where is your Oracle JDBC Driver?");
//            e.printStackTrace();
//            return;
//
//        }
//
//        System.out.println("Oracle JDBC Driver Registered!");
//
//        Connection connection = null;
//
//        try {
//            Locale.setDefault(Locale.ENGLISH);
//            connection = DriverManager.getConnection(
//                    "jdbc:oracle:thin:@localhost:1521:xe", "evgeny",
//                    "root");
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery("Select * from aims");
//            while(rs.next()) {
//                //Retrieve by column name
//                int id = rs.getInt("id_aim");
//                String name = rs.getString("name");
//
//                //Display values
//                System.out.print("ID: " + id);
//                System.out.println("Name: "+name);
//            }
//
//        } catch (SQLException e) {
//
//            System.out.println("Connection Failed! Check output console");
//            e.printStackTrace();
//            return;
//
//        }
//
//        if (connection != null) {
//            System.out.println("You made it, take control your database now!");
//        } else {
//            System.out.println("Failed to make connection!");
//        }
   }

}
