package com.isengardcode.votenorestaurante.repository;

import com.isengardcode.votenorestaurante.domain.Voter;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Voter entity.
 */
public interface VoterRepository extends JpaRepository<Voter,Long> {

}
