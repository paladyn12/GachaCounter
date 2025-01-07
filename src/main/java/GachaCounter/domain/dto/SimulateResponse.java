package GachaCounter.domain.dto;

import GachaCounter.domain.Star;
import GachaCounter.domain.entity.Character;
import GachaCounter.domain.entity.LightCone;
import lombok.Data;

@Data
public class SimulateResponse {
    String name;
    String imagePath;
    Star star;

    public SimulateResponse(Character character) {
        this.name = character.getName();
        this.imagePath = character.getImagePath();
        this.star = character.getStar();
    }
    public SimulateResponse(LightCone lightCone) {
        this.name = lightCone.getName();
        this.imagePath = lightCone.getImagePath();
        this.star = lightCone.getStar();
    }
}
