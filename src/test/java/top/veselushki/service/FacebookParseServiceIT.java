package top.veselushki.service;

import mockit.MockUp;
import org.apache.commons.io.IOUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import top.veselushki.dto.FacebookPost;

import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.CharEncoding.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FacebookParseServiceIT {
    private FacebookParseService sut = new FacebookParseService("https://facebook.com/veselushki.top/");
    private static Connection connection = mock(Connection.class);

    @Test
    public void getPublishedPosts() throws Exception {
        //GIVEN
        mockJsoupConnection();
        String facebookPageHtml = IOUtils.toString(getClass().getResourceAsStream("/facebookPage.html"), UTF_8);
        Document page = Jsoup.parse(facebookPageHtml);
        when(connection.get()).thenReturn(page);
        List<String> topicLinks = asList("http://veselushki.top/samyie-dorogostoyashhie-krutyie-i-umnyie-avtomobili-2017-goda/",
                "http://veselushki.top/10-bezumnyih-foto-lyudey-kotoryim-sovershenno-plevat-na-obshhestvennoe-mnenie/",
                "http://veselushki.top/yaponskiy-hudozhnik-risuet-chuvstva-kotoryie-hot-raz-ispyityival-kazhdyiy-no-ne-mog-opisat-slovami/",
                "http://veselushki.top/skazochnyie-makro-foto-hudozhnika-vyacheslava-mishhenko/",
                "http://veselushki.top/esli-vyi-uvideli-monetu-na-dveri-avto-deystvuyte-nemedlenno/",
                "http://veselushki.top/mandala-udachi/");
        //WHEN
        List<FacebookPost> publishedPosts = sut.getPublishedPosts(topicLinks);
        //THEN
        assertThat(publishedPosts, hasSize(2));
        FacebookPost facebookPost1 = publishedPosts.get(0);
        assertThat(facebookPost1.getLabel(), is("Это очень важно!!!"));
        assertThat(facebookPost1.getTopicLink(), is("http://veselushki.top/esli-vyi-uvideli-monetu-na-dveri-avto-deystvuyte-nemedlenno/"));
        FacebookPost facebookPost2 = publishedPosts.get(1);
        assertThat(facebookPost2.getLabel(), is("Прекрасная мандала удичи"));
        assertThat(facebookPost2.getTopicLink(), is("http://veselushki.top/mandala-udachi/"));
    }

    private static void mockJsoupConnection() {
        new MockUp<Jsoup>() {
            @mockit.Mock
            public Connection connect(String url) {
                return connection;
            }
        };
    }
}