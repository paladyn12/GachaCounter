package GachaCounter.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrackerResponse {
    String imagePath;
    int count;
}
