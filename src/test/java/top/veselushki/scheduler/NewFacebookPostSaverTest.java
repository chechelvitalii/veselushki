package top.veselushki.scheduler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import top.veselushki.model.FacebookPost;
import top.veselushki.repository.FacebookPostRepository;
import top.veselushki.service.FacebookParseService;
import top.veselushki.service.SiteParseService;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NewFacebookPostSaverTest {
    @InjectMocks
    private NewFacebookPostSaver sut;
    @Mock
    private SiteParseService siteParseService;
    @Mock
    private FacebookParseService facebookParseService;
    @Mock
    private FacebookPostRepository facebookPostRepository;

    @Test
    public void shouldSaveNewTopicFromSite() {
        //GIVEN
        List<String> lastTopicLinks = asList("http://veselushki.top/test1", "http://veselushki.top/test2");
        when(siteParseService.getLastTopicLinks()).thenReturn(lastTopicLinks);
        FacebookPost savedFacebookPost = new FacebookPost("http://veselushki.top/test1", null);
        when(facebookPostRepository.findAll(lastTopicLinks)).thenReturn(asList(savedFacebookPost));
        List<FacebookPost> newFacebookPosts = asList(new FacebookPost("http://veselushki.top/test2", null));
        when(facebookParseService.getPublishedPosts(anyList())).thenReturn(newFacebookPosts);
        //WHEN
        sut.saveNewPosts();
        //THEN
        verify(facebookParseService).getPublishedPosts(asList("http://veselushki.top/test2"));
        verify(facebookPostRepository).save(newFacebookPosts);
    }
}