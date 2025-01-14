package GachaCounter.service;

import GachaCounter.domain.dto.CalculateRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CalculatorService {

    public int calculate(CalculateRequest request) {
        // 캐릭터와 광추의 뽑는 개수와 스택 여부에 대한 값을 각각 더해 return
        int result = 0;
        if (request.getCharacters() != 0)
            result += calculateCharacter(request);
        if (request.getLightCones() != 0)
            result += calculateLightCone(request);
        return result;
    }

    private int calculateCharacter(CalculateRequest request) {
        // 스택, 천장이 모두 없을 때 기대값은 한 캐릭터당 94뽑
        if (!request.isCharacterIsFull() && request.getCharacterCount() == 0) {
            return request.getCharacters() * 94;
        } else {
            // 스택, 천장이 있을 때 한 캐릭터는 기대값이 달라짐, 나머지 캐릭터는 94뽑
            int sum = 0;
            sum += (request.getCharacters()-1) * 94;
            sum += stackedCharacter(request.getCharacterCount(), request.isCharacterIsFull());
            return sum;
        }
    }



    private int calculateLightCone(CalculateRequest request) {
        // 스택, 천장이 모두 없을 때 기대값은 한 광추당 72뽑
        if (!request.isLightConeIsFull() && request.getLightConeCount() == 0) {
            return request.getLightCones() * 72;
        } else {
            // 스택, 천장이 있을 때 한 캐릭터는 기대값이 달라짐, 나머지 캐릭터는 72뽑
            int sum = 0;
            sum += (request.getLightCones()-1) * 72;
            sum += stackedLightCone(request.getLightConeCount(), request.isLightConeIsFull());
            return sum;
        }
    }
    private int stackedCharacter(int characterCount, boolean characterIsFull) {
        int test_case = 100000;
        Random random = new Random();
        double sum = 0.0;
        while (test_case-- > 0) {
            int stack = characterCount; // 스택
            boolean isFull = characterIsFull;
            int count = 1; // 시도 횟수
            while (true) {
                double randomDouble = random.nextDouble();
                double percentage = adjstPrbbl_Character(stack) + 0.006;
                if (randomDouble > percentage) {
                    stack++;
                    count++;
                } else {
                    if (isFull) {
                        break;
                    }
                    else {
                        randomDouble = random.nextDouble();
                        if (randomDouble < 0.5) {
                            break;
                        } else {
                            isFull = true;
                            stack = 0;
                        }
                    }
                }

            }
            System.out.println(count);
            sum += count;
        }
        return (int) Math.round(sum/100000);
    }

    private int stackedLightCone(int lightConeCount, boolean lightConeIsFull) {
        int test_case = 100000;
        Random random = new Random();
        double sum = 0.0;
        while (test_case-- > 0) {
            int stack = lightConeCount; // 스택
            boolean isFull = lightConeIsFull;
            int count = 1; // 시도 횟수
            while (true) {
                double randomDouble = random.nextDouble();
                double percentage = adjstPrbbl_LightCone(stack) + 0.006;
                if (randomDouble > percentage) {
                    stack++;
                    count++;
                } else {
                    if (isFull) {
                        break;
                    }
                    else {
                        randomDouble = random.nextDouble();
                        if (randomDouble < 0.75) {
                            break;
                        } else {
                            isFull = true;
                            stack = 0;
                        }
                    }
                }

            }
            System.out.println(count);
            sum += count;
        }
        return (int) Math.round(sum/100000);
    }

    private double adjstPrbbl_Character(int count) {
        if (count <= 73) return 0.0;
        else return Math.min((count - 73) * 0.06, 1.0); // 6%씩 증가, 최대 100% 제한
    }

    private double adjstPrbbl_LightCone(int count) {
        if (count <= 65) return 0.0;
        else return Math.min((count - 65) * 0.07, 1.0); // 7%씩 증가, 최대 100% 제한
    }

}
