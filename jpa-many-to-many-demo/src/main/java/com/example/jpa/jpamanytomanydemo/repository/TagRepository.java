package com.example.jpa.jpamanytomanydemo.repository;

import com.example.jpa.jpamanytomanydemo.models.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Page<Tag> findByPostId(Long postId, Pageable pageable);
    Optional<Tag> findByIdAndPostId(Long id, Long postId);
}
