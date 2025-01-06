package GachaCounter.domain.dto;

import lombok.Data;

@Data
public class CharacterSimulateRequest {
    private int characterCount;
    private boolean characterIsFull;
    private String imageName;
}
