import music.*;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import javax.naming.AuthenticationException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AccountTest {
    @BeforeAll
    public static void setupDatabase() throws SQLException {
        DatabaseConnection.connect("src/main/java/music/songs.db", "connection");
        ListenerAccount.Persistence.init();
    }


    @Test
    public void testRegistration() {
        try {
            int id = ListenerAccount.Persistence.register("user", "password");
            assertNotEquals(0, id);
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
    @Test
    public void testAuthentication() {
        try {
            int id = ListenerAccount.Persistence.register("user", "password");
            ListenerAccount authenticatedAccount = ListenerAccount.Persistence.authenticate("test_user", "test_password");
            assertNotNull(authenticatedAccount);
            assertEquals(id, authenticatedAccount.getId());
        } catch (SQLException | AuthenticationException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
    @Test
    public void testInitialCredits() {
        try {
            ListenerAccount account = new ListenerAccount(1, "test_user");
            assertEquals(0, account.getCredit());
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testAddCredits() {
        try {
            ListenerAccount account = new ListenerAccount(1, "test_user");
            int initialCredits = account.getCredit();
            int addedCredits = 100;
            account.addCredit(addedCredits);
            assertEquals(initialCredits + addedCredits, account.getCredit());
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
    @Test
    public void testBuySongExists() throws SQLException, NotEnoughCreditsException {
        ListenerAccount account = new ListenerAccount(2, "Bob");
        ListenerAccount.Persistence.addSong(2, 3);
        account.buySong(3);
        assertEquals(0, account.getCredit());
    }

    @Test
    public void testCreatePlaylist() throws SQLException, NotEnoughCreditsException {
        List<Integer> songIds = new ArrayList<>();
        songIds.add(1);
        songIds.add(2);
        Playlist predefinedPlaylist = new Playlist();
        Song song1 = new Song("The Beatles","Hey Jude",431);
        Song song2 = new Song("The Rolling Stones","(I Can't Get No) Satisfaction",224);
        predefinedPlaylist.add(song1);
        predefinedPlaylist.add(song2);
        ListenerAccount listenerAccount = new ListenerAccount(1, "test");
        Playlist createdPlaylist = listenerAccount.createPlaylist(songIds);
        assertEquals(predefinedPlaylist.size(), createdPlaylist.size());
        for (int i = 0; i < predefinedPlaylist.size(); i++) {
            assertEquals(predefinedPlaylist.get(i), createdPlaylist.get(i));
        }
    }

    @Test
    public void testBuySongNotExists() throws SQLException, NotEnoughCreditsException {
        ListenerAccount account = new ListenerAccount(2, "Bob");
        ListenerAccount.Persistence.addSong(2, 5);
        account.buySong(3);
        assertEquals(0, account.getCredit());
    }
    @Test
    public void testBuySongCreditsZero(){
        ListenerAccount account = new ListenerAccount(2, "Bob");
        assertThrows(NotEnoughCreditsException.class, () -> account.buySong(1));
    }


    @AfterAll
    public static void disconnectDatabase() throws SQLException {
        DatabaseConnection.disconnect();
    }

}
