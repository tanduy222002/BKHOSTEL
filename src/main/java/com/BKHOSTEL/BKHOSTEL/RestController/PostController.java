package com.BKHOSTEL.BKHOSTEL.RestController;

import com.BKHOSTEL.BKHOSTEL.Dto.CreatePostDto;
import com.BKHOSTEL.BKHOSTEL.Dto.PaginationDto;
import com.BKHOSTEL.BKHOSTEL.Entity.Post;
import com.BKHOSTEL.BKHOSTEL.Service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping(value = "/posts")
@SecurityRequirement(name = "bearerAuth")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("")
    @Operation(summary = "Create a new post")
    public ResponseEntity<?> createPost(@Valid @RequestBody CreatePostDto dto){
       String message= postService.createPost(new Post(dto),dto.getServiceTypeId());
       return ResponseEntity.ok(message);
    }


    @GetMapping("")
    @Operation(summary = "Get posts with filter and pagination")
    public ResponseEntity<?> getAllPost(@RequestParam(required = false) String area,
                                        @Min(0) @RequestParam(required = false) String minPrice,
                                        @RequestParam(required = false) String maxPrice,
                                        @RequestParam(required = false) String status,
                                        @RequestParam(required = false) String userId,
                                        @RequestParam(required = false) String ward,
                                        @RequestParam(required = false) String district,
                                        @RequestParam(required = false) String city,
                                        @RequestParam(required = false) String customerType,
                                        @Min(5) @RequestParam(defaultValue = "5", required = false) int pageSize,
                                        @Min(1) @RequestParam(defaultValue = "1",required = false) int pageIndex) {
        PaginationDto post= postService.getAllPost(area, minPrice, maxPrice, status, userId, ward, district, city, customerType, pageSize,pageIndex);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/{postId}")
    @Operation(summary = "Get detailed information of a specific post")
    public ResponseEntity<?> getPostById(@PathVariable("postId") String id){
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

}
