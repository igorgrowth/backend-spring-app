package com.manage.company.company.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@ToString(exclude = {"commentList"})
@EqualsAndHashCode(exclude = {"commentList"})
@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    public Topic(Long id, String title, String description, List<Comment> commentList) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.commentList = commentList;
    }

    // Getters and Setters

    public void addComment(Comment comment) {
        commentList.add(comment);
        comment.setTopic(this);
    }

    public void removeComment(Comment comment) {
        commentList.remove(comment);
        comment.setTopic(null);
    }
}
