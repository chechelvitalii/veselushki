package top.veselushki.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.veselushki.model.FacebookPost;
import top.veselushki.repository.FacebookPostRepository;
import top.veselushki.service.SiteParseService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toSet;

@Component
@Transactional
@Slf4j
public class SiteTopicSaver {
    @Autowired
    private SiteParseService siteParseService;
    @Autowired
    private FacebookPostRepository facebookPostRepository;

    @Scheduled(cron = "* * * ? * *")
    public void saveNewTopicFromSite() {
        Set<String> lastTopicLinks = siteParseService.getLastTopicLinks();
        Set<String> savedLinks = getSavedLinks(lastTopicLinks);
        Set<String> newLinks = resolveNewLinks(lastTopicLinks, savedLinks);
        List<FacebookPost> newFacebookPosts = newLinks.stream()
                .map(link -> new FacebookPost(link))
                .collect(Collectors.toList());
        facebookPostRepository.save(newFacebookPosts);
        log.info("Saved {} links for facebook posts", newLinks.size());
    }

    private Set<String> getSavedLinks(Set<String> lastTopicLinks) {
        Iterable<FacebookPost> allLinks = facebookPostRepository.findAll(lastTopicLinks);
        return StreamSupport.stream(allLinks.spliterator(), false)
                .map(FacebookPost::getTopicLink)
                .collect(toSet());
    }

    private Set<String> resolveNewLinks(Set<String> lastTopicLinks, Set<String> savedLinks) {
        return lastTopicLinks.stream()
                .filter(lastTopicLink -> !savedLinks.contains(lastTopicLink))
                .collect(toSet());
    }
}
