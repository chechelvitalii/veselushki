package top.veselushki.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import top.veselushki.model.FacebookPost;
import top.veselushki.repository.FacebookPostRepository;
import top.veselushki.telegram.VeselushkiBot;
import top.veselushki.telegram.object.InstantViewMessage;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@Transactional
@Slf4j
public class TelegramPostSender {
    @Autowired
    private VeselushkiBot bot;
    @Autowired
    private FacebookPostRepository facebookPostRepository;

    @Scheduled(cron = "${cron.send.telegram}")
    public void sendPostToTelegram() {
        List<FacebookPost> newPosts = facebookPostRepository.findNotSentPosts();
        List<SendMessage> messageForSending = convertToMessage(newPosts);
        for (SendMessage message : messageForSending) {
            bot.safetySendMessage(message);
            log.info("Post sent: {}", message);
        }
        List<String> postLinks = newPosts.stream()
                .map(FacebookPost::getTopicLink)
                .collect(toList());
        if (!postLinks.isEmpty()) {
            facebookPostRepository.updateToSent(postLinks);
        }
    }

    private List<SendMessage> convertToMessage(List<FacebookPost> facebookPosts) {
        return facebookPosts.stream()
                .map(facebookPost -> new InstantViewMessage(facebookPost.getTopicLink(), facebookPost.getLabel()))
                .collect(toList());
    }
}
