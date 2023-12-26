package com.onetag.javadev;

import java.util.List;
import java.util.Objects;

/*
 * Create an array of paths of csv files
 * Pass this array to getExtractor
 * In ExtractorFactory open these files
 * */


public class AcademyAwardsApp {

    public static void main(String[] args) {
 
        Extractor awardsExtractor = ExtractorFactory.getExtractor();        
        
        if (Objects.nonNull(awardsExtractor)) {
            List<String> extract = awardsExtractor.extract();
            extract.forEach(System.out::println);
        }
    }
}
