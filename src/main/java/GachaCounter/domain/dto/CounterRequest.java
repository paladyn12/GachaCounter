package GachaCounter.domain.dto;

import lombok.Data;

@Data
public class CounterRequest {
    private int characterCount;
    private int lightConeCount;
    private boolean characterIsFull;
    private boolean lightConeIsFull;
}
