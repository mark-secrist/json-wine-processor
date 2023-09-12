import wine.data.WineDetails;
import wine.data.WineJSONRepository;

import java.util.List;

public class WineApp {

    public static void main(String[] args) {
        WineJSONRepository repo = new WineJSONRepository();
       List<WineDetails> wineDetailsList = repo.getWines();
        System.out.println("Read documents: " + wineDetailsList.size());
        repo.saveToFile();

    }

}
