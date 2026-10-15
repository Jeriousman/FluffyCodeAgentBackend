package com.hojui.AgentIo.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.hojui.AgentIo.model.UserEntity;
import com.hojui.AgentIo.model.UserRole;
import com.hojui.AgentIo.repository.UserRepository;

@Service 
public class Oauth2UserService extends DefaultOAuth2UserService{

    private final UserRepository userRepository;

    Oauth2UserService (UserRepository userRepository){
        this.userRepository = userRepository;
    }



    @Override 
    public OAuth2User loadUser(OAuth2UserRequest userRequest){
        
        // 1. 부모(DefaultOAuth2UserService)가 GitHub/Google에서 사용자 정보를 가져옴
        OAuth2User oauth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oauth2User.getAttributes();

        // 2. 어느 제공자로 로그인했는지 ("github" 또는 "google") - application.properties의 registration 이름
        String provider = userRequest.getClientRegistration().getRegistrationId();

        // 3. 제공자마다 키 이름이 달라서 나눠서 꺼냄
        String providerId;
        String email = (String) attributes.get("email"); // GitHub는 비공개면 null
        if (provider.equals("github")) {
            providerId = String.valueOf(attributes.get("id")); // GitHub id는 숫자라서 문자열로 변환
        } else { // google
            providerId = (String) attributes.get("sub");
        }

        // 4. DB에서 찾고, 없으면(처음 로그인) 새로 저장
        UserEntity user = userRepository.findByProviderAndProviderId(provider, providerId)
                .orElseGet(() -> { //DB에 맞는 아이디가 없으면 지금들어온 정보로 등록 
                    UserEntity newUser = new UserEntity();
                    newUser.setProvider(provider);
                    newUser.setProviderId(providerId);
                    newUser.setUsername(provider + "_" + providerId); // 일반 가입 username과 겹치지 않게
                    newUser.setEmail(email);
                    newUser.setRole(UserRole.USER);                   // 기본 권한
                    return userRepository.save(newUser);
                });

        // 5. 우리 DB의 role로 권한을 붙여서 반환 → hasRole("USER") 같은 규칙을 통과할 수 있게 됨
        String nameAttributeKey = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); // github: "id", google: "sub"

        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())), // List.of("a", "b", "c")     // ["a", "b", "c"]      .       SimpleGrantedAuthority -> 
                attributes,
                nameAttributeKey);



    }

}
