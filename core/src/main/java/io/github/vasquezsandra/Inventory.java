package io.github.vasquezsandra;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    //Creates map for the plant name, and how much of it is in the inventory
    private Map<String, Integer> collectedPlants;

    public Inventory() {
        collectedPlants = new HashMap<>();
    }

    /**
     * When this method is used, the plant and how much is in inventory is returned
     * @param plantName
     * @return Plant, Count
     */
    public int getPlantCount(String plantName) {
        return collectedPlants.getOrDefault(plantName.toLowerCase(), 0);
    }

    /**
     * When this method is used, the plant name is being added to the map, as well
     * as how much is being added to its inventory.
     * @param plantName
     * @param amount
     */
    public void collectPlant(String plantName, int amount) {
        collectedPlants.put(plantName.toLowerCase(), getPlantCount(plantName) + amount);
    }

}
