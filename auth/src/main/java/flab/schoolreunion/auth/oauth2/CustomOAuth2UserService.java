package flab.schoolreunion.auth.oauth2;

import flab.schoolreunion.auth.entity.Member;
import flab.schoolreunion.auth.entity.Role;
import flab.schoolreunion.auth.repository.MemberRepository;
import flab.schoolreunion.auth.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;

    public CustomOAuth2UserService(MemberRepository memberRepository,
                                   RoleRepository roleRepository) {
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

        OAuth2User oauth2User = oAuth2UserService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        log.info("registrationId : " + registrationId);
        log.info("userNameAttributeName : " + userNameAttributeName);

        OAuth2Attributes oAuth2Attribute =
                OAuth2Attributes.of(registrationId, userNameAttributeName, oauth2User.getAttributes());

        var user = oAuth2Attribute.convertToMap();
        log.info("user : " + user);

        Member member;
        try {
            member = memberRepository.findMemberByEmail(user.get("email").toString()).orElseThrow();

        } catch (NoSuchElementException e) {
            Role role = roleRepository.findRoleByRoleName("ROLE_USER").orElseThrow();
            List<Role> roles = new ArrayList<>();
            roles.add(role);

            member = Member.builder()
                    .name(user.get("name").toString())
                    .email(user.get("email").toString())
                    .roles(roles)
                    .build();

            memberRepository.save(member);
        }

        return new DefaultOAuth2User(
                Set.copyOf(member.getRoles().stream().map(role ->
                        new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toSet()))
                , oAuth2Attribute.getAttributes()
                , "email"
        );
    }
}
