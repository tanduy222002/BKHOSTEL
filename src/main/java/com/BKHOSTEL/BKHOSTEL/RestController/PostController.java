package com.BKHOSTEL.BKHOSTEL.RestController;

import com.BKHOSTEL.BKHOSTEL.Dto.CreatePostDto;
import com.BKHOSTEL.BKHOSTEL.Entity.Post;
import com.BKHOSTEL.BKHOSTEL.Service.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/posts")
@SecurityRequirement(name = "bearerAuth")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("")
    public ResponseEntity<?> createPost(@Valid @RequestBody CreatePostDto dto){
       String message= postService.createPost(new Post(dto),dto.getServiceTypeId());
       return ResponseEntity.ok(message);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getAllUserPost(@PathVariable("id") String id){
        List<Post> postList= postService.getAllUserPost(id);
        System.out.println(postList);
        return ResponseEntity.ok(postList);
    }

}
