package net.cap5lut.growbox;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import net.cap5lut.database.Database;
import net.cap5lut.growbox.device.DeviceApiController;
import net.cap5lut.growbox.device.DeviceManager;
import net.cap5lut.growbox.device.data.DeviceData;
import net.cap5lut.growbox.device.data.DeviceDataApiController;
import net.cap5lut.growbox.device.data.DeviceDataManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.ds.PGSimpleDataSource;

import java.util.Scanner;

import static java.util.Objects.requireNonNull;
import static net.cap5lut.growbox.Utils.sql;
import static net.cap5lut.growbox.view.html.DSL.$tr;

public class Application {
    private static final Logger logger = LogManager.getLogger();

    private static final Reference<Database> database = new Reference<>();
    private static final Reference<Javalin> webserver = new Reference<>();
    private static final Reference<DeviceManager> deviceManager = new Reference<>();
    private static final Reference<DeviceDataManager> deviceDataManager = new Reference<>();

    public static void main(String[] args) {
        logger.info("cap5lut-growbox");
        initializeDatabase();
        initializingManagers();
        initializeWebserver();
        webserver.get().start(8080);
        logger.info("running");
    }

    private static void initializeDatabase() {
        logger.info("initializing database");
        final var psqlDs = new PGSimpleDataSource();
        psqlDs.setUrl("jdbc:postgresql://localhost:5433/growbox");
        psqlDs.setUser("growbox");
        psqlDs.setPassword("growbox");

        final var config = new HikariConfig();
        config.setDataSource(psqlDs);

        final var stream = requireNonNull(Application.class.getResourceAsStream("/database.sql"));
        try (final var scanner = new Scanner(stream)) {
            database
                    .set(Database.of(new HikariDataSource(config)))
                    .create(sql("/initialize"))
                    .join();
        }
    }

    private static void initializingManagers() {
        logger.info("initializing managers");
        deviceManager.set(new DeviceManager(database.get()));
        deviceDataManager.set(new DeviceDataManager(database.get()));
    }

    private static void initializeWebserver() {
        logger.info("initializing webserver");
        webserver.set(Javalin.create(config -> {
            config.addStaticFiles("/static", Location.CLASSPATH);
            config.enableWebjars();
        }));
        new WebController(deviceManager.get(), webserver.get());
        new DeviceApiController(deviceManager.get(), webserver.get());
        new DeviceDataApiController(deviceDataManager.get(), webserver.get());
    }
}
