package com.onetag.javadev;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Factory for Extractor generation
 */
public class ExtractorFactory {

    public static Extractor getExtractor() {
    	
    	return new Extractor() {
            @Override
            public List<String> extract() {
            	// Init the list of input files
            	List<String> filePaths = Arrays.asList("./src/main/resources/oscar_age_female.csv", "./src/main/resources/oscar_age_male.csv");
            	// Init a starMap
            	Map<String, Map<String, Integer>> starMap = new HashMap<>();
            	// read the files and add the data to starMap
            	filePaths.forEach(fp -> {
            		try (BufferedReader br = new BufferedReader(new FileReader(fp))) {
                        String line;
                        boolean headerSkipped = false;
                        
                        while ((line = br.readLine()) != null) {
                        	// Skip the header row
                        	if (!headerSkipped) {
                                headerSkipped = true;
                                continue;
                            }
                            
                            String[] values = line.split(","); // Split the line by comma
                            
                            if (values.length >= 5) {
                                String name = values[3];
                                int yearBirth = Integer.parseInt(values[1].trim()) - Integer.parseInt(values[2].trim());
                                
                                // check if name is already in starMap
                                // if yes, count++
                                if(starMap.containsKey(name)) {
                                	Map<String, Integer> innerMap = starMap.get(name);
                                	innerMap.put("count", innerMap.get("count") + 1);
                                	starMap.put(name, innerMap);
                                }
                                // if not add the new star to the map, with the year of birth and the count = 1
                                else {
                                	Map<String, Integer> innerMap = new HashMap<>();
                                	innerMap.put("yearBirth", yearBirth);
                                	innerMap.put("count", 1);
                                	starMap.put(name, innerMap);
                                }
                            }
                        }
                    
                    } catch (IOException | NumberFormatException e) {
                        e.printStackTrace();
                    }
                           		
            	});
            	
            	// filter out stars who only won ones
            	Map<String, Map<String, Integer>> filteredStarMap = starMap.entrySet().stream()
            		.filter(entry -> entry.getValue().get("count") > 1)
            		.collect(LinkedHashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);
            	// clear old map
            	starMap.clear();
                    
                // Create a custom comparator for sorting the main map
                Comparator<Map.Entry<String, Map<String, Integer>>> customComparator =
                    (star1, star2) -> {
                        int count1 = star1.getValue().get("count");
                        int count2 = star2.getValue().get("count");
                        
                        int yearBirth1 = star1.getValue().get("yearBirth");
                        int yearBirth2 = star2.getValue().get("yearBirth");

		                // Until the counts of prizes are different, sort by count in descending order
		                if (count1 != count2) {
		                    return Integer.compare(count2, count1); 
		                } else {
		                    // If counts are equal, compare year of birth in descending order (youngest first)
		                    return Integer.compare(yearBirth2, yearBirth1);
		                }
                };
                
                // Sort the main map using the custom comparator
                List<Map.Entry<String, Map<String, Integer>>> entries = new ArrayList<>(filteredStarMap.entrySet());
                entries.sort(customComparator);
                
                // the list to return is the list of the keys of the entries
                List<String> sortedStars = new ArrayList<>();
                entries.forEach(entry -> {
                	sortedStars.add(entry.getKey());
                });
                
                // clear entries
                entries.clear();
                
                return sortedStars;
            }
        };
    }
}
