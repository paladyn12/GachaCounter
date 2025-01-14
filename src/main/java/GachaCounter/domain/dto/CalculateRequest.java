package GachaCounter.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class CalculateRequest {
    private int sungok;
    private int ticket;
    private List<Target> targets;
    private int characterCount;
    private boolean characterIsFull;
    private int lightConeCount;
    private boolean lightConeIsFull;


    public int getCharacters() {
        int sum = 0;
        for (Target target : targets) {
            sum += target.characterCount();
        }
        return sum;
    }
    public int getLightCones() {
        int sum = 0;
        for (Target target : targets) {
            sum += target.lightConeCount();
        }
        return sum;
    }

    public static class Target {
        private String character;
        private String lightCone;

        public int characterCount() {
            return switch (character) {
                case "X" -> 0;
                case "명함" -> 1;
                case "1돌" -> 2;
                case "2돌" -> 3;
                case "3돌" -> 4;
                case "4돌" -> 5;
                case "5돌" -> 6;
                case "6돌" -> 7;
                default -> 0;
            };
        }
        public int lightConeCount() {
            return switch (lightCone) {
                case "X" -> 0;
                case "명함" -> 1;
                case "1재" -> 2;
                case "2재" -> 3;
                case "3재" -> 4;
                case "4재" -> 5;
                case "5재" -> 6;
                default -> 0;
            };
        }

        // Getters and Setters
        public String getCharacter() {
            return character;
        }

        public void setCharacter(String character) {
            this.character = character;
        }

        public String getLightCone() {
            return lightCone;
        }

        public void setLightCone(String lightCone) {
            this.lightCone = lightCone;
        }
    }
}
