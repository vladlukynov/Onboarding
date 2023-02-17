package com.api.userservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.api.userservice.DTO.ColleagueDTO;
import com.api.userservice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    @Modifying
    @Query("update User u set u.post.id = ?1 where u.email = ?2")
    void updatePostIdByEmailEquals(@NonNull Long id, @NonNull String email);

    @Transactional
    @Modifying
    @Query("update User u set u.team.id = ?1 where u.email = ?2")
    void updateTeamIdByEmailEquals(@NonNull Long id, @NonNull String email);

    @Transactional
    @Modifying
    @Query("update User u set u.dateStartWork = ?1 where u.email = ?2")
    void updateDateStartWorkByEmailEquals(@Nullable String dateStartWork, @NonNull String email);

    List<User> findUsersByEmail(String email);

    @Query(value = "UPDATE User u SET u.linkPhoto=:linkPhoto WHERE u.id=:userId")
    @Modifying
    @Transactional
    void updateLinkPhotoByUserId(String linkPhoto, Long userId);

    @Query(value = "DELETE FROM User u WHERE u.email=:email and u.isEnabled=false")
    @Modifying
    @Transactional
    void deleteAllUsersExceptVerified(String email);

    @Query("SELECT new com.api.userservice.DTO.ColleagueDTO(u.id, u.name, u.post.name) FROM User u" +
            " WHERE u.team.id=:teamId AND u.id<>:userId AND u.isEnabled = true")
    List<ColleagueDTO> getColleagues(Long userId, Long teamId);
}
