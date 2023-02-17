package com.api.userservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api.userservice.DTO.TeamDTO;
import com.api.userservice.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("SELECT new com.api.userservice.DTO.TeamDTO(t.id, t.name) FROM Team t")
    List<TeamDTO> getAllTeam();
}
