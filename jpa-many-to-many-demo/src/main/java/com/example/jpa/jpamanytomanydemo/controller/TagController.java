package com.example.jpa.jpamanytomanydemo.controller;

import com.example.jpa.jpamanytomanydemo.exception.ResourceNotFoundException;
import com.example.jpa.jpamanytomanydemo.models.Tag;
import com.example.jpa.jpamanytomanydemo.repository.PostRepository;
import com.example.jpa.jpamanytomanydemo.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TagController {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts/{postId}/tags")
    public Page<Tag> getAllTags(@PathVariable (value = "postId") Long postId,
                                Pageable pageable) {
        return tagRepository.findByPostId(postId, pageable);
    }

    @PostMapping("/posts/{postId}/tags")
    public Tag createTag(@PathVariable (value = "postId") Long postId,
                          @Valid @RequestBody Tag tagRequest) {
        return postRepository.findById(postId)
                .map(post -> {
                    post.getTags().add(tagRequest);
                    postRepository.save(post);
                    tagRequest.getPosts().add(post);
                    return tagRepository.save(tagRequest);
                }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }

    @PutMapping("/posts/{postId}/tags/{tagId}")
    public Tag updateTag(@PathVariable Long postId,
                         @PathVariable Long tagId,
                         @Valid @RequestBody Tag tagRequest) {
        return tagRepository.findById(tagId)
                .map(tag -> {
                    tag.setName(tagRequest.getName());
                    tagRepository.save(tag);
                    postRepository.findById(postId)
                            .map(post -> {
                                post.getTags().remove(tag);
                            })
                })
    }
}
