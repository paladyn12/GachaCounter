package GachaCounter.service;

import GachaCounter.domain.dto.CounterRequest;
import GachaCounter.domain.entity.User;
import GachaCounter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CounterService {

    private final UserRepository userRepository;

    public void setCounter(User user, CounterRequest counterRequest) {
        user.updateCounter(counterRequest.getCharacterCount(), counterRequest.getLightConeCount(),
                counterRequest.isCharacterIsFull(), counterRequest.isLightConeIsFull());
        userRepository.save(user);
    }
}
