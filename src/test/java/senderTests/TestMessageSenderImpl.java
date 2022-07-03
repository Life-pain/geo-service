package senderTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;


class TestMessageSenderImpl {
    @ParameterizedTest
    @MethodSource
    void testSend_RussianCountry(Location locationStream) {
        GeoService geoService = mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.any(String.class)))
                .thenReturn(locationStream);

        LocalizationService localizationService = mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        String result = messageSender.send(new HashMap<>());
        String expected = "Добро пожаловать";
        Assertions.assertEquals(expected, result);
    }

    static Stream<Location> testSend_RussianCountry() {
        return Stream.of(
                new Location("Moscow", Country.RUSSIA, "Lenina", 15),
                new Location("Moscow", Country.RUSSIA, null, 0),
                new Location(null, Country.RUSSIA, null, 0)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testSend_notRussianCountry(Location location, Country country) {
        GeoService geoService = mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.any(String.class)))
                .thenReturn(location);

        LocalizationService localizationService = mock(LocalizationService.class);
        Mockito.when(localizationService.locale(country))
                .thenReturn("Welcome");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        String result = messageSender.send(new HashMap<>());
        String expected = "Welcome";
        Assertions.assertEquals(expected, result);
    }

    static Stream<Arguments> testSend_notRussianCountry() {
        return Stream.of(
                Arguments.arguments(new Location(null, Country.USA, null, 0), Country.USA),
                Arguments.arguments(new Location("New York", Country.USA, " 10th Avenue", 32), Country.USA),
                Arguments.arguments(new Location(null, Country.GERMANY, null, 0), Country.GERMANY),
                Arguments.arguments(new Location(null, Country.BRAZIL, null, 0), Country.BRAZIL)
        );
    }

}