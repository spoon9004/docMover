package com.doc.mover.service;

import com.doc.mover.config.DocLoaderProperties;
import java.time.Duration;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class DocSender {

    private final WebClient webClient;
    private final String url;
    private final String authorizationHeaderValue;

    public DocSender(DocLoaderProperties props) {
        this.url = props.getUrl();
        this.authorizationHeaderValue = props.getAuthorizationHeaderValue();

        this.webClient = WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(cfg -> cfg.defaultCodecs().maxInMemorySize(4 * 1024 * 1024))
                        .build())
                .build();
    }

    public void sendBatch(List<String> ids) {
        WebClient.RequestBodySpec req = webClient.post()
                .uri(url)
                .header(HttpHeaders.ACCEPT, MediaType.ALL_VALUE)
                .contentType(MediaType.APPLICATION_JSON);

        if (authorizationHeaderValue != null && !authorizationHeaderValue.isBlank()) {
            req.header(HttpHeaders.AUTHORIZATION, authorizationHeaderValue);
        }

        String[] body = ids.toArray(new String[0]);

        String responseBody = req
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(ex -> Mono.error(new RuntimeException("HTTP error: " + ex.getMessage(), ex)))
                .block(Duration.ofSeconds(60));

        System.out.println("OK: sent " + ids.size() + " ids; response: " + responseBody);
    }
}