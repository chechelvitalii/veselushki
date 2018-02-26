package top.veselushki.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.veselushki.dto.FacebookPost;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class FacebookParseService {
    private String facebookUrl;

    public FacebookParseService(@Value("${facebook.url}") String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public List<FacebookPost> getPublishedPosts(List<String> topicLinks) throws IOException {
        List<FacebookPost> publishedPosts = new ArrayList<>();
        List<String> escapedTopicLinks = escapeLinks(topicLinks);
        Elements allLinksOnFacebookPage = getAllLinks();
        for (String escapedTopicLink : escapedTopicLinks) {
            Optional<Element> elementWithLinkToSite = allLinksOnFacebookPage.stream()
                    .filter(a -> a.attr("href").contains(escapedTopicLink))
                    .filter(element -> element instanceof Element)
                    .map(Element.class::cast)
                    .findFirst();
            if (elementWithLinkToSite.isPresent()) {
                String label = getLabel(elementWithLinkToSite.get());
                publishedPosts.add(new FacebookPost(escapedTopicLink, label));
            }
        }

        return publishedPosts;
    }

    private List<String> escapeLinks(List<String> links) {
        return links.stream()
                .map(URLEncoder::encode)
                .collect(toList());
    }

    private Elements getAllLinks() throws IOException {
        Element page = Jsoup.connect(facebookUrl).get().body();
        return page.select("a");
    }

    private String getLabel(Element elementWithLinkToSite) {
        return elementWithLinkToSite.parent().parent().parent().parent().parent().parent().parent().parent().parent()
                .child(1)
                .child(0).text();
    }

}
