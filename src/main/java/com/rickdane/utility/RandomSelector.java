package com.rickdane.utility;

import java.util.List;
import java.util.Random;

/**
 * @author Rick Dane
 */
public class RandomSelector {

    private static Random rand = new Random();

    public static int randomListIndex(List list) {

        int adjSize = list.size() - 1;
        int randomNum = rand.nextInt(adjSize - 0 + 1) + 0;
        return randomNum;
    }

    public static int generateRandomNumberInRange(int min, int max) {
        int randomNum = rand.nextInt(max - min + 1) + min;
        return randomNum;
    }

}
