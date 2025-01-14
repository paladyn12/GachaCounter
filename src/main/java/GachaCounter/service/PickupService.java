package GachaCounter.service;

import GachaCounter.domain.entity.Pickup;
import GachaCounter.repository.PickupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PickupService {

    private final PickupRepository pickupRepository;

    // 하루에 한 번 실행 (cron 표현식)
    @Scheduled(cron = "0 0 0 * * ?")  // 매일 자정 12시에 실행
    public void cleanupExpiredEntities() {
        LocalDateTime now = LocalDateTime.now();
        List<Pickup> expiredEntities = pickupRepository.findByEndDateBefore(now);

        if (!expiredEntities.isEmpty()) {
            pickupRepository.deleteAll(expiredEntities);
            System.out.println("Deleted expired entities: " + expiredEntities.size());
        }
    }
}
