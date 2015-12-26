package om.ceitechs.duperaser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
public class DuplicatesEraserApplication {

    public static void main(String[] args) {
        SpringApplication.run(DuplicatesEraserApplication.class, args);
    }
}
