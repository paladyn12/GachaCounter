package GachaCounter.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String username;
    private String password; // OAuth2로 로그인 구현하여 의미 없음

    private String role;

    private int characterCount;
    private int lightConeCount;
    private boolean characterIsFull;
    private boolean lightConeIsFull;

    private String authority;
    private String provider;

    public void updateCounter(int characterCount, int lightConeCount, boolean characterIsFull, boolean lightConeIsFull) {
        this.characterCount = characterCount;
        this.lightConeCount = lightConeCount;
        this.characterIsFull = characterIsFull;
        this.lightConeIsFull = lightConeIsFull;
    }
}
