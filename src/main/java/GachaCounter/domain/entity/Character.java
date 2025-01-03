package GachaCounter.domain.entity;

import GachaCounter.domain.Star;
import GachaCounter.domain.Type;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Character {

    @Id @GeneratedValue
    Long id;

    String name;
    Star star;
    Type type;
    private String imagePath;
}
