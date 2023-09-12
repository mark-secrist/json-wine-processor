package wine.tests;

import org.junit.jupiter.api.Test;
import wine.data.WineDetails;
import wine.data.WineRepository;
import wine.processors.WineListProcessor;
import wine.processors.WineToJSONProcessor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Lab01Tests {
    @Test
    public void shouldFormatAsJSON() {
        String expected = "[{\"WineName\": \"Caymus 1998 Cabernet Sauvignon (Napa Valley)\",\"Ratings\": [{\"WA\": 93 },{\"WE\": 91 },]},{\"WineName\": \"J.L. Chave 1999  Hermitage\",\"Ratings\": [{\"WS\": 94 },{\"WE\": 96 },]},{\"WineName\": \"E. Guigal 1998 La Mouline  (Côte Rôtie)\",\"Ratings\": [{\"JS\": 94 },{\"WS\": 88 },{\"WE\": 93 },]},{\"WineName\": \"Château Fonréaud 2006  Listrac-Médoc\",\"Ratings\": [{\"JS\": 90 },]},{\"WineName\": \"Brandborg 2009 Gewürztraminer (Umpqua Valley)\",\"Ratings\": [{\"WA\": 92 },{\"WE\": 90 },]},]";
        List<WineDetails> wineList = WineRepository.getWineDetailsList();
        StringBuffer wineListJSON = WineToJSONProcessor.toJSONWineList(wineList);
        assertEquals( expected, wineListJSON.toString(), "Failed to format JSON document");
    }

    @Test
    public void shouldSortList() {
        List<WineDetails> wineList = WineRepository.getWineDetailsList();
        WineListProcessor.sortWineListByAverageRating(wineList);
        assertEquals("456", wineList.get(0).getWineId(), "First element should be 'J.L. Chave 1999  Hermitage'");
        assertEquals("876", wineList.get(4).getWineId(), "Last element should be 'Château Fonréaud 2006  Listrac-Médoc'");
    }

    @Test
    public void shouldRemoveBelow91() {
        List<WineDetails> wineList = WineRepository.getWineDetailsList();
        List<WineDetails> trimmeList = WineListProcessor.removeLowRatings(wineList, 92);
        assertEquals(3, trimmeList.size(), "Wine List should only contain 3 entries");

    }
}
