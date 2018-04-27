package web.schedule.api;

import org.junit.Assert;
import org.junit.Test;
import web.schedule.api.constants.AirportCode;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class YandexScheduleAPITest {
    @Test
    public void testGetCountOfFlight() throws ParseException {
        YandexScheduleAPI api = new YandexScheduleAPI();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String companyCode = "U2";
        Date date = df.parse("2018-06-20");
        int count = api.getCountOfFlight(AirportCode.BERLIN, AirportCode.PARIS, companyCode, date);

        int expected = 3;
        Assert.assertEquals(expected, count);
    }
}