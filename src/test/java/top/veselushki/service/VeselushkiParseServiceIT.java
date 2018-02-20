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

public class VeselushkiParseServiceIT {

    private VeselushkiParseService sut = new VeselushkiParseService("http://veselushki.top/page/", 1, 1);
    private static Connection connection = mock(Connection.class);

    @Test
    public void shouldReturnLastTopicsName() throws IOException {
        //GIVEN
        mockJsoupConnection();
        String firstPageHtml = IOUtils.toString(getClass().getResourceAsStream("/veselushkiFirstPage.html"), UTF_8);
        Document firstPage = Jsoup.parse(firstPageHtml);
        when(connection.get()).thenReturn(firstPage);
        //WHEN
        List<String> lastTopicsName = sut.getLastTopicsName();
        //THEN
        assertThat(lastTopicsName, hasSize(6));
        assertThat(lastTopicsName,
                hasItems("vot-kak-v-deystvitelnosti-vyiglyadyat-snezhinki-oni-hotyat-nam-chto-to-skazat",
                        "kak-uhazhivat-za-zhenskim-schastem-chtobyi-ono-regulyarno-pyishno-tsvelo",
                        "pochemu-vazhno-vsegda-ostavlyat-monetu-v-morozilke-pered-tem-kak-vyi-uhodite-iz-domu-vyi-dolzhnyi-eto-znat",
                        "vot-tak-seychas-vyiglyadit-devochka-kotoruyu-roditeli-v-2-goda-sdelali-modelyu",
                        "vdohnul-v-mototsikl-1960-goda-novuyu-zhizn-budto-tolko-s-konveyera-soshyol",
                        "babushka-na-odesskom-plyazhe-etot-nomer-prosto-vzorval-zal"));
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