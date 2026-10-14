package com.hojui.AgentIo.config;
// Should be a Spring Security configuration class

import com.hojui.AgentIo.controller.UserAuthController;



import java.beans.BeanProperty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

@Configuration 
public class SecurityConfig {

    private final UserAuthController userAuthController;

    SecurityConfig(UserAuthController userAuthController) {
        this.userAuthController = userAuthController;
    }

    // 혹시라도 비밀번호 DB가 유출되었을때 복원할수 없도록 암호화하는 방법
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // role hierachy registry 사용자들의 권한 순위 설정하는 부분 
    @Bean 
    public RoleHierarchy roleHierarchy(){
        return RoleHierarchyImpl.withRolePrefix("ROLE_")
                .role("ADMIN").implies("USER")  // ADMIN is superior than USER
                .role("USER").implies(("STRANGER"))
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config){
        return config.getAuthenticationManager(); // this will give me an object of auth 
    }

    // access()에 넣을 커스텀 인가 규칙. hasRole("ADMIN")과 같은 동작
    private AuthorizationManager<RequestAuthorizationContext> customAuthorizationManager(){
        return (authentication, context) -> {

            boolean allowed =
                    authentication.get().getAuthorities().stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            return new AuthorizationDecision(allowed);
        };
    }





    // 시큐리티 필터 체인 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
        @Value("${security.remember-me.key}") String rememberMeKey)
     {

        http
            .csrf(csrf -> csrf.disable());
            // .csrf(Customizer.withDefaults()); //now csrf tokens come from back to frontend. Then when client requests, it must have _csrf token attached to be successful
            // .csrf(csrf -> csrf.ignoringRequestMatchers("/logout")); // logout doesnt really need to be protected 
        
        // 이 formLogin이 자동으로 유저 가입시 Post해서 결과 주는것도 생성하기 때문에 PostMapping을 로긴페이지에 생성할 필요없고, 요청만 POST로 하면 된다. 
        http 
            .formLogin(login -> login // Login form filter provided by Spring Security
                .loginProcessingUrl("/login")  
                .loginPage("/login") // Custom login page URL
                .defaultSuccessUrl("/")          // 생략해도 기본값이 "/"
                .failureUrl("/login?error"));    // 생략해도 기본값이 "/login?error"



        // 권한이나 아이디에 따라 인가/허용 필터 설정
        // http
            // .authorizeHttpRequests(auth -> auth
            // .requestMatchers("/").permitAll() // "Everyone has an access to the base URL '/' " 
            // .requestMatchers("/signup").permitAll()
            // .requestMatchers("/login").permitAll()
            // .requestMatchers("/user").hasAnyRole("USER", "ADMIN") // Anyone from USER and ADMIN can
            // .requestMatchers("/admin").hasRole("ADMIN")  // Only ADMIN can (아래 access와 중복되면 먼저 매칭된 이 줄만 적용됨)
            // .requestMatchers("/admin").access(customAuthorizationManager())  // custom method that makes something like hasRole can be done by access and access needs AuthorizationManager<RequestAuthorizationContext> down below. This is identical with .requestMatchers("/admin").hasRole("ADMIN")
            // .anyRequest().denyAll()  // Other than the above pages, all deny
        // );

        // rememberMe settting
        http
            .rememberMe(me -> me
                .key(rememberMeKey)
                .rememberMeParameter("remember-me") //특정 위치에서 rememberMe 활성화를 시킬거기 때문에, 그 위치를 인자로 적어줌 
                .tokenValiditySeconds(14 * 24 * 60 * 60)); // 2weeks
        
        // session related settings (default is state-remembered). This will make the service not remember user in memory even after logging in. So JWT should be involved.
        // http
        //     .sessionManagement(session -> session
        //         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); 
        
        // 세션 고정 공격 방지하기 위해 로그아웃할때 JsessionID를 바꿔주는 방법 
        http
            .sessionManagement(session -> session
                .sessionFixation().changeSessionId()
            );


        // final build
        return http.build();
        
    }


}
