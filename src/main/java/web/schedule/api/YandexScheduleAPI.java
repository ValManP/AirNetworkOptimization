package web.schedule.api;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import web.schedule.api.constants.AirportCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class YandexScheduleAPI {
    private static String urlStart = "https://api.rasp.yandex.net/v3.0/search/?";
    private static String apikey = "apikey=46f316a6-fc3f-4aa9-91a0-ec7acb3c04a6";
    private static String typePlane = "&transport_types=plane&system=iata";
    private static String dateTag = "&date=";
    private static String fromTag = "&from=";
    private static String toTag = "&to=";

    public int getCountOfFlight(AirportCode from, AirportCode to, String companyCode, Date date) {
        int count = 0;

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            JSONObject searchResult = getSchedule(from.getValue(), to.getValue(), df.format(date));

            JSONObject pagination = searchResult.getJSONObject("pagination");
            int total = pagination.getInt("total");

            JSONArray segments = searchResult.getJSONArray("segments");
            for (int i = 0; i < total; i++) {
                JSONObject segment = segments.getJSONObject(i);
                JSONObject thread = segment.getJSONObject("thread");
                String carrierCode = thread.getJSONObject("carrier").getJSONObject("codes").getString("iata");
                if (StringUtils.equalsIgnoreCase(carrierCode, companyCode)) {
                    count++;
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return count;
    }

    private JSONObject getSchedule(String from, String to, String date) {
        try {
            URL url = new URL(compileUrl(from, to, date));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + connection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            StringBuilder data = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                data.append(output);
            }

            JSONObject jsonObject = new JSONObject(data.toString());
            connection.disconnect();

            return jsonObject;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String compileUrl(String from, String to, String date) {
        StringBuilder url = new StringBuilder();
        url.append(urlStart)
                .append(apikey)
                .append(typePlane)
                .append(dateTag)
                .append(date)
                .append(fromTag)
                .append(from)
                .append(toTag)
                .append(to);
        return url.toString();
    }
}
