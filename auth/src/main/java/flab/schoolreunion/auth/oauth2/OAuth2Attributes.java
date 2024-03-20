package flab.schoolreunion.auth.oauth2;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
public class OAuth2Attributes {
    private Map<String, Object> attributes;
    private String attributeKey;
    private String email;
    private String name;

    public static OAuth2Attributes of(String provider, String attributeKey, Map<String, Object> attributes) {
        if (provider.equals("google")) {
            return ofGoogle(attributeKey, attributes);
        }
        throw new IllegalArgumentException("Unknown provider: " + provider);
    }

    private static OAuth2Attributes ofGoogle(String attributeKey, Map<String, Object> attributes) {

        return OAuth2Attributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();
    }

    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("attributeKey", attributeKey);
        map.put("name", name);
        map.put("email", email);

        return map;
    }
}
