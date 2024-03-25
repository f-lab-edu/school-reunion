package flab.schoolreunion.auth.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "refreshToken")
@Builder
public class RefreshToken {

    @Id
    private String refreshToken;
    private String email;

    public void update(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
