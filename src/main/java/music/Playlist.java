package music;

import java.util.ArrayList;

public class Playlist extends ArrayList<Song> {
    public Song atSecond(int second){
        int sum=0;
        if(second < 0){
            throw new IndexOutOfBoundsException("Time is too short: " + second);
        }
        for(Song song : this){
            sum += song.getDuration();
            if(sum >= second){
                return song;
            }
        }
        if(second > sum){
            throw new IndexOutOfBoundsException("Time is longer than the whole playlist duration: " + second);
        }
        return null;
    }
}
