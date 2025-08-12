package com.example.schedule.comment.entity;

import com.example.schedule.common.entity.BaseEntity;
import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.user.entity.User;
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

    private Comment(User user, Schedule schedule, String contents) {
        this.user = user;
        this.schedule = schedule;
        this.contents = contents;
    }

    public static Comment create(User user, Schedule schedule, String contents) {
        return new Comment(user, schedule, contents);
    }
}
