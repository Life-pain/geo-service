package geoTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

public class TestGeoService {
    @Test
    void test_byIp_Country(){
        GeoService geoService = new GeoServiceImpl();

        Enum<Country> expected = Country.RUSSIA;

        Country result = geoService.byIp("172.").getCountry();
        Assertions.assertEquals(expected, result);
    }


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

}
