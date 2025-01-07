package GachaCounter.domain.dto;

import lombok.Data;

@Data
public class LightConeSimulateRequest {
    private int lightConeCount;
    private boolean lightConeIsFull;
    private String imageName;
}
