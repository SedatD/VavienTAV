package agency.vavien.vavientav;

/**
 * Created by Sedat
 * on 14.04.2018.
 */

public class Airport {
    private int intensity = -1, totalArrivalCount = 0, totalDepartureCount = 0;

    Airport() {
    }

    public int getIntensity() {
        return intensity;
    }

    int getTotalArrivalCount() {
        return totalArrivalCount;
    }

    int getTotalDepartureCount() {
        return totalDepartureCount;
    }

    void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    void setTotalArrivalCount(int totalArrivalCount) {
        this.totalArrivalCount = totalArrivalCount;
    }

    void setTotalDepartureCount(int totalDepartureCount) {
        this.totalDepartureCount = totalDepartureCount;
    }

}
