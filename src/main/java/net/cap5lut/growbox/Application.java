package net.cap5lut.growbox;

import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.json.JavalinJackson;
import io.javalin.plugin.rendering.vue.JavalinVue;
import net.cap5lut.sql.Database;
import net.cap5lut.growbox.controller.DefaultController;
import net.cap5lut.growbox.controller.DevelopmentController;
import net.cap5lut.growbox.controller.DeviceControllerV1;
import net.cap5lut.growbox.manager.DeviceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ForkJoinPool;

import static java.util.Objects.requireNonNull;

public class Application {
    private static final Logger logger = LogManager.getLogger();

    public static final String ENV_VAR_URL = "net.cap5lut.growbox.database.url";

    public static void main(String[] args) {
        JavalinVue.vueVersion(config -> config.vue3("app.vue"));
        JavalinJackson.Companion
                .defaultMapper()
                .registerModule(new AfterburnerModule());

        new Application();
    }

    public static String sql(String path) {
        try {
            return new String(
                    requireNonNull(Application.class.getResourceAsStream("/sql/" + path + ".sql")).readAllBytes(),
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private final Broadcaster broadcaster;
    private final Database database;
    private final DeviceManager deviceManager;
    private final Javalin webserver;

    public Application() {
        {
            logger.info("Initializing database");
            final var postgres = new PGSimpleDataSource();
            postgres.setUrl(System.getenv(ENV_VAR_URL));

            final var config = new HikariConfig();
            config.setPoolName("growbox-connection-pool");
            config.setDataSource(postgres);

            database = Database.of(new HikariDataSource(config), ForkJoinPool.commonPool());
            database.create(sql("initialize")).join();
        }
        {
            logger.info("Initializing managers");
            broadcaster = new Broadcaster();
            deviceManager = new DeviceManager(database);
        }
        {
            logger.info("Initializing webserver");
            webserver = Javalin.create(config -> {
                config.addStaticFiles("/static", Location.CLASSPATH);
                config.enableWebjars();
            });
            broadcaster.register(this);
            new DefaultController(this);
            new DevelopmentController(this);
            new DeviceControllerV1(this);
            webserver.start(8080);
        }
    }

    public Broadcaster getBroadcaster() {
        return broadcaster;
    }

    public Database getDatabase() {
        return database;
    }

    public DeviceManager getDeviceManager() {
        return deviceManager;
    }

    public Javalin getWebserver() {
        return webserver;
    }
}
