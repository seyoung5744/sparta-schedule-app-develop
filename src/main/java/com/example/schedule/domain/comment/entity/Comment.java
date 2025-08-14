package com.example.schedule.domain.comment.entity;

import com.example.schedule.common.entity.BaseEntity;
import com.example.schedule.domain.schedule.entity.Schedule;
import com.example.schedule.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    private String contents;

    private Comment(User user, String contents) {
        this.user = user;
        this.contents = contents;
    }

    public static Comment create(User user, Schedule schedule, String contents) {
        Comment comment = new Comment(user, contents);
        schedule.addComment(comment);
        return comment;
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }

    public void addSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
