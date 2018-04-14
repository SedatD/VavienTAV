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

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setFoodSuggest(String foodSuggest) {
        this.foodSuggest = foodSuggest;
    }

    public String getFoodSuggest() {
        return foodSuggest;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public String getAirlineIATACode() {
        return airlineIATACode;
    }

    public String getAirlineICAOCode() {
        return airlineICAOCode;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public String getSubTypeICAOCode() {
        return subTypeICAOCode;
    }

    public String getGateResource() {
        return gateResource;
    }

    public String getStandResource() {
        return standResource;
    }

    public String getOriginIATACode() {
        return originIATACode;
    }

    public String getOriginICAOCode() {
        return originICAOCode;
    }

    public String getOriginCity() {
        return originCity;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public String getOriginRegion() {
        return originRegion;
    }

    public String getDestinationIATACode() {
        return destinationIATACode;
    }

    public String getDestinationICAOCode() {
        return destinationICAOCode;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public String getDestinationRegion() {
        return destinationRegion;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public int getSubTypeIATACode() {
        return subTypeIATACode;
    }

    public int getCapacity() {
        return capacity;
    }

    public char getFlightCategory() {
        return flightCategory;
    }

    public char getLeg() {
        return leg;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

}
