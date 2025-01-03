package GachaCounter.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CounterRequest {

    @Min(0)
    @Max(89)
    private int characterCount;
    @Min(0)
    @Max(79)
    private int lightConeCount;
    private boolean characterIsFull;
    private boolean lightConeIsFull;
}
