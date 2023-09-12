package wine.data;

import java.util.Map;

/**
 * Wine Ratings
 * JS - James Suckling
 * WE - Wine Enthusiast
 * WA - Wine Advocate
 * WS - Wine Spectator
 * JD - Jeb Dunnuck
 * IWR - International Wine Report
 * LM - Luca Moroni
 * VI - Vinous
 */
public class WineDetails {
    private String wineId;
    private String name;
    private String region;
    private String description;
    private String winery;
    private float price;
    private String country;
    private String province;
    private String variety;

    private Map<String, Integer> ratings;

    public WineDetails(String wineId, String name, String region, float price, Map<String,Integer> ratings) {
        this.wineId = wineId;
        this.name = name;
        this.region = region;
        this.price = price;
        this.ratings = ratings;
    }

    public String getWineId() {
        return wineId;
    }

    public void setWineId(String wineId) {
        this.wineId = wineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Map<String, Integer> getRatings() {
        return ratings;
    }

    public void setRatings(Map<String, Integer> ratings) {
        this.ratings = ratings;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWinery() {
        return winery;
    }

    public void setWinery(String winery) {
        this.winery = winery;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return "WineDetails{" +
                "wineId='" + wineId + '\'' +
                ", name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", winery='" + winery + '\'' +
                ", variety='" + variety + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", description='" + description + '\'' +
                ", ratings=" + ratings +
                '}';
    }
}
