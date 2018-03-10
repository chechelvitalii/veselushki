package top.veselushki.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static top.veselushki.model.FacebookPost.PostStatus.NEW;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "facebook_post")
public class FacebookPost {
    public FacebookPost(String topicLink, String label) {
        this.topicLink = topicLink;
        this.label = label;
    }

    public enum PostStatus {NEW, SENT}

    @Id
    @Column(name = "topicLink", nullable = false)
    private String topicLink;

    @Column(name = "label")
    private String label;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", length = 4, nullable = false)
    private PostStatus status = NEW;
}
