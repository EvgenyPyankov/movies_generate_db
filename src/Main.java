import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        final int N = 10000;

//        Generator generator = new Generator();
//        generator.generate();

        DBController dbController = new DBController();
        try {
            //dbController.getInfoAboutMovie(1000);
            dbController.run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
