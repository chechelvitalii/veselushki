package top.veselushki.service;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
public class VeselushkiService {
    private static final int FIRST_PAGE = 1;

    private final String veseluskiSite;
    private final int countPage;

    public VeselushkiService(@Value("${site.veselushki}") String veseluskiSite,
                             @Value("${count.page}") int countPage) {
        this.veseluskiSite = veseluskiSite;
        this.countPage = countPage;
    }

    public List<String> getLastTopicsName() throws IOException {
        List<String> lastTopicsName = new ArrayList<>();
        for (int i = FIRST_PAGE; i <= countPage; i++) {
            String page = "page/" + i;
            Elements links = Jsoup.connect(veseluskiSite + page).get().body()
                    .select("article a");
            List<String> topicsName = links.stream()
                    .map(element -> element.attr("href"))
                    .filter(url -> url.startsWith(veseluskiSite))
                    .map(this::getPageName)
                    .collect(toList());
            lastTopicsName.addAll(topicsName);
        }
        return lastTopicsName;
    }

    private String getPageName(String pageUrl) {
        return pageUrl.replace(veseluskiSite, EMPTY).replace("/", EMPTY);
    }
}
