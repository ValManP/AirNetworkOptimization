package web.schedule.api.constants;

public enum AirportCode {
    BERLIN("BER"),
    BUCHAREST("BUH"),
    LONDON("LON"),
    MILAN("MIL"),
    MOSCOW("MOW"),
    PARIS("PAR"),
    ROME("ROM"),
    STOCKHOLM("STO");

    private String value;

    AirportCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
