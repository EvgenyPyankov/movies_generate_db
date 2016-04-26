import javax.xml.transform.Result;
import java.sql.*;
import java.util.Locale;

public class DBController {
    Connection conn;
    Statement stmt;

    public DBController(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            return;
        }

        try {
            Locale.setDefault(Locale.ENGLISH);
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "movies", "root");
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            return;
        }
    }

    public void getInfoAboutMovie(int movieId) throws SQLException{
        PreparedStatement ps;
        ResultSet rs;
        String actorsQuery = "select a.name, a.surname from actors a, movies_actors ma where ma.movie_id = ? and a.id=ma.actor_id";
        String movieQuery="select name from movies where id = ?";
        String ratingsQuery="select r.name, mr.value\n" +
                "from ratings r, movies_ratings mr\n" +
                "where mr.movie_id = ?\n" +
                "and r.id=mr.rating_id";
        String genresQuery="select g.name\n" +
                "from genres g, movies_genres mg\n" +
                "where mg.movie_id = ?\n" +
                "and g.id= mg.genre_id";

        ps = conn.prepareStatement(movieQuery);
        ps.setLong(1,movieId);
        rs = ps.executeQuery();
       // System.out.println("ID: "+movieId);
        //if (rs.next() )System.out.println(rs.getString("name"));

        ps = conn.prepareStatement(actorsQuery);
        ps.setInt(1,movieId);
        rs =  ps.executeQuery();
        //System.out.println("Actors:");
        while(rs.next()){
            String name = rs.getString("name");
            String surname = rs.getString("surname");

            //System.out.println(name+" "+surname);
        }

        ps = conn.prepareStatement(genresQuery);
        ps.setInt(1,movieId);
        rs =  ps.executeQuery();
       // System.out.println("Genres:");
        while(rs.next()){
            String name = rs.getString("name");

          //  System.out.println(name);
        }

        ps = conn.prepareStatement(ratingsQuery);
        ps.setInt(1,movieId);
        rs =  ps.executeQuery();
       // System.out.println("Ratings:");
        while(rs.next()){
            String name = rs.getString("name");
            int value = rs.getInt("value");

            //Display values
           // System.out.println(name+": "+value);
        }

    }

    public void run() throws SQLException{
        String query;
        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        getInfoAboutMovie(1000);
        endTime = System.currentTimeMillis();
        System.out.println(("Get all info about movie: "+(endTime-startTime)+" millis"));

        query = "Select name from movies where id =1000";
        startTime = System.nanoTime();
        stmt.executeQuery(query);
        endTime = System.nanoTime();
        System.out.println(("Search for a singe field by movie id: "+(endTime-startTime)+" nanos"));

        query = "Select * from movies where id =1000";
        startTime = System.nanoTime();
        stmt.executeQuery(query);
        endTime = System.nanoTime();
        System.out.println(("Search for all fields by movie id: "+(endTime-startTime)+" nanos"));

        query = "Select * from movies where name like 'Movie #1000'";
        startTime = System.currentTimeMillis();
        stmt.executeQuery(query);
        endTime = System.currentTimeMillis();
        System.out.println(("Search for all fields by certain field value: "+(endTime-startTime)+" millis"));

    }

    public Connection getConnection(){
        if (conn!=null) return conn;
        return null;
    }

    public Statement getStatement(){
        if (stmt!=null) return stmt;
        return null;
    }


}
