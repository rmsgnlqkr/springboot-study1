package com.springboot.example.study.service.posts;

import com.springboot.example.study.domain.posts.Posts;
import com.springboot.example.study.domain.posts.PostsRepository;
import com.springboot.example.study.web.dto.PostsListResponseDto;
import com.springboot.example.study.web.dto.PostsResponseDto;
import com.springboot.example.study.web.dto.PostsSaveRequestDto;
import com.springboot.example.study.web.dto.PostsUpdateRequestDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        postsRepository.delete(posts);
    }


    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new PostsResponseDto(entity);

    }

    //@Transactional(readOnly = true)
    //public ResponseEntity<PostsResponseDto> findByIds(Long id) {
    //    Posts entity = postsRepository.findById(id)
    //            .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
    //    PostsResponseDto dto = new PostsResponseDto(entity);
    //
    //    return new ResponseEntity<PostsResponseDto>(dto, HttpStatus.OK);
    //}


    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }


    //@Transactional(readOnly = true)
    //public List<PostsListResponseDto> findAll() {
    //    PageRequest page = PageRequest.of(0, 12, Sort.by("id").ascending());
    //
    //    return postsRepository.findAll(page).stream().map(PostsListResponseDto::new).collect(Collectors.toList());
    //}


}