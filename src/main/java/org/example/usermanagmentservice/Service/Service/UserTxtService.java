package org.example.usermanagmentservice.Service.Service;

import org.example.usermanagmentservice.request.UserTxtRequest;
import org.example.usermanagmentservice.response.UserTxtResponse;

import java.io.IOException;
import java.util.List;

public interface UserTxtService {

    /**
     * Upload a new image for the authenticated user
     * - saves in DB
     * - publishes to RabbitMQ for async processing
     * - caches metadata in Redis
     */
    UserTxtResponse uploadTxtAsync(UserTxtRequest request) throws IOException, InterruptedException;

    /**
     * Get all images for authenticated user
     */
    List<UserTxtResponse> getUserTxts();
}
