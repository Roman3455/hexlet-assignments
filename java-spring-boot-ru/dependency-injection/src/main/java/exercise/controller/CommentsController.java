package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Comment getComment(@PathVariable Long id) {
        var commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            return commentOptional.get();
        } else {
            throw new ResourceNotFoundException("Comment with id " + id + " not found");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(@RequestBody Comment comment) {
        commentRepository.save(comment);
        return comment;
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        var commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            var updatedComment = commentOptional.get();
            updatedComment.setBody(comment.getBody());
            commentRepository.save(updatedComment);
            return comment;
        } else {
            throw new ResourceNotFoundException("Comment with id " + id + " not found");
        }
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable Long id) {
        var commentOptional = commentRepository.findById(id);
        if (commentOptional.isPresent()) {
            commentRepository.delete(commentOptional.get());
        } else {
            throw new ResourceNotFoundException("Comment with id " + id + " not found");
        }
    }

}
// END
