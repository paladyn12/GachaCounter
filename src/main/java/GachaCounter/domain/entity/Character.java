package GachaCounter.domain.entity;

import GachaCounter.domain.Element;
import GachaCounter.domain.Path;
import GachaCounter.domain.Special;
import GachaCounter.domain.Star;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Character {

    @Id @GeneratedValue
    Long id;

    String name;
    Star star;
    Path path;
    Element element;
    Special special;
    private String imagePath;
}
