package top.veselushki.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import top.veselushki.model.FacebookPost;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static top.veselushki.model.FacebookPost.PostStatus.NEW;
import static top.veselushki.model.FacebookPost.PostStatus.SENT;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@TestPropertySource(value = "classpath:test-application.properties")
public class FacebookPostRepositoryIT {
    @Autowired
    private FacebookPostRepository facebookPostRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    public void shouldFindNewPosts() {
        //GIVEN
        FacebookPost sentFacebookPost = new FacebookPost("sentPostLink", "sentPostLabel");
        sentFacebookPost.setStatus(SENT);
        FacebookPost newFacebookPost = new FacebookPost("newPostLink", "newPostLabel");

        entityManager.persist(sentFacebookPost);
        entityManager.persist(newFacebookPost);
        assertThat(facebookPostRepository.findAll(), hasSize(2));
        //WHEN
        List<FacebookPost> notSentPosts = facebookPostRepository.findNotSentPosts();
        //THEN
        assertThat(notSentPosts, hasSize(1));
        FacebookPost notSentPost = notSentPosts.get(0);
        assertThat(notSentPost.getTopicLink(), is("newPostLink"));
        assertThat(notSentPost.getLabel(), is("newPostLabel"));
        assertThat(notSentPost.getStatus(), is(FacebookPost.PostStatus.NEW));
        assertNotNull(notSentPost.getCreatedDateTime());
        assertNull(notSentPost.getSentDateTime());
    }

    @Test
    public void shouldUpdatePostStatusToSent() {
        //GIVEN
        FacebookPost facebookPost = new FacebookPost("newPostLink", "newPostLabel");
        entityManager.persist(facebookPost);
        assertThat(facebookPost.getStatus(), is(NEW));
        //WHEN
        facebookPostRepository.updateToSent(asList(facebookPost.getTopicLink()));
        entityManager.refresh(facebookPost);
        //THEN
        assertThat(facebookPost.getStatus(), is(SENT));
        assertNotNull(facebookPost.getSentDateTime());
    }
}