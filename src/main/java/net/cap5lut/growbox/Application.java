package net.cap5lut.growbox;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.vue.JavalinVue;
import net.cap5lut.database.Database;
import net.cap5lut.growbox.device.DeviceApiController;
import net.cap5lut.growbox.device.DeviceManager;
import net.cap5lut.growbox.device.data.DeviceDataApiController;
import net.cap5lut.growbox.device.data.DeviceDataManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.ds.PGSimpleDataSource;

import java.util.HashMap;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;
import static net.cap5lut.growbox.Utils.sql;
import static net.cap5lut.growbox.view.html.DSL.$tr;

public class Application {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("cap5lut-growbox");
        JavalinVue.vueVersion(config -> config.vue3("app"));

        new Application().webserver.start(8080);
        logger.info("running");
    }

    public final Database database;
    public final DeviceManager deviceManager;
    public final DeviceDataManager deviceDataManager;
    public final Javalin webserver;

    public Application() {
        logger.info("initializing database");
        {
            final var dataSource = new PGSimpleDataSource();
            dataSource.setUrl("jdbc:postgresql://localhost:5433/growbox?user=growbox&password=growbox");
            final var config = new HikariConfig();
            config.setDataSource(dataSource);
            database = Database.of(new HikariDataSource(config));
            database
                    .create(sql("/initialize"))
                    .join();
        }

        logger.info("initializing managers");
        {
            deviceManager = new DeviceManager(database);
            deviceDataManager = new DeviceDataManager(database);
        }


        logger.info("initializing webserver");
        {
            webserver = Javalin.create(config -> {
                config.addStaticFiles("/static", Location.CLASSPATH);
                config.enableWebjars();
            });
            new WebController(this);
            new DeviceApiController(this);
            new DeviceDataApiController(this);
        }
    }
}
