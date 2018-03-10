package top.veselushki.scheduler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import top.veselushki.model.FacebookPost;
import top.veselushki.repository.FacebookPostRepository;
import top.veselushki.service.SiteParseService;

import java.util.HashSet;
import java.util.List;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static top.veselushki.model.FacebookPost.PostStatus.NEW;

@RunWith(MockitoJUnitRunner.class)
public class SiteTopicSaverTest {
    @InjectMocks
    private SiteTopicSaver sut;
    @Mock
    private SiteParseService siteParseService;
    @Mock
    private FacebookPostRepository facebookPostRepository;
    @Captor
    private ArgumentCaptor<List<FacebookPost>> facebookPostCaptor;

    @Test
    public void shouldSaveNewTopicFromSite() {
        //GIVEN
        HashSet<String> lastTopicLinks = newHashSet("http://veselushki.top/test1", "http://veselushki.top/test2");
        when(siteParseService.getLastTopicLinks())
                .thenReturn(lastTopicLinks);
        when(facebookPostRepository.findAll(lastTopicLinks))
                .thenReturn(asList(new FacebookPost("http://veselushki.top/test1")));
        //WHEN
        sut.saveNewTopicFromSite();
        //THEN
        verify(facebookPostRepository).save(facebookPostCaptor.capture());
        List<FacebookPost> newFacebookPosts = facebookPostCaptor.getValue();
        assertThat(newFacebookPosts, hasSize(1));
        FacebookPost newFacebookPost = newFacebookPosts.get(0);
        assertThat(newFacebookPost.getTopicLink(),is("http://veselushki.top/test2"));
        assertThat(newFacebookPost.getPostStatus(),is(NEW));
        assertNull(newFacebookPost.getLabel());
    }
}