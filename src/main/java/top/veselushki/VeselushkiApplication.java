package top.veselushki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import top.veselushki.telegram.VeselushkiBot;
import top.veselushki.telegram.object.HtmlMessage;

@SpringBootApplication
@ComponentScan
public class VeselushkiApplication {
    @Autowired
    private VeselushkiBot bot;


    public static void main(String[] args) {
        SpringApplication.run(VeselushkiApplication.class, args);
    }

//    @Bean
//    public String test() throws TelegramApiException {
//        SendMessage message = new HtmlMessage()
//                .addInstantView("domik-mechtyi-ya-tozhe-hochu-zhit-v-takom")
//                .setText("Смотрите еще больше на сайте http://veselushki.top/");
//        bot.sendMessage(message);
//        return "ololo";
//    }
}
