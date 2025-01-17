package GachaCounter.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Table(name = "pickups")
@Getter
@Setter
public class Pickup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private Date startDate;
    private Date endDate;

    @ManyToMany
    @JoinTable(
            name = "pickup_pickup_characters", // 중간 테이블 이름 명시
            joinColumns = @JoinColumn(name = "pickup_id"), // Pickup 엔티티의 ID 컬럼
            inverseJoinColumns = @JoinColumn(name = "character_id") // Character 엔티티의 ID 컬럼
    )
    private List<Character> pickupCharacters = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "pickup_pickup_light_cones", // 중간 테이블 이름 명시
            joinColumns = @JoinColumn(name = "pickup_id"), // Pickup 엔티티의 ID 컬럼
            inverseJoinColumns = @JoinColumn(name = "light_cone_id") // LightCone 엔티티의 ID 컬럼
    )
    private List<LightCone> pickupLightCones = new ArrayList<>();
}
