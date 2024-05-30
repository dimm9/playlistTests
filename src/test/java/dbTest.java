import music.DatabaseConnection;
import music.Song;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class dbTest {
    @BeforeAll
    public static void openDB(){
        DatabaseConnection.connect("src/main/java/music/songs.db", "connection");
    }
    @Test
    public void rightSongRead(){
        try {
            Optional<Song> song = Song.Persistence.read(1);
            assertTrue(song.isPresent());
        } catch (SQLException e) {
            System.err.println(e.getErrorCode());
        }

    }
    @Test
    public void wrongSongRead(){
        try {
            Optional<Song> song = Song.Persistence.read(1000);
            assertTrue(song.isEmpty());
        } catch (SQLException e) {
            System.err.println(e.getErrorCode());
        }

    }

    @ParameterizedTest
    @MethodSource("provideIndexesAndExpectedSongs")
    public void testReadParameterizedStream(int index, Song expectedSong) throws SQLException {
        Optional<Song> song = Song.Persistence.read(index);
        assertEquals(expectedSong, song.orElse(null));
    }
    static Stream<Arguments> provideIndexesAndExpectedSongs() {
        return Stream.of(
                arguments(3, new Song("Led Zeppelin", "Stairway to Heaven", 482)),
                arguments(47, new Song("The Doors", "Riders on the Storm", 434)),
                arguments(-1, null),
                arguments(100, null)
    );
    }
    @ParameterizedTest
    @CsvFileSource(resources = "music/songs.csv", numLinesToSkip = 1)
    public void parametrizedByFile(String artist, String title, int duration) {
        assertNotNull(title);
        assertNotNull(artist);
        assertNotNull(duration);
        Song song = new Song(artist, title, duration);
        assertNotNull(song);
    }
    @AfterAll
    public static void closeDb(){
        DatabaseConnection.disconnect("connection");
    }
}
