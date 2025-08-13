package petnet.com.exceptions;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
        super("Deze post bestaat niet.");
    }
}
