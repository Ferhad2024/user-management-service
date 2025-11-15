package org.example.usermanagmentservice.Service.RedisService;

import lombok.RequiredArgsConstructor;

import org.example.usermanagmentservice.entity.UserTxt;
import org.example.usermanagmentservice.response.UserTxtResponse;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisCacheService {

    private final RedissonClient redissonClient;

    public void cacheTxtMetadata(UserTxt userTxt) {
        RMapCache<Long, UserTxtResponse> mapCache = redissonClient.getMapCache("user:txt:metadata");

        UserTxtResponse response = new UserTxtResponse(
                userTxt.getId(),
                userTxt.getFileName(),
                userTxt.getStatus().name(),
                userTxt.getCreatedAt(),
                userTxt.getFileContent()
        );
        mapCache.put(userTxt.getId(), response, 60, TimeUnit.MINUTES);
    }

    public UserTxtResponse getCachedTxt(Long txtId) {
        RMapCache<Long, UserTxtResponse> mapCache = redissonClient.getMapCache("user:txt:metadata");
        return mapCache.get(txtId);
    }
}