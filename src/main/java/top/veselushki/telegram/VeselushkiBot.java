package top.veselushki.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramWebhookBot;

@Slf4j
@Component
public class VeselushkiBot extends TelegramWebhookBot {
    // Initialize Api Context
    static {
        ApiContextInitializer.init();
    }

    public static final String CHAT_ID = "@veselushki";

    private final String botToken = "531112787:AAHT3fIabCNI_Al2LsQblOBoBfcBO6Mf4Tc";
    private final String botUserName = "VeselushkiBot";

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {
        log.warn("EVENT: onWebhookUpdateReceived not handled !!!");
        return null;
    }

    @Override
    public String getBotPath() {
        log.warn("EVENT: getBotPath not handled !!!");
        return null;
    }

}
