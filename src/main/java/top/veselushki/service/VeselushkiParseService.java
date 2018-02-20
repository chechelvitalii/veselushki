package top.veselushki.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Slf4j
@Service
public class VeselushkiParseService {

    private final String veseluskiSitePage;
    private final int pageFrom;
    private final int pageTo;

    public VeselushkiParseService(@Value("${site.veselushki.page}") String veseluskiSitePage,
                                  @Value("${page.from}")int pageFrom,
                                  @Value("${page.to}") int pageTo) {
        this.veseluskiSitePage = veseluskiSitePage;
        this.pageFrom = pageFrom;
        this.pageTo = pageTo;
    }

    public List<String> getLastTopicsName() throws IOException {
        List<String> lastTopicsName = new ArrayList<>();
        for (int i = pageFrom; i <= pageTo; i++) {
            Element body = Jsoup.connect(veseluskiSitePage + i).get().body();
            Elements links = getArticleLinks(body);
            List<String> topicsName = links.stream()
                    .map(element -> element.attr("href"))
                    .filter(url -> !url.startsWith(veseluskiSitePage))
                    .map(this::getPageName)
                    .collect(toList());
            lastTopicsName.addAll(topicsName);
        }
        return lastTopicsName;
    }

    private Elements getArticleLinks(Element body) {
        return body.select("article a");
    }

    private String getPageName(String pageUrl) {
        String[] split = pageUrl.split("/");
        if (split.length == 4) {
            return split[split.length - 1];
        } else {
            log.warn("Wrong url happened: " + pageUrl);
            return EMPTY;
        }
    }
}
