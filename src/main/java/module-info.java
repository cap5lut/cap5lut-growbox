module net.cap5lut.growbox {
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.module.afterburner;
    requires com.zaxxer.hikari;
    requires io.javalin;
    requires java.sql;
    requires kotlin.stdlib;
    requires net.cap5lut.database;
    requires org.apache.logging.log4j;
    requires org.postgresql.jdbc;
}