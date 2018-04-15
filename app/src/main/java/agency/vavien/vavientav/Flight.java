package agency.vavien.vavientav;

import org.json.JSONObject;

/**
 * Created by Sedat
 * on 14.04.2018.
 */

public class Flight {
    private String airlineName, airlineIATACode, airlineICAOCode, registrationNo, subTypeICAOCode, gateResource, standResource;
    private String originIATACode, originICAOCode, originCity, originCountry, originRegion;
    private String destinationIATACode, destinationICAOCode, destinationCity, destinationCountry, destinationRegion;
    private int flightNumber, subTypeIATACode, capacity;
    private char flightCategory, leg;

    private String startDate, endDate;
    private String foodSuggest;

    Flight(JSONObject jsonObject) {
        this.airlineName = jsonObject.optString("airlineName", "");
        this.airlineIATACode = jsonObject.optString("airlineIATACode", "");
        this.airlineICAOCode = jsonObject.optString("airlineICAOCode", "");
        this.registrationNo = jsonObject.optString("registrationNo", "");
        this.subTypeICAOCode = jsonObject.optString("subTypeICAOCode", "");
        this.gateResource = jsonObject.optString("gateResource", "");
        this.standResource = jsonObject.optString("standResource", "");
        this.originIATACode = jsonObject.optString("originIATACode", "");
        this.originICAOCode = jsonObject.optString("originICAOCode", "");
        this.originCity = jsonObject.optString("originCity", "");
        this.originCountry = jsonObject.optString("originCountry", "");
        this.originRegion = jsonObject.optString("originRegion", "");
        this.destinationIATACode = jsonObject.optString("destinationIATACode", "");
        this.destinationICAOCode = jsonObject.optString("destinationICAOCode", "");
        this.destinationCity = jsonObject.optString("destinationCity", "");
        this.destinationCountry = jsonObject.optString("destinationCountry", "");
        this.destinationRegion = jsonObject.optString("destinationRegion", "");


        this.flightNumber = jsonObject.optInt("flightNumber", -1);
        this.subTypeIATACode = jsonObject.optInt("subTypeIATACode", -1);
        this.capacity = jsonObject.optInt("capacity", -1);


        this.flightCategory = jsonObject.optString("flightCategory", "x").charAt(0);
        this.leg = jsonObject.optString("leg", "x").charAt(0);

        this.startDate = startDate;
        this.endDate = endDate;
    }

    void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    void setFoodSuggest(String foodSuggest) {
        this.foodSuggest = foodSuggest;
    }

    String getFoodSuggest() {
        return foodSuggest;
    }

    String getAirlineName() {
        return airlineName;
    }

    String getAirlineIATACode() {
        return airlineIATACode;
    }

    String getAirlineICAOCode() {
        return airlineICAOCode;
    }

    String getRegistrationNo() {
        return registrationNo;
    }

    String getSubTypeICAOCode() {
        return subTypeICAOCode;
    }

    String getGateResource() {
        return gateResource;
    }

    String getStandResource() {
        return standResource;
    }

    String getOriginIATACode() {
        return originIATACode;
    }

    String getOriginICAOCode() {
        return originICAOCode;
    }

    String getOriginCity() {
        return originCity;
    }

    String getOriginCountry() {
        return originCountry;
    }

    String getOriginRegion() {
        return originRegion;
    }

    String getDestinationIATACode() {
        return destinationIATACode;
    }

    String getDestinationICAOCode() {
        return destinationICAOCode;
    }

    String getDestinationCity() {
        return destinationCity;
    }

    String getDestinationCountry() {
        return destinationCountry;
    }

    String getDestinationRegion() {
        return destinationRegion;
    }

    int getFlightNumber() {
        return flightNumber;
    }

    int getSubTypeIATACode() {
        return subTypeIATACode;
    }

    int getCapacity() {
        return capacity;
    }

    char getFlightCategory() {
        return flightCategory;
    }

    char getLeg() {
        return leg;
    }

    String getStartDate() {
        return startDate;
    }

    String getEndDate() {
        return endDate;
    }

}
