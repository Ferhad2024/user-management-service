package org.example.usermanagmentservice.Service.ServiceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.usermanagmentservice.entity.User;
import org.example.usermanagmentservice.entity.UserTxt;
import org.example.usermanagmentservice.Exception.UserNotFoundException;
import org.example.usermanagmentservice.Mapper.UserTxtMapper;
import org.example.usermanagmentservice.Service.RabbitMqService.RabbitMqPublisher;
import org.example.usermanagmentservice.Service.RedisService.RedisCacheService;
import org.example.usermanagmentservice.Service.Service.UserTxtService;
import org.example.usermanagmentservice.repository.UserTxtRepository;
import org.example.usermanagmentservice.repository.UserRepository;
import org.example.usermanagmentservice.request.UserTxtRequest;
import org.example.usermanagmentservice.response.UserTxtResponse;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserTxtServiceImpl implements UserTxtService {

    private final UserRepository userRepository;
    private final UserTxtRepository textRepository;
    private final UserTxtMapper mapper;
    private final RabbitMqPublisher rabbitMqPublisher;
    private final RedisCacheService redisCacheService;
    private final RedissonClient redissonClient;

    @Override
    @Transactional
    public UserTxtResponse uploadTxtAsync(UserTxtRequest request) throws IOException, InterruptedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User tapılmadı: " + username));
        RLock lock = redissonClient.getLock("user:text:lock:" + user.getId());
        lock.lock(10, TimeUnit.SECONDS);
        try {
            UserTxt txt = mapper.toEntity(request, user);
            textRepository.save(txt);
            rabbitMqPublisher.sendImageProcessingMessage(txt.getId());
            redisCacheService.cacheTxtMetadata(txt);
            return mapper.toResponse(txt);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<UserTxtResponse> getUserTxts() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User tapılmadı: " + username));
        List<UserTxt> text = textRepository.findByUserId(user.getId());

        return text.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
