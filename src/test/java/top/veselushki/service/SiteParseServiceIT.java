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
        String firstPageHtml = IOUtils.toString(getClass().getResourceAsStream("/veselushkiFirstPage.html"), UTF_8);
        Document firstPage = Jsoup.parse(firstPageHtml);
        when(connection.get()).thenReturn(firstPage);
        //WHEN
        List<String> lastTopicsName = sut.getLastTopicLinks();
        //THEN
        assertThat(lastTopicsName, hasSize(6));
        assertThat(lastTopicsName,
                hasItems("http://veselushki.top/vot-kak-v-deystvitelnosti-vyiglyadyat-snezhinki-oni-hotyat-nam-chto-to-skazat/",
                        "http://veselushki.top/kak-uhazhivat-za-zhenskim-schastem-chtobyi-ono-regulyarno-pyishno-tsvelo/",
                        "http://veselushki.top/pochemu-vazhno-vsegda-ostavlyat-monetu-v-morozilke-pered-tem-kak-vyi-uhodite-iz-domu-vyi-dolzhnyi-eto-znat/",
                        "http://veselushki.top/vot-tak-seychas-vyiglyadit-devochka-kotoruyu-roditeli-v-2-goda-sdelali-modelyu/",
                        "http://veselushki.top/vdohnul-v-mototsikl-1960-goda-novuyu-zhizn-budto-tolko-s-konveyera-soshyol/",
                        "http://veselushki.top/babushka-na-odesskom-plyazhe-etot-nomer-prosto-vzorval-zal/"));
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