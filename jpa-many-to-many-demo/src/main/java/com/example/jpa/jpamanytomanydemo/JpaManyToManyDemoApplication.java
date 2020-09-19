package com.example.jpa.jpamanytomanydemo;

import com.example.jpa.jpamanytomanydemo.models.Post;
import com.example.jpa.jpamanytomanydemo.models.Tag;
import com.example.jpa.jpamanytomanydemo.repository.PostRepository;
import com.example.jpa.jpamanytomanydemo.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaManyToManyDemoApplication implements CommandLineRunner {

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private PostRepository postRepository;

	public static void main(String[] args) {
		SpringApplication.run(JpaManyToManyDemoApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		postRepository.deleteAllInBatch();
		tagRepository.deleteAllInBatch();

		Post post = new Post("Post title", "Post description", "Post content");

		Tag firstTag = new Tag("First tag name");
		Tag secondTag = new Tag("Second tag name");

		post.getTags().add(firstTag);
		post.getTags().add(secondTag);

		firstTag.getPosts().add(post);
		secondTag.getPosts().add(post);

		postRepository.save(post);
	}
}
