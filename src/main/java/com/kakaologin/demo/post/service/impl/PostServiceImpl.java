package com.kakaologin.demo.post.service.impl;

import com.kakaologin.demo.post.dto.PostDto;
import com.kakaologin.demo.post.dto.User1Dto;
import com.kakaologin.demo.post.entity.Post;
import com.kakaologin.demo.post.entity.User1;
import com.kakaologin.demo.post.repository.PostRepository;
import com.kakaologin.demo.post.repository.User1Repository;
import com.kakaologin.demo.post.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final User1Repository user1Repository;


    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper
                            ,User1Repository user1Repository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.user1Repository =  user1Repository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        // UserDto를 User 엔티티로 변환
        User1 user = modelMapper.map(postDto.getAuthor(), User1.class);

        User1 savedUser = user1Repository.save(user);

        // PostDto를 Post 엔티티로 변환
        Post post = modelMapper.map(postDto, Post.class);

        // 작성자 정보 설정
        post.setAuthor(user);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        // DB에 저장 (postRepository는 DB 저장 역할)
        Post savedPost = postRepository.save(post);

        // 저장된 Post 객체를 PostDto로 변환
        PostDto result = modelMapper.map(savedPost, PostDto.class);

        // UserDto로 변환 후 PostDto의 author 필드에 설정
        User1Dto userDto = modelMapper.map(savedPost.getAuthor(), User1Dto.class);
        result.setAuthor(userDto);

        return result;




//        // DTO → Entity 변환
//        Post post = modelMapper.map(postDto, Post.class);
//        post.setCreatedAt(LocalDateTime.now());
//        post.setUpdatedAt(LocalDateTime.now());
//
//        // DB 저장
//        Post savedPost = postRepository.save(post);
//
//        // Entity → DTO 변환
//        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public List<PostDto> getAllPosts(int page) {

        int pageSize = 5;

        // 페이지 계산 (5개씩 이동)
        int offset = page * pageSize;

        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(offset / pageSize, pageSize, Sort.by(Sort.Order.desc("createdAt")));

        // 페이징된 게시글 목록 가져오기

        Page<Post> postPage = postRepository.findAllActivePosts(pageable);

        // Post 객체를 PostDto로 변환
        return postPage.getContent().stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deletePost(Long id) {

        // 게시글이 존재하는지 확인
        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional.isPresent()) {
            // 게시글 삭제
            Post post = postOptional.get();
            post.setDeleted(true); // Soft delete 처리
            postRepository.save(post); // 변경 사항 저장
            return true;

        } else {
            // 게시글이 존재하지 않으면 false 반환
            return false;
        }
    }

}
