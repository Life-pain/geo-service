package senderTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;

import static org.mockito.Mockito.mock;


class TestMessageSenderImpl{
    @Test
    void testSend_RussianCountry(){
        GeoService geoService = mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.any(String.class)))
                .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));

        LocalizationService localizationService = new LocalizationServiceImpl();

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        String result = messageSender.send(new HashMap<>());
        String expected ="Добро пожаловать";
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testSend_notRussianCountry(){
        GeoService geoService = mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.any(String.class)))
                .thenReturn(new Location("Moscow", Country.USA, "Lenina", 15));

        LocalizationService localizationService = new LocalizationServiceImpl();

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        String expected ="Welcome";
        //test with Country.USA
        Assertions.assertEquals(expected, messageSender.send(new HashMap<>()));
        //test with Country.Brazil
        Mockito.when(geoService.byIp(Mockito.any(String.class)))
                .thenReturn(new Location("Moscow", Country.BRAZIL, "Lenina", 15));
        Assertions.assertEquals(expected, messageSender.send(new HashMap<>()));
        //test with Country.Germany
        Mockito.when(geoService.byIp(Mockito.any(String.class)))
                .thenReturn(new Location("Moscow", Country.GERMANY, "Lenina", 15));
        Assertions.assertEquals(expected, messageSender.send(new HashMap<>()));
    }

}