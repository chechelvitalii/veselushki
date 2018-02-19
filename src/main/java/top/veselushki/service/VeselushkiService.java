package top.veselushki.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
@Slf4j
@Service
public class VeselushkiService {
    private static final int FIRST_PAGE = 1;

    private final String veseluskiSitePage;
    private final int countPage;

    public VeselushkiService(@Value("${site.veselushki.page}") String veseluskiSitePage,
                             @Value("${page.count}") int countPage) {
        this.veseluskiSitePage = veseluskiSitePage;
        this.countPage = countPage;
    }

    public List<String> getLastTopicsName() throws IOException {
        List<String> lastTopicsName = new ArrayList<>();
        for (int i = FIRST_PAGE; i <= countPage; i++) {
            Element body = Jsoup.connect(veseluskiSitePage + i).get().body();
            Elements links = getArticleLinks(body);
            List<String> topicsName = links.stream()
                    .map(element -> element.attr("href"))
                    .filter(url -> url.startsWith(veseluskiSitePage))
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
        return pageUrl.replace(veseluskiSitePage, EMPTY).replace("/", EMPTY);
    }
}
