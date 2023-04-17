package com.manage.company.company.controller;
import com.manage.company.company.dto.CommentDTO;
import com.manage.company.company.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("${url}/topics/{topicId}/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<Page<CommentDTO>> getComments(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<CommentDTO> commentsPage = commentService.getAll(pageable);
        return ResponseEntity.ok().body(commentsPage);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO,
                                                    @PathVariable Long topicId) {
        return new ResponseEntity<>(commentService.save(commentDTO, topicId), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentDTO commentDTO,
                                                    @PathVariable Long topicId) {
        return ResponseEntity.ok().body(commentService.update(commentDTO, topicId ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok().body(commentService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommentDTO> deleteComment(@PathVariable Long id) {
        return ResponseEntity.ok().body(commentService.delete(id));
    }

    @GetMapping("/byTopicTitle/{title}")
    public ResponseEntity<Page<CommentDTO>> getCommentsByTopicTitle(@PathVariable String title,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<CommentDTO> commentsPage = commentService.findByTopicTitle(title, pageable);
        return ResponseEntity.ok().body(commentsPage);
    }

    @GetMapping("/byEmployeeName/{name}")
    public ResponseEntity<Page<CommentDTO>> getCommentsByEmployeeName(@PathVariable Long userId,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<CommentDTO> commentsPage = commentService.findByUserId(userId, pageable);
        return ResponseEntity.ok().body(commentsPage);
    }


}
