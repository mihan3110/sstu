package sstu.lora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import sstu.lora.dao.DbConnection;

import java.sql.SQLException;

@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) throws SQLException {

       SpringApplication.run(Application.class, args);
    }

}
