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
@Getter
@Setter
public class Pickup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private Date startDate;
    private Date endDate;

    @OneToMany
    private List<Character> pickupCharacters = new ArrayList<>();

    @OneToMany
    private List<LightCone> pickupLightCones = new ArrayList<>();
}
