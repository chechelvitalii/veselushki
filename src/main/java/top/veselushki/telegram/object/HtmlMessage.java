package top.veselushki.telegram.object;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import top.veselushki.telegram.VeselushkiBot;

public class HtmlMessage extends SendMessage {
    private String instantView;

    public HtmlMessage() {
        setChatId(VeselushkiBot.CHAT_ID);
        setParseMode("HTML");
    }

    public SendMessage addInstantView(String pageName) {
        instantView = InstantView.create(pageName);
        return this;
    }

    @Override
    public SendMessage setText(String text) {
        String fullText = instantView + text;
        return super.setText(fullText);
    }
}
