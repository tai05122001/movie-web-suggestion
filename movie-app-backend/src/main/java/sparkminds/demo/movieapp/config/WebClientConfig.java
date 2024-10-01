package sparkminds.demo.movieapp.config;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import static sparkminds.demo.movieapp.constant.UrlConstant.URL_API_CALCULATE_SIMILARITY;
import static sparkminds.demo.movieapp.constant.UrlConstant.URL_API_CALCULATE_SIMILARITY_LOCAL;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(URL_API_CALCULATE_SIMILARITY)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .resolver(DefaultAddressResolverGroup.INSTANCE)))
                .build();
    }

    @Bean
    public WebClient webClientLocal() {
        return WebClient.builder()
                .baseUrl(URL_API_CALCULATE_SIMILARITY_LOCAL)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .resolver(DefaultAddressResolverGroup.INSTANCE)))
                .build();
    }
}
