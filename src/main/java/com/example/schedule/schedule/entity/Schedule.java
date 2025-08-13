package com.example.schedule.schedule.entity;

import com.example.schedule.common.entity.BaseEntity;
import com.example.schedule.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    private Schedule(User user, String title, String contents) {
        this.user = user;
        this.title = title;
        this.contents = contents;
    }

    public static Schedule create(User user, String title, String contents) {
        return new Schedule(user, title, contents);
    }

    public void updateTitleAndContents(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
