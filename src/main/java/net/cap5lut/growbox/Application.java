package net.cap5lut.growbox;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.vue.JavalinVue;
import net.cap5lut.growbox.dev.DevelopmentController;
import net.cap5lut.growbox.device.data.DeviceDataController;
import net.cap5lut.growbox.device.data.PutDeviceData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.CompletionException;

import static java.util.Objects.requireNonNull;
import static java.util.concurrent.CompletableFuture.runAsync;

public final class Application {

    public static void main(String[] args) {
        JavalinVue.vueVersion(config -> config.vue3("app.vue"));
        new Application(args);
    }

    public static String sql(String path) {
        try {
            return new String(
                    requireNonNull(Application.class.getResourceAsStream("/sql/" + path + ".sql")).readAllBytes(),
                    StandardCharsets.UTF_8
            );
        } catch (IOException error) {
            throw new AssertionError(error);
        }
    }

    public final Logger logger = LogManager.getLogger();
    public final ShutdownManager shutdownManager = new ShutdownManager();
    public final DataSource dataSource;
    public final Javalin webserver;

    public Application(String... args) {
        logger.atInfo().log("Starting");
        logger.atDebug().log("Initializing database");
        {
            final var pgDataSource = new PGSimpleDataSource();
            pgDataSource.setURL("jdbc:postgresql://localhost:5432/?user=cap5lut-growbox&password=cap5lut-growbox");

            final var config = new HikariConfig();
            config.setDataSource(pgDataSource);

            dataSource = new HikariDataSource(config);

            try (final var connection = dataSource.getConnection()) {
                connection.setAutoCommit(true);
                try (final var statement = connection.createStatement()) {
                    statement.execute(sql("initialize"));
                }
            } catch (SQLException error) {
                shutdownManager.shutdown(logger,"Could not initialize database", error);
            }
        }

        logger.atDebug().log("Starting webserver");
        webserver = Javalin
                .create(config -> {
                    config.addStaticFiles("/static", Location.CLASSPATH);
                    config.enableWebjars();
                    config.showJavalinBanner = false;
                });
        new DevelopmentController(this);
        new DeviceDataController(this);

        webserver.start(8080);
        logger.atInfo().log("Running");
    }
}
