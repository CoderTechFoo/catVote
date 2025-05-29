package org.csf.cat.dao;


import org.csf.cat.model.Cat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CatDao extends JpaRepository<Cat, String> {

    Page<Cat> findAllByOrderByIdAsc(Pageable pageable);

    @Transactional(propagation = Propagation.REQUIRED)
    @Modifying
    @Query("UPDATE Cat c SET c.voteCount = c.voteCount + 1 WHERE c.id = :id")
    void incrementVoteCount(@Param("id") String id);

    @Query("SELECT c FROM Cat c ORDER BY c.voteCount DESC")
    List<Cat> findTopVote(Pageable pageable);

    Optional<Cat> findById(String id);

    Cat save(Cat cat);
}
