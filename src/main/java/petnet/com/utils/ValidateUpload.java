package petnet.com.utils;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ValidateUpload {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final List<String> ALLOWED_CONTENT_TYPES = List.of(
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/svg+xml",
            "image/webp"
    );

    public static void validateAvatar(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Geen bestand geselecteerd.");
        }

        if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException("Bestandstype niet toegestaan: " + file.getContentType());
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException(new FileSizeLimitExceededException(
                    "Bestand is te groot",
                    file.getSize(),
                    MAX_FILE_SIZE
            ));
        }
    }
}