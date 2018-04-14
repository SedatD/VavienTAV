package agency.vavien.vavientav;

/**
 * Created by Sedat
 * on 14.04.2018.
 */

public class Airport {
    private int intensity = -1, totalArrivalCount = 0, totalDepartureCount = 0;

    public Airport() {
    }

    public int getIntensity() {
        return intensity;
    }

    public int getTotalArrivalCount() {
        return totalArrivalCount;
    }

    public int getTotalDepartureCount() {
        return totalDepartureCount;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public void setTotalArrivalCount(int totalArrivalCount) {
        this.totalArrivalCount = totalArrivalCount;
    }

    public void setTotalDepartureCount(int totalDepartureCount) {
        this.totalDepartureCount = totalDepartureCount;
    }

}
