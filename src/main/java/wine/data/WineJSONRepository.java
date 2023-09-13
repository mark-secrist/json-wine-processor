package wine.data;

import com.github.cliftonlabs.json_simple.*;
import wine.exceptions.WineAppException;
import wine.processors.WineFileProcessor;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WineJSONRepository {
    private static final String WINE_READ_FILE="src/main/resources/wine-data-set-orig.json";
    private static final String WINE_SAVE_FILE = "src/main/resources/wine-data-set.json";

    private static final List<String> ratingCodes = new ArrayList<>(Arrays.asList("JS", "WE", "WA", "WS", "JD", "IWR", "LM", "VI"));

    private List<WineDetails> wineDetailsList = null;

    /**
     * Construct a WineJSONRepository instance. To be successful, instantiating this
     * class presumes that the path to the file will be found and can be opend
     *
     * @throws WineAppException If the file is not found
     */
    public WineJSONRepository() {
        File testFile = new File(WINE_READ_FILE);
        if (! testFile.exists() )
            throw new WineAppException("File does not exist: " + WINE_READ_FILE);

    }
    public List<WineDetails> getWines() {
        if (wineDetailsList == null)
            loadFromFile();
        return wineDetailsList;
    }

    /**
     * Attempt to read the file containing JSON objects and convert
     * into a list of WineDetails
     */
    public void loadFromFile() {
        try (FileReader reader = new FileReader(WINE_READ_FILE); )
        {
            Object obj = Jsoner.deserialize(reader);
            //Object obj = parser.parse(reader);
            if (obj instanceof JsonArray) {
                JsonArray jsonEntries = (JsonArray) obj;

                // In this case, the JSONArray object's stream() method is defined
                // on the underlying Collection interface. As such, it is necessary
                // to cast the result of calling stream() to a Stream<JSONObject>
                wineDetailsList =  jsonEntries.stream()
                        .map(this::toWineDetails)
                        //.limit(10)
                        .collect(Collectors.toList());
            }
        } catch (IOException | JsonException ex) {
            throw new WineAppException("An exception was caught while processing the Wine file: " + ex);
        }
    }

    /**
     * Save the List of WineDetails objects to a file
     */
    public void saveToFile() {
         JsonArray jsonArray = new JsonArray();
        try (FileWriter writer = new FileWriter(WINE_SAVE_FILE)) {
            for (WineDetails item : wineDetailsList) {
                JsonObject json = fromWineDetails(item);
                jsonArray.add(json);
            }
            Jsoner.serialize(jsonArray, writer);
            writer.flush();
        } catch (IOException ex) {
            throw new WineAppException("An exception was caught while writing to the Wine file: " + ex);
        }
    }

    /**
     * Convert a WineDetails object to the associated JsonObject
     *
     * @param item A WineDetails object to convert
     * @return JsonObject that has all the values assigned
     */
    private JsonObject fromWineDetails(WineDetails item) {
        JsonObject obj = new JsonObject();
        obj.put("wineId", item.getWineId());
        obj.put("title", item.getName());
        obj.put("region", item.getRegion());
        obj.put("province", item.getProvince());
        obj.put("country", item.getCountry());
        obj.put("winery", item.getWinery());
        obj.put("description", item.getDescription());
        obj.put("variety", item.getVariety());
        obj.put("price", item.getPrice());
        obj.put("ratings", new JsonObject(item.getRatings()));
        return obj;
    }

    /**
     * Build a WineDetails object from the JSONObject data read from the file
     *
     * @param obj An Object (JsonObject) read from the file
     * @return A WineDetails instance populated from data in the JSONObject
     */
    private WineDetails toWineDetails(Object obj) {
        JsonObject jsonObject = (JsonObject) obj;
        UUID uuid = UUID.randomUUID();

        // Extract the price, which can be empty and convert to Float
        Float floatPrice =  50f;
        //Long price = (Long) jsonObject.get("price");
        Double doublePrice = jsonObject.getDouble(Jsoner.mintJsonKey("price", 50f));
        if (doublePrice != null)
            floatPrice = doublePrice.floatValue();

        // Generate ratings map from a single value - points
        Long rating = jsonObject.getLong(Jsoner.mintJsonKey("points", 85));
        Map<String, Integer>  ratings = WineFileProcessor.generateRatings(rating);

        // Build WineDetails object and populate it
        WineDetails newWine = new WineDetails(uuid.toString(), (String) jsonObject.get("title"),
                                            (String) jsonObject.get("region_1"), floatPrice, ratings);
        newWine.setProvince((String) jsonObject.get("province"));
        newWine.setCountry((String) jsonObject.get("country"));
        newWine.setWinery((String) jsonObject.get("winery"));
        newWine.setDescription((String) jsonObject.get("description"));
        newWine.setVariety((String) jsonObject.get("variety"));
        return newWine;
    }

}
