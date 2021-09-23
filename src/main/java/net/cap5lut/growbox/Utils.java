package net.cap5lut.growbox;

import com.zaxxer.hikari.HikariDataSource;
import net.cap5lut.database.Database;

import java.util.Scanner;

import static java.util.Objects.requireNonNull;

public class Utils {
    public static String sql(String path) {
        final var stream = Application.class.getResourceAsStream("/sql" + path + ".sql");
        if (stream == null) {
            throw new RuntimeException("Statement not found: " + path);
        }

        try (final var scanner = new Scanner(stream)) {
            return scanner.useDelimiter("\\A").next();
        }
    }

    private Utils() {
        throw new UnsupportedOperationException();
    }
}
