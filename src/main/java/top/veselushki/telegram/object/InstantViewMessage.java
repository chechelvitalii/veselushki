package top.veselushki.telegram.object;

import lombok.Value;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import top.veselushki.telegram.VeselushkiBot;

@Value
public class InstantViewMessage extends SendMessage {
    private static final String ICO = "\uD83C\uDF08";
    private static final String INSTANT_VIEW_PREFIX = "<a href=\"https://t.me/iv?url=";
    private static final String INSTANT_VIEW_SUFFIX = "&rhash=9bb7ae35f0799e\">" + ICO + "</a>";
    private String topicLink;
    private String label;

    public InstantViewMessage(String topicLink, String label) {
        this.topicLink = topicLink;
        this.label = label;
        createTextMessage();
        setChatId(VeselushkiBot.CHAT_ID);
        setParseMode("HTML");
    }

    private void createTextMessage() {
        String telegramLink = createTelegramLink(topicLink);
        setText(telegramLink + label);
    }

    public String createTelegramLink(String topicLink) {
        return INSTANT_VIEW_PREFIX + topicLink + INSTANT_VIEW_SUFFIX;
    }
}
