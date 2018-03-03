package top.veselushki.facade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import top.veselushki.model.FacebookPost;
import top.veselushki.repository.FacebookPostRepository;
import top.veselushki.service.FacebookParseService;
import top.veselushki.service.SiteParseService;
import top.veselushki.telegram.VeselushkiBot;
import top.veselushki.telegram.object.InstantViewMessage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class PostPublisherFacade {
    @Autowired
    private VeselushkiBot bot;
    @Autowired
    private FacebookParseService facebookParseService;
    @Autowired
    private SiteParseService siteParseService;

    //TODO every 30 min
    @Scheduled(cron = "* * * ? * *")
    public void publish() throws TelegramApiException, IOException {
        List<String> lastTopicLinks = siteParseService.getLastTopicLinks();
        List<FacebookPost> facebookPublishedPosts = facebookParseService.getPublishedPosts(lastTopicLinks);
        if (facebookPublishedPosts.isEmpty()) {
            log.info("Post wasn't found at time: {}", LocalDateTime.now());
        } else {
            log.info("Was found {} posts: \n {}", facebookPublishedPosts.size(), facebookPublishedPosts.toString());
            List<SendMessage> messageReadyForSending = convertToMessage(facebookPublishedPosts);
            for (SendMessage sendMessage : messageReadyForSending) {
                bot.sendMessage(sendMessage);
                log.info("Post sent: {}", sendMessage);
            }
        }
    }

    private List<SendMessage> convertToMessage(List<FacebookPost> facebookPublishedPosts) {
        return facebookPublishedPosts.stream()
                .map(facebookPost -> new InstantViewMessage(facebookPost.getTopicLink(), facebookPost.getLabel()))
                .collect(toList());

    }
}
