package wine.processors;

import java.util.*;

/**
 * A set of utility methods to aid in processing the Wine File to get it into the
 * format needed for other usages and to fit the needed pattern for the WineDetails class
 */
public class WineFileProcessor {
    // These are a set of codes representing wine tasters (ex JS = James Suckling, WE = Wine Enthusiast)
    private static final List<String> ratingCodes = new ArrayList<>(Arrays.asList("JS", "WE", "WA", "WS", "JD", "IWR", "LM", "VI"));

    /**
     * Generate an artificial (generated) list of ratings based on a single
     * core rating. The algorithm calculates a series of random numbers all
     * deviating around the supplied rating. It also obtains a random rating
     * code representing a set of tasting agencies. It then builds a map
     * of these codes and associated generated rating.
     *
     * @param initialRating Initial rating value read from the wine entry
     * @return A map containing rating codes and associated ratings
     */
    public static Map<String, Integer> generateRatings(Long initialRating) {
        Map<String, Integer> stringMap = new HashMap<>();
        int baseRating = 85;
        if (initialRating != null)
            baseRating = initialRating.intValue();

        for (int rating : generateRandomRatingScores(baseRating)) {
            stringMap.put(getRandomTaster(), rating);
        }
        return stringMap;

    }

    /**
     * Given a provided rating code, generate a series of random ratings (between
     * 2 and 4) all of which deviate a maximum of 4 points from the original.
     *
     * @param baseRating Initial rating value read from the wine entry
     * @return An array of randomly generating ratings
     */
    public static int[] generateRandomRatingScores(int baseRating) {
        Random random = new Random();
        int numberOfNumbers = random.nextInt(3) + 2; // Generates 2, 3, or 4

        int[] numbers = new int[numberOfNumbers];

        for (int i = 0; i < numberOfNumbers; i++) {
            int deviation = random.nextInt(9) - 4; // Generates a deviation between -4 and 4
            int generatedNumber = baseRating + deviation;
            // Ensure that the score does not exceed 99
            while (generatedNumber > 99)
                generatedNumber--;

            numbers[i] = generatedNumber;
        }
        return numbers;
    }

    /**
     * Randomly produce a code from the initial list of codes. It simply picks a
     * random number between 0 and the number of entries in the list and pulls that
     * entry
     *
     * @return A randomly selected code from the list of codes.
     */
    public static String getRandomTaster() {
        int randomIndex = new Random().nextInt(ratingCodes.size());
        return ratingCodes.get(randomIndex);
    }
}
