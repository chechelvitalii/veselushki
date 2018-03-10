package top.veselushki.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.veselushki.model.FacebookPost;
import top.veselushki.repository.FacebookPostRepository;
import top.veselushki.service.FacebookParseService;
import top.veselushki.service.SiteParseService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
@Transactional
@Slf4j
public class NewFacebookPostSaver {
    @Autowired
    private SiteParseService siteParseService;
    @Autowired
    private FacebookParseService facebookParseService;
    @Autowired
    private FacebookPostRepository facebookPostRepository;

    @Scheduled(cron = "${cron.save.newPost}")
    public void saveNewPosts() {
        List<String> lastTopicLinks = siteParseService.getLastTopicLinks();
        List<String> unsavedLinks = getUnsavedTopicLinks(lastTopicLinks);
        List<FacebookPost> publishedPosts = facebookParseService.getPublishedPosts(unsavedLinks);
        facebookPostRepository.save(publishedPosts);
        log.info("Saved {} links for facebook posts", publishedPosts.size());
    }

    private List<String> getUnsavedTopicLinks(List<String> lastTopicLinks) {
        List<String> savedLastTopicLinks = facebookPostRepository.findAll(lastTopicLinks).stream()
                .map(FacebookPost::getTopicLink)
                .collect(Collectors.toList());
        return lastTopicLinks.stream()
                .filter(link -> !savedLastTopicLinks.contains(link))
                .collect(toList());
    }
}
