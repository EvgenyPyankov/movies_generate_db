import java.sql.*;
import java.util.*;

public class Generator implements Constants {
    Connection conn;
    Statement stmt;

    public Generator() {
        DBController db = new DBController();
        conn = db.getConnection();
        stmt=db.getStatement();
    }

    public void generate(){
        try {
            addGenres();
            addRatings();
            addActors();
            addMovies();
            allocateGenres();
            allocateActors();
            allocateRatings();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void addGenres() throws SQLException {
        String query = "insert into genres (id,name) values (?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);

        for (int i = 0; i < GENRES_NAMES.length; i++) {
            ps.setLong(1, i);
            ps.setString(2, GENRES_NAMES[i]);
            ps.addBatch();
        }
        ps.executeBatch();
        ps.close();

    }

    public void addRatings() throws SQLException {
        String query = "insert into ratings (id,name) values (?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);

        for (int i = 0; i < RATING_SITES.length; i++) {
            ps.setLong(1, i);
            ps.setString(2, RATING_SITES[i]);
            ps.addBatch();
        }
        ps.executeBatch();
        ps.close();
    }

    public void addActors() throws SQLException {
        String sql = "insert into actors (id, gender,name,surname) values (?, ?, ?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        final int batchSize = 1000;
        String gender = "";
        String name = "";
        String surname = "";

        for (long i = 0; i < ACTORS_NUMBER; i++) {
            int gen = Rand.getRand(2);
            if (gen > 0) {
                gender = "m";
                name = M_NAMES[Rand.getRand(M_NAMES.length)];
                surname = M_SURNAMES[Rand.getRand(M_SURNAMES.length)];
            } else {
                gender = "f";
                name = F_NAMES[Rand.getRand(F_NAMES.length)];
                surname = F_SURNAMES[Rand.getRand(F_SURNAMES.length)];
            }
            ps.setLong(1, i);
            ps.setString(2, gender);
            ps.setString(3, name);
            ps.setString(4, surname);
            ps.addBatch();
            System.out.println("Generated actor #"+i);

            if (i % batchSize == 0) {
                ps.executeBatch();
            }
        }
        ps.executeBatch(); // insert remaining records
        ps.close();
    }

    public void addMovies() throws SQLException{
        String sql = "insert into movies (id, name) values (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        final int batchSize = 1000;
        String name = "";

        for (long i = 0; i < MOVIES_NUMBER; i++) {

            name = "Movie #"+i;
            ps.setLong(1, i);
            ps.setString(2, name);
            ps.addBatch();
            System.out.println("Generated movie #"+i);

            if (i % batchSize == 0) {
                ps.executeBatch();
            }
        }
        ps.executeBatch(); // insert remaining records
        ps.close();
    }

    private boolean contains(long[]arr, long item){
        for (int i=0; i<arr.length; i++){
            if (arr[i]==item)
                return true;
        }
        return false;
    }

    public void allocateGenres()throws SQLException{
        String query = "insert into movies_genres (movie_id,genre_id) values (?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);

        for (int i=0; i<MOVIES_NUMBER; i++){
            int numberOfGenres = Rand.getRand(4)+1;
            long[]arr = new long[numberOfGenres];
            int index;
            for (int j=0; j<numberOfGenres; j++) {
                do{
                    index = Rand.getRand(GENRES_NAMES.length);
                }
                while (contains(arr,index));
                ps.setLong(1,i);
                ps.setLong(2,index);
                ps.addBatch();
                System.out.println("Allocated genres movie #"+i);
            }
            ps.executeBatch();

        }
        ps.close();
    }

    public void allocateActors()throws SQLException{
        String query = "insert into movies_actors (movie_id,actor_id) values (?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);

        for (int i=0; i<MOVIES_NUMBER; i++){
            int numberOfActors = Rand.getRand(AVERAGE_ACTORS_CAST_MOVIE_NUMBER)+1;
            long[]arr = new long[numberOfActors];
            int index;
            for (int j=0; j<numberOfActors; j++) {
                do{
                    index = Rand.getRand(ACTORS_NUMBER);
                }
                while (contains(arr,index));
                ps.setLong(1,i);
                ps.setLong(2,index);
                ps.addBatch();
                System.out.println("Allocated actor movie #"+i);
            }
            ps.executeBatch();
        }
        ps.close();
    }

    public void allocateRatings()throws SQLException{
        String query = "insert into movies_ratings (movie_id,rating_id,value) values (?, ?,?)";
        PreparedStatement ps = conn.prepareStatement(query);

        for (int i=0; i<MOVIES_NUMBER; i++){

            for (int j=0; j<RATING_SITES.length; j++) {

                ps.setLong(1,i);
                ps.setLong(2,j);
                ps.setInt(3,Rand.getRand(100)+1);
                ps.addBatch();
                System.out.println("Allocated ratings movie #"+i);

            }
            ps.executeBatch();
        }
        ps.close();
    }
}
