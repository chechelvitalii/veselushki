package top.veselushki.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "facebook_post")
public class FacebookPost {

    @Id
    @Column(name = "topicLink")
    private String topicLink;
    @Column(name = "label")
    private String label;
}
