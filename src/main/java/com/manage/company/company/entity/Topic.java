package com.manage.company.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@ToString(exclude = {"commentList"})
@EqualsAndHashCode(exclude = {"commentList"})
@Entity
@Table(name = "topics")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
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

    public void addComment(Comment comment) {
        commentList.add(comment);
        comment.setTopic(this);
    }

    public void removeComment(Comment comment) {
        commentList.remove(comment);
        comment.setTopic(null);
    }
}
