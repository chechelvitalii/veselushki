package top.veselushki.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
public class SiteParseService {

    private final String siteUrlPage;
    private final int pageFrom;
    private final int pageTo;

    public SiteParseService(@Value("${site.url.page}") String siteUrlPage,
                            @Value("${page.from}")int pageFrom,
                            @Value("${page.to}") int pageTo) {
        this.siteUrlPage = siteUrlPage;
        this.pageFrom = pageFrom;
        this.pageTo = pageTo;
    }

    public List<String> getLastTopicLinks() {
        List<String> lastTopicsName = new ArrayList<>();
        for (int i = pageFrom; i <= pageTo; i++) {
            //TODO wrap into custom class
            Element body = null;
            try {
                body = Jsoup.connect(siteUrlPage + i).get().body();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Elements links = getArticleLinks(body);
            Predicate<String> linkWithoutTopic = url -> !url.startsWith(siteUrlPage);
            List<String> topicsName = links.stream()
                    .map(element -> element.attr("href"))
                    .filter(linkWithoutTopic)
                    .collect(toList());
            lastTopicsName.addAll(topicsName);
        }
        return lastTopicsName;
    }

    private Elements getArticleLinks(Element body) {
        return body.select("article a");
    }
}
