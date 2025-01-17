package GachaCounter.domain.entity;

import GachaCounter.domain.Element;
import GachaCounter.domain.Path;
import GachaCounter.domain.Special;
import GachaCounter.domain.Star;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "characters")
@Getter
public class Character {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String name;
    @Enumerated(EnumType.STRING)
    Star star;
    @Enumerated(EnumType.STRING)
    Path path;
    @Enumerated(EnumType.STRING)
    Element element;
    @Enumerated(EnumType.STRING)
    Special special;
    @Column(name = "image_path")
    private String imagePath;

}
