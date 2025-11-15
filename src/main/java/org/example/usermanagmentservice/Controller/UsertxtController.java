package org.example.usermanagmentservice.Controller;

import lombok.RequiredArgsConstructor;
import org.example.usermanagmentservice.Service.Service.UserTxtService;
import org.example.usermanagmentservice.request.UserTxtRequest;
import org.example.usermanagmentservice.response.UserTxtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/app/dashboard/images")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "https://txt-dowland.netlify.app")
public class UsertxtController {

    private final UserTxtService textService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/upload")
    public ResponseEntity<UserTxtResponse> uploadTxt(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        if (file == null || file.isEmpty()) {
            System.out.println("file null dur");
        }
        System.out.println("Received file: " + file.getOriginalFilename());
        UserTxtRequest request = new UserTxtRequest(file);
        UserTxtResponse response = textService.uploadTxtAsync(request);
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<UserTxtResponse>> getUserTxt() {
        List<UserTxtResponse> text = textService.getUserTxts();
        return ResponseEntity.ok(text);
    }
}
