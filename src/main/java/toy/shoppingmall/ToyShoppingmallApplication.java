package toy.shoppingmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ToyShoppingmallApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToyShoppingmallApplication.class, args);
    }

}
