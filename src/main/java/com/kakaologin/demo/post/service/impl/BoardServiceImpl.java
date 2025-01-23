package com.kakaologin.demo.post.service.impl;

import com.kakaologin.demo.post.dto.*;
import com.kakaologin.demo.post.entity.Board;
import com.kakaologin.demo.post.entity.BoardStatus;
import com.kakaologin.demo.post.entity.Comment;
import com.kakaologin.demo.post.entity.Users;
import com.kakaologin.demo.post.repository.BoardRepository;
import com.kakaologin.demo.post.repository.BoardStatusRepository;
import com.kakaologin.demo.post.repository.CommentRepository;
import com.kakaologin.demo.post.repository.UsersRepository;
import com.kakaologin.demo.post.service.BoardService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;
    private final UsersRepository usersRepository;
    private final RedisTemplate<String, Integer> redisTemplate;
    private final BoardStatusRepository boardStatusRepository;
    private final CommentRepository commentRepository;
    public BoardServiceImpl(BoardRepository boardRepository, ModelMapper modelMapper
                            , UsersRepository usersRepository
                            , RedisTemplate<String, Integer> redisTemplate
                            , BoardStatusRepository boardStatusRepository
                            , CommentRepository commentRepository) {
        this.boardRepository = boardRepository;
        this.modelMapper = modelMapper;
        this.usersRepository =  usersRepository;
        this.redisTemplate = redisTemplate;
        this.boardStatusRepository = boardStatusRepository;
        this.commentRepository = commentRepository;
    }

    private static final String VIEW_KEY = "board:%d:view";  // 게시글 조회수 키
    private static final String LIKE_KEY = "post:%d:like";  // 게시글 좋아요 키

    public int getView(Long boardId) {
        String viewKey = String.format(VIEW_KEY, boardId);
        Integer viewCount = redisTemplate.opsForValue().get(viewKey);

//        if (viewCount == null) {
//            // Redis에서 조회수 없으면 DB에서 가져옴
//            BoardStatus metrics = boardStatusRepository.findByBoardId(boardId);
//            System.out.println("나 db에꺼 가져왔어요 !!!!!!!!!!");
//            if (metrics != null) {
//                viewCount = metrics.getViewCount();
//                redisTemplate.opsForValue().set(viewKey, viewCount);  // Redis에 저장
//
//            } else {
//                viewCount = 0;  // DB에도 없으면 조회수를 0으로 설정
//            }
//        }
            if(viewCount==null)
                viewCount=0;

        return viewCount;
    }

    public void incrementView(Long boardId) {
        String viewKey = String.format(VIEW_KEY, boardId);

        Integer updatedViewCount = redisTemplate.opsForValue().get(viewKey);

        // Redis에 조회수가 없으면 DB에서 값을 가져와 Redis에 저장
//        if (updatedViewCount == null) {
//            BoardStatus boardStatus = boardStatusRepository.findByBoardId(boardId);
//            if (boardStatus != null) {
//                System.out.println("증가했는데 redis에 없어서  기존의 값 db에서 가져왔어요");
//                updatedViewCount = boardStatus.getViewCount();  // DB에서 조회수 가져옴
//                redisTemplate.opsForValue().set(viewKey, updatedViewCount);  // Redis에 값 저장
//            }
//        }
        if (updatedViewCount == null)
            redisTemplate.opsForValue().set(viewKey, 0);

        // Redis에서 조회수 증가
        redisTemplate.opsForValue().increment(viewKey, 1);  // 1 증가

        //redisTemplate.expire(viewKey, 1, TimeUnit.MINUTES);  // TTL을 1분으로 설정

        // Redis에서 증가된 조회수를 가져옴
        //updatedViewCount = redisTemplate.opsForValue().get(viewKey);

        // DB에 반영 (조회수가 변경되었으므로 DB에도 업데이트)
        /*BoardStatus boardStatus = boardStatusRepository.findByBoardId(boardId);
        if (boardStatus != null) {
            // DB에 기존 값이 있으면 업데이트
            boardStatus.setViewCount(updatedViewCount);
            boardStatusRepository.save(boardStatus);
        } else {
            // DB에 없으면 새로 생성해서 저장
            BoardStatus newboardStatus = new BoardStatus();
            newboardStatus.setBoardId(boardId);
            newboardStatus.setViewCount(updatedViewCount);
            boardStatusRepository.save(newboardStatus);
        }*/

    }


    @Override
    public void createBoard(BoardRequestDto boardRequestDto) {

        Users user = usersRepository.findByUserid(boardRequestDto.getUserid());

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }


        // BoardDto를 Post 엔티티로 변환
        Board board = modelMapper.map(boardRequestDto, Board.class);

        // 작성자 정보 설정
        board.setAuthor(user);
        board.setCreatedAt(LocalDateTime.now());
        board.setUpdatedAt(LocalDateTime.now());


        // DB에 저장 (postRepository는 DB 저장 역할)
        boardRepository.save(board);

        /*// 저장된 Post 객체를 BoardDto로 변환
        BoardResponseDto result = modelMapper.map(savedPost, BoardResponseDto.class);

        // UserDto로 변환 후 BoardDto의 author 필드에 설정
        User1Dto userDto = modelMapper.map(savedPost.getAuthor(), User1Dto.class);
        result.setAuthor(userDto);*/

    }

    @Override
    public List<BoardResponseDto> getAllBoards(int page) {

        int pageSize = 5;

        // 페이지 계산 (5개씩 이동)
        int offset = page;

        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(offset, pageSize, Sort.by(Sort.Order.desc("createdAt")));

        // 페이징된 게시글 목록 가져오기
        Page<Board> qnaPage = boardRepository.findByCategoryAndIsDeletedFalse("qna", pageable);

        // 2. community 게시글 가져오기 (isDeleted = false)
        Page<Board> communityPage = boardRepository.findByCategoryAndIsDeletedFalse("community", pageable);

        // 3. qna와 community 게시글 합치기
        List<Board> combinedBoards = new ArrayList<>();
        combinedBoards.addAll(qnaPage.getContent());
        combinedBoards.addAll(communityPage.getContent());

        return combinedBoards.stream()
                .map(board -> {
                    // Board -> BoardResponseDto 변환
                    BoardResponseDto dto = modelMapper.map(board, BoardResponseDto.class);

                    // Redis에서 해당 게시글의 view 값을 조회 (예: "board:view:{boardId}" 형식으로 저장)
                    int viewcount = getView(board.getId());


                    // DTO에 view 값을 설정
                    dto.setViews(viewcount);  // 기본값 0 설정

                    return dto;
                })
                .collect(Collectors.toList());

        // Post 객체를 BoardDto로 변환
        /*return boardPage.getContent().stream()
                .map(board -> modelMapper.map(board, BoardResponseDto.class))
                .collect(Collectors.toList());*/
    }

    @Override
    public boolean deleteBoard(Long id) {

        // 게시글이 존재하는지 확인
        Optional<Board> boardOptional = boardRepository.findById(id);

        if (boardOptional.isPresent()) {
            // 게시글 삭제
            Board board = boardOptional.get();
            board.setDeleted(true); // Soft delete 처리
            boardRepository.save(board); // 변경 사항 저장
            return true;

        } else {
            // 게시글이 존재하지 않으면 false 반환
            return false;
        }
    }

    @Override
    public void addComment(Long boardId, CommentRequestDto commentRequestDto) {

        Users user = usersRepository.findByUserid(commentRequestDto.getUserid());

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));

        Comment comment = new Comment();
        comment.setComment_content(commentRequestDto.getComment_content());
        comment.setBoard(board);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setAuthor(user); // 예시로 작성자 정보를 DTO에서 받아옴
        board.getComments().add(comment);

        // 댓글 저장
        commentRepository.save(comment);
    }
    @Override
    public List<CommentResponseDto> getCommentsByBoardId(Long boardId) {
        // 필요한 데이터만 조회
        List<Comment> comments = commentRepository.findCommentsByBoardIdWithAuthor(boardId);

        // DTO 변환
        return comments.stream()
                .map(comment -> {
                    // Comment -> CommentResponseDto 변환
                    CommentResponseDto commentResponseDto = modelMapper.map(comment, CommentResponseDto.class);

                    return commentResponseDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public BoardDetailResponseDto getBoardDetails(Long boardId) {
        // Board 데이터 조회
        Board board = boardRepository.findByIdWithCommentsAndAuthor(boardId);
        if (board == null) {
            throw new IllegalArgumentException("Board not found with id: " + boardId);
        }

        // Board 데이터를 DTO로 변환
        return new BoardDetailResponseDto(board);
    }

    @PostConstruct
    public void initData() {
        if (usersRepository.count() == 0) {
            // 10개의 임의 데이터를 생성하여 삽입
            for (int i = 1; i <= 10; i++) {
                Users user = new Users();
                user.setUserid("testUser" + i);
                user.setProfileImage("default-profile" + i + ".jpg");
                user.setActivityScore(50 + i);  // 예시로 점수를 51~60으로 설정
                usersRepository.save(user);
            }
        }
    }



}
