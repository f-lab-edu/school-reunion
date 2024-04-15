package flab.schoolreunion.apigateway.filter;

import flab.schoolreunion.apigateway.TokenProvider;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@Component
public class JwtAuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final int TOKEN_PREFIX_LENGTH = 7;
    private final TokenProvider tokenProvider;

    public JwtAuthenticationGatewayFilterFactory(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String authorizationHeader = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);

            if(StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(TOKEN_PREFIX)){
                String token = authorizationHeader.substring(TOKEN_PREFIX_LENGTH);

                try {
                    if (tokenProvider.isValidToken(token)) {
                        return chain.filter(exchange);
                    }
                }catch (Exception e){
                    log.info("token validation error: " + e.getMessage());
                }
            }
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        };
    }
}
