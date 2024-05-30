package music;

public class NotEnoughCreditsException extends Exception{

    public NotEnoughCreditsException(String message) {
        super("Not enough credits: " + message);
    }
}
