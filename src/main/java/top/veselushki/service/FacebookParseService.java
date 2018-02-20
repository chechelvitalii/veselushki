package top.veselushki.service;

import com.google.common.net.UrlEscapers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.veselushki.dto.FacebookPost;

import java.io.IOException;
import java.util.List;

import static javafx.beans.binding.Bindings.select;

@Service
public class FacebookParseService {
    private String facebookUrl;

    public FacebookParseService(@Value("${facebook.url}") String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public FacebookPost getPublishedPosts(List<String> pageName) throws IOException {
        Element page = Jsoup.connect(facebookUrl).get().body();
        Elements all_A_Tags = page.select("a");
        UrlEscapers.urlFragmentEscaper().escape("http://veselushki.top/mamyi-vnimanie-posle-kakogo-muzhskogo-vozrasta-opasno-imet-detey/");

        return null;
    }


}
