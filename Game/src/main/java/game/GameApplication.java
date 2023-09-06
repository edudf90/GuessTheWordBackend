package game;

import game.characterComparison.CharacterComparator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GameApplication {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public CharacterComparator getWebClientBuilder(){
        return new CharacterComparator();
    }

    public static void main(String[] args){
        SpringApplication.run(GameApplication.class);
    }
}
