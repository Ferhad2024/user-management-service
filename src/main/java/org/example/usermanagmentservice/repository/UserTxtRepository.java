package org.example.usermanagmentservice.repository;

import org.example.usermanagmentservice.entity.UserTxt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTxtRepository extends JpaRepository<UserTxt, Long> {

    List<UserTxt> findByUserId(Long userId);
}
