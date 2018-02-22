package top.veselushki.service;

import mockit.MockUp;
import org.apache.commons.io.IOUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.apache.commons.lang3.CharEncoding.UTF_8;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SiteParseServiceIT {

    private SiteParseService sut = new SiteParseService("http://veselushki.top/page/", 1, 1);
    private static Connection connection = mock(Connection.class);

    @Test
    public void shouldReturnLastTopicLinks() throws IOException {
        //GIVEN
        mockJsoupConnection();
        String firstPageHtml = IOUtils.toString(getClass().getResourceAsStream("/siteFirstPage.html"), UTF_8);
        Document firstPage = Jsoup.parse(firstPageHtml);
        when(connection.get()).thenReturn(firstPage);
        //WHEN
        List<String> lastTopicsName = sut.getLastTopicLinks();
        //THEN
        assertThat(lastTopicsName, hasSize(6));
        assertThat(lastTopicsName,
                hasItems("http://veselushki.top/samyie-dorogostoyashhie-krutyie-i-umnyie-avtomobili-2017-goda/",
                        "http://veselushki.top/10-bezumnyih-foto-lyudey-kotoryim-sovershenno-plevat-na-obshhestvennoe-mnenie/",
                        "http://veselushki.top/yaponskiy-hudozhnik-risuet-chuvstva-kotoryie-hot-raz-ispyityival-kazhdyiy-no-ne-mog-opisat-slovami/",
                        "http://veselushki.top/skazochnyie-makro-foto-hudozhnika-vyacheslava-mishhenko/",
                        "http://veselushki.top/esli-vyi-uvideli-monetu-na-dveri-avto-deystvuyte-nemedlenno/",
                        "http://veselushki.top/mandala-udachi/"));
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