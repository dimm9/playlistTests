package music;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class Song {
    private String artist;
    private String title;
    private int duration;


    public Song(String artist, String title, int duration) {
        this.artist = artist;
        this.title = title;
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return duration == song.duration && Objects.equals(artist, song.artist) && Objects.equals(title, song.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, title, duration);
    }

    public int getDuration() {
        return duration;
    }

    public static class Persistence {
        public static Optional<Song> read(int index) throws SQLException {
            Connection c = DatabaseConnection.getConnection("connection");
            String query = "SELECT artist, title, length FROM song WHERE id = ?";
            PreparedStatement statement = c.prepareStatement(query);
            statement.setInt(1, index);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                return Optional.of(new Song(
                        results.getString("artist"),
                        results.getString("title"),
                        results.getInt("length")
                ));
            }
            return Optional.empty();
        }
    }
}