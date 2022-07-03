package i18nTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;
import java.util.stream.Stream;

public class TestLocalizationServiceImpl {
    LocalizationServiceImpl localizationService;

    @BeforeEach
    void  init_localizationService(){
        localizationService = new LocalizationServiceImpl();
    }

    @Test
    void test_locale(){
        String expected = "Добро пожаловать";

        String result = localizationService.locale(Country.RUSSIA);
        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource
    void test_locale_Welcome(Country countryEnum){
    String result = localizationService.locale(countryEnum);
    Assertions.assertEquals("Welcome", result);
    }

    static Stream<Country> test_locale_Welcome(){
        return Stream.of(Country.USA, Country.BRAZIL, Country.GERMANY);
    }
}
