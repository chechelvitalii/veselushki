package top.veselushki.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

import mockit.MockUp;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VeselushkiServiceIT {

    private VeselushkiService sut = new VeselushkiService("Test",5);
    private static Connection connection = mock(Connection.class);

    @Test
    public void shouldReturnLastTopicsName() throws IOException {
        //GIVEN
        mockJsoupConnection();
        when(connection.get()).thenReturn(new Document("/veselushkiFirstPage.html"));
        //WHEN
        sut.getLastTopicsName();
        //THEN
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