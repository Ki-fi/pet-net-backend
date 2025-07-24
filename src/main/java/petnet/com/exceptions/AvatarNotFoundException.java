package petnet.com.exceptions;

public class AvatarNotFoundException extends RuntimeException {
    public AvatarNotFoundException() {
        super("Avatar niet gevonden");
    }
}
