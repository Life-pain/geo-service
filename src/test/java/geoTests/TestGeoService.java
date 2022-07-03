package geoTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

public class TestGeoService {
    //при условии добавления методов equals и hashcode в класс Location
    @ParameterizedTest
    @ValueSource(strings = {"172.", "172.1", "172.321", "172.12345.12476"})
    void test_byIp_172(String argument){
        GeoService geoService = new GeoServiceImpl();

        Location expected = new Location("Moscow", Country.RUSSIA, null, 0);

        Location result = geoService.byIp(argument);
        Assertions.assertEquals(expected, result);

    }

    @ParameterizedTest
    @ValueSource(strings = {"172", "12.1", "72.321", ".", ""})
    void test_byIp_null(String argument){
        GeoService geoService = new GeoServiceImpl();

        Location result = geoService.byIp(argument);
        Assertions.assertNull(result);

    }

    @ParameterizedTest
    @MethodSource
    void byIP_allCases(String ip, Location expected){
        GeoServiceImpl geoService = new GeoServiceImpl();
        Location result = geoService.byIp(ip);
        Assertions.assertEquals(expected, result);
    }

    static Stream<Arguments> byIP_allCases(){
        return Stream.of(
                Arguments.arguments("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.arguments("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.arguments("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.arguments("172.", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.arguments("96.", new Location("New York", Country.USA, null,  0))
        );
    }
}
