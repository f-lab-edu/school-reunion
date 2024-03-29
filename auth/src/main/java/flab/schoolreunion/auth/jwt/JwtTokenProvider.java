package flab.schoolreunion.auth.jwt;

import flab.schoolreunion.auth.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {
    private final RefreshTokenRepository refreshTokenRepository;
    private static final String AUTHORITIES_KEY = "auth";
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    private final long tokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;
    private final String secret;
    private Key key;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds,
            @Value("${jwt.refresh-token-validity-in-days}") long refreshTokenValidityInDays,
            RefreshTokenRepository refreshTokenRepository) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInDays * 1000 * 60 * 60 * 24;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        long now = new Date().getTime();
        Date validity = new Date(now + tokenValidityInMilliseconds);

        return getToken(authentication, validity);
    }

    public String createRefreshToken(Authentication authentication) {
        long now = new Date().getTime();
        Date validity = new Date(now + refreshTokenValidityInMilliseconds);

        return getToken(authentication, validity);
    }

    private String getToken(Authentication authentication, Date validity) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public TokenValidState validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return TokenValidState.VALIDATED;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            return TokenValidState.INVALID;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            return TokenValidState.EXPIRED;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            return TokenValidState.INVALID;
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            return TokenValidState.INVALID;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.
                parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public ResponseEntity<?> tokenRefresh(HttpServletRequest request) {
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(REFRESH_TOKEN_COOKIE_NAME)) {
                    refreshToken = c.getValue();
                }
            }
        }
        if (refreshToken != null && refreshTokenRepository.findById(refreshToken).isPresent()) {
            Authentication authentication = getAuthentication(refreshToken);
            String accessToken = createToken(authentication);
            refreshToken = createRefreshToken(authentication);

            ResponseCookie responseCookie =
                    ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
                            .httpOnly(true)
//                            .secure(true)
                            .path("/")
                            .maxAge(60)
                            .build();

            return ResponseEntity.ok()
                    .header(AUTHORIZATION_HEADER, TOKEN_PREFIX + accessToken)
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .body(null);
        }
        return ResponseEntity.badRequest().body(null);
    }
}
