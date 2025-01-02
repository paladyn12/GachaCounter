package GachaCounter.handler;

import GachaCounter.config.JWTProvider;
import GachaCounter.config.PrincipalDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // OAuth2 로그인이 성공했을 때의 추가 작업
        // 여기에서는 JWT 토큰을 발급하고 형식에 맞게 return
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        String token = jwtProvider.createToken(principalDetails.getUser());

        // 응답 헤더에 JWT 토큰 추가
        response.addHeader("Authorization", "Bearer " + token);

        // JWT 토큰을 response에 담아서 전송
        response.setContentType("application/json");
        response.sendRedirect("/");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
        System.out.println("TOKEN : "+token);
    }
}
