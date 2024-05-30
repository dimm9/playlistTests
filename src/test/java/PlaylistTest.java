import music.Playlist;
import music.Song;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlaylistTest {
    @Test
    public void emptyTest(){
        Playlist playlist = new Playlist();
        assertTrue(playlist.isEmpty());
    }

    @Test
    public void addOne(){
        Playlist playlist = new Playlist();
        playlist.add(new Song("Heavy Young Heathens", "Being Evil Has a Price", 194));
        assertEquals(1, playlist.size());
    }

    @Test
    public void addSameOne(){
        Playlist playlist = new Playlist();
        Song song = new Song("Heavy Young Heathens", "Being Evil Has a Price", 194);
        playlist.add(song);
        assertEquals(song, playlist.getFirst());
    }

    @Test
    public void addSame(){
        Playlist playlist = new Playlist();
        Song song = new Song("Heavy Young Heathens", "Being Evil Has a Price", 194);
        playlist.add(song);
        Song song2 = new Song("Heavy Young Heathens", "Being Evil Has a Price", 194);
        assertEquals(song2, playlist.getFirst());
    }

    @Test
    public void atSecondCheck(){
        Playlist playlist = new Playlist();
        Song song1 = new Song("Heavy Young Heathens", "Being Evil Has a Price", 203);
        Song song2 = new Song("The Forest Rangers", "John The Revelator", 334);
        Song song3 = new Song("Lee DeWyze", "Blackbird Song", 254);
        playlist.add(song1);
        playlist.add(song2);
        playlist.add(song3);
        assertEquals(song1, playlist.atSecond(100));
        assertEquals(song2, playlist.atSecond(400));
        assertEquals(song3, playlist.atSecond(600));
    }
    @Test
    public void shortTimeTest(){
        Playlist playlist = new Playlist();
        IndexOutOfBoundsException e = assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(-5));
        assertEquals("Time is too short: -5", e.getMessage());
    }

    @Test
    public void longTimeTest(){
        Playlist playlist = new Playlist();
        IndexOutOfBoundsException e = assertThrows(IndexOutOfBoundsException.class, () -> playlist.atSecond(1000));
        assertEquals("Time is longer than the whole playlist duration: 1000", e.getMessage());
    }

}
