package GachaCounter.service;

import GachaCounter.config.PrincipalDetails;
import GachaCounter.domain.entity.User;
import GachaCounter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

//        System.out.println("getClientRegistration: "+userRequest.getClientRegistration());
//        System.out.println("getAccessToken: "+userRequest.getAccessToken());
//        System.out.println("getAttributes: "+super.loadUser(userRequest).getAttributes());

        OAuth2User oauth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration()
                .getRegistrationId(); //google kakao facebook...
        String provideId = oauth2User.getAttribute("sub");
        String email = oauth2User.getAttribute("email");
        String role = checkAdmin(email) ? "ROLE_ADMIN" : "ROLE_USER";
        String username = oauth2User.getAttribute("name");
        String password = "OAuth2"; //Oauth2로 로그인을 해서 패스워드는 의미없음.

        Optional<User> user = userRepository.findByEmail(email);


        //이미 소셜로그인을 한적이 있는지 없는지
        if (user.isEmpty()) {
            User newUser = User.builder()
                    .email(email)
                    .username(username)
                    .password(password)
                    .role(role)
                    .characterCount(0)
                    .lightConeCount(0)
                    .characterIsFull(false)
                    .lightConeIsFull(false)
                    .authority(role)
                    .provider(provider)
                    .build();

            userRepository.save(newUser);
            return new PrincipalDetails(newUser, oauth2User.getAttributes());
        } else {
            return new PrincipalDetails(user.get(), oauth2User.getAttributes());
        }
    }
    private boolean checkAdmin(String email) {
        return email.equals("paladyn65@gmail.com");
    }
}