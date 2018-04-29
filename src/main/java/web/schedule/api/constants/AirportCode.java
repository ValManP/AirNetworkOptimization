package web.schedule.api.constants;

public enum AirportCode {
    BERLIN("BER"),
    BUCHAREST("BUH"),
    LONDON("LON"),
    MILAN("MIL"),
    MOSCOW("MOW"),
    PARIS("PAR"),
    ROME("ROM"),
    STOCKHOLM("STO"),
    DUBAI("DXB"),
    FRANKFURT("FRA"),
    AMSTERDAM("AMS"),
    MUNICH("MUC"),
    BARCELONA("BCN"),
    MINSK("MSQ"),
    WARSAW("WAW"),
    ANKARA("ESB"),
    GENEVA("GVA"),
    MADRID("MAD"),
    SOCHI("AER"),
    SAINT_PETERSBURG("LED");

    private String value;

    AirportCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
