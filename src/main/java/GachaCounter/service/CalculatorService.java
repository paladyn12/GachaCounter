package GachaCounter.service;

import GachaCounter.domain.dto.CalculateRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CalculatorService {

    public int calculate(CalculateRequest request) {
        // 캐릭터와 광추의 뽑는 개수와 스택 여부에 대한 값을 각각 더해 return
        double result = 0;
        if (request.getCharacters() != 0)
            result += calculateCharacter(request);
        if (request.getLightCones() != 0)
            result += calculateLightCone(request);
        return (int) result;
    }

    private double calculateCharacter(CalculateRequest request) {
        // 스택, 천장이 모두 없을 때
        if (!request.isCharacterIsFull() && request.getCharacterCount() == 0) {
            return request.getCharacters() * characterNotFullValues[0];
        } else {
            // 스택, 천장이 있을 때 한 캐릭터는 기대값이 달라짐, 나머진 스택 없을 때와 동일
            double sum = 0;
            sum += (request.getCharacters()-1) * characterNotFullValues[0];
            if (request.isCharacterIsFull()) {
                sum += characterFullValues[request.getCharacterCount()];
            } else {
                sum += characterNotFullValues[request.getCharacterCount()];
            }
            return sum;
        }
    }

    private double calculateLightCone(CalculateRequest request) {
        // 스택, 천장이 모두 없을 때
        if (!request.isLightConeIsFull() && request.getLightConeCount() == 0) {
            return request.getLightCones() * lightConeNotFullValues[0];
        } else {
            // 스택, 천장이 있을 때 한 광추는 기대값이 달라짐, 나머진 스택 없을 때와 동일
            double sum = 0;
            sum += (request.getLightCones()-1) * lightConeNotFullValues[0];
            if (request.isLightConeIsFull()) {
                sum += lightConeFullValues[request.getLightConeCount()];
            } else {
                sum += lightConeNotFullValues[request.getLightConeCount()];
            }
            return sum;
        }
    }

    private double[] characterNotFullValues = new double[]{
            89.53896, 88.71571, 88.07831, 87.59873, 87.2813, 86.59623, 85.7747, 85.018,
            84.52335, 83.84636, 82.96937, 82.47836, 81.73859, 80.81214, 80.40638, 79.29663,
            78.90305, 78.19584, 77.44126, 76.87919, 76.14495, 75.44806, 75.11161, 74.09774,
            73.38388, 72.43106, 71.8182, 71.27769, 70.43803, 69.6362, 68.79003, 68.34003,
            67.33142, 66.4458, 65.74203, 65.27159, 64.20846, 63.40458, 62.78159, 61.98328,
            61.19466, 60.51136, 59.46761, 58.66139, 58.06229, 57.14885, 56.21074, 55.45128,
            54.75416, 53.9176, 52.91882, 52.03559, 51.16384, 50.2646, 49.35297, 48.63693,
            47.66202, 46.60468, 45.90212, 44.93238, 44.2089, 43.12739, 42.2837, 41.53772,
            40.50183, 39.51302, 38.71814, 37.84785, 36.56992, 35.73914, 34.77533, 33.88795,
            32.94651, 32.14509, 31.32153, 30.80364, 30.16197, 30.0128, 29.57406, 29.31646,
            29.20907, 29.00851, 28.80963, 28.5809, 28.49825, 28.41888, 28.39056, 28.37141,
            28.23499, 28.11743
    };
    private double[] characterFullValues = new double[]{
            62.31601, 61.72863, 61.11557, 60.33369, 59.82694, 59.09455, 58.43988, 57.80469,
            57.1799, 56.44896, 55.76769, 55.19126, 54.70454, 53.81497, 53.07239, 52.368,
            51.79447, 51.09658, 50.29222, 49.6177, 48.94469, 48.23774, 47.54059, 46.71349,
            46.05566, 45.31319, 44.54934, 43.9131, 43.14405, 42.33845, 41.63683, 40.90459,
            40.1089, 39.31894, 38.62541, 37.78073, 37.04219, 36.26175, 35.43295, 34.71517,
            33.84467, 33.12367, 32.33994, 31.42511, 30.62478, 29.79702, 29.03132, 28.21087,
            27.38583, 26.49887, 25.66691, 24.79261, 23.93625, 23.0967, 22.21217, 21.36239,
            20.47147, 19.58653, 18.67307, 17.81752, 16.9095, 15.99858, 15.08873, 14.16559,
            13.2617, 12.32782, 11.40662, 10.47405, 9.5169, 8.57679, 7.61375, 6.65248,
            5.69616, 4.73061, 3.99601, 3.40976, 2.97592, 2.61278, 2.3166, 2.07839,
            1.87757, 1.71049, 1.56771, 1.44901, 1.34532, 1.24958, 1.17197, 1.09581,
            1.03377, 1.0
    };
    private double[] lightConeNotFullValues = {
            70.98977, 70.40707, 69.60123, 68.96588, 68.27099, 67.6519, 67.16492, 66.28107,
            65.64859, 64.97302, 64.26107, 63.53573, 62.87317, 62.0418, 61.2897, 60.5189,
            60.07222, 59.31264, 58.59312, 57.75934, 56.97158, 56.38862, 55.67235, 54.83526,
            53.83674, 53.20921, 52.43862, 51.53351, 50.85262, 50.07988, 49.26915, 48.57575,
            47.81303, 46.97643, 46.16662, 45.38684, 44.47682, 43.66666, 42.71971, 41.95059,
            41.17817, 40.28369, 39.44525, 38.50597, 37.83375, 36.80483, 36.10245, 35.07889,
            34.35121, 33.25985, 32.52537, 31.69695, 30.70706, 29.84846, 29.02026, 28.09123,
            27.16455, 26.02088, 25.347, 24.2954, 23.3474, 22.42009, 21.51029, 20.57286,
            19.52827, 18.60834, 17.6479, 17.05981, 16.81186, 16.31555, 16.2156, 16.03458,
            15.83058, 15.59244, 15.51063, 15.38824, 15.37494, 15.22968, 15.10133, 15.01758
    };
    private double[] lightConeFullValues = new double[]{
            57.01107, 56.2954, 55.50487, 54.89157, 54.22125, 53.60393, 52.82222, 52.25385,
            51.48059, 50.73001, 50.13369, 49.37434, 48.79377, 47.97111, 47.32342, 46.5646,
            45.75624, 45.16808, 44.2525, 43.58936, 42.92041, 42.07203, 41.34497, 40.60067,
            39.82772, 39.10948, 38.29364, 37.51224, 36.79768, 35.99502, 35.17389, 34.39342,
            33.55769, 32.81474, 31.99701, 31.20314, 30.34091, 29.49207, 28.67521, 27.84415,
            27.03741, 26.2063, 25.32795, 24.47032, 23.59987, 22.79676, 21.93314, 21.00934,
            20.14565, 19.27361, 18.36163, 17.46192, 16.57685, 15.66397, 14.75502, 13.83715,
            12.91643, 11.99202, 11.05599, 10.11336, 9.16159, 8.21474, 7.25331, 6.28431,
            5.33309, 4.36353, 3.63289, 3.08818, 2.65982, 2.3162, 2.04888, 1.83682,
            1.64919, 1.49532, 1.36826, 1.26044, 1.16812, 1.08448, 1.01446, 1.0
    };

}
