package GachaCounter.config;

import GachaCounter.handler.OAuth2LoginSuccessHandler;
import GachaCounter.service.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .and()
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasRole("ADMIN") // /admin/ 경로는 ADMIN 권한만 접근 가능
                        .anyRequest().permitAll()) // 나머지 요청은 모두 허용
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(principalOauth2UserService))
                        .successHandler(oAuth2LoginSuccessHandler))
                .formLogin().disable() // 필요에 따라 폼 로그인 설정
                .logout().permitAll();
        return http.build();
    }
}
