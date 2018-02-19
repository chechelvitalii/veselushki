package top.veselushki.service;

import mockit.MockUp;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;

public class VeselushkiServiceIT {

    private VeselushkiService sut = mock(VeselushkiService.class);

    @Test
    public void shouldReturnLastTopicsName() throws IOException {
        //GIVEN
        new MockUp<Jsoup>() {
        Jsoup.connect()
        };
        //WHEN
        sut.getLastTopicsName();
        //THEN
    }
}