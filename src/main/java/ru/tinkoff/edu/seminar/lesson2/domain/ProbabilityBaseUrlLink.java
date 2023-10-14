package ru.tinkoff.edu.seminar.lesson2.domain;

import java.util.*;

public class ProbabilityBaseUrlLink extends AbstractLink{
    public ProbabilityBaseUrlLink(Map<String, Integer> fullUrlToProbabilityMap,
                                  String shortUrl) {
        fullUrlCollection = fullUrlToProbabilityMap.keySet().toArray(new String[0]);
        int totalPercents = fullUrlToProbabilityMap.values()
                .stream()
                .mapToInt(x -> x)
                .sum();
        int i = 0;
        probability = new int[fullUrlCollection.length];
        for (String key : fullUrlCollection) {
            if(i > 0)
                probability[i] = probability[i-1] + fullUrlToProbabilityMap.get(key)*100/totalPercents;
            else
                probability[i] = fullUrlToProbabilityMap.get(key)*100/totalPercents;
            i++;
        }
        this.shortUrl = shortUrl;
    }

    private final String[] fullUrlCollection;
    private final int[] probability;
    private final String shortUrl;

    private final Random rnd = new Random();
    @Override
    public String getFullUrl() {
        double d = rnd.nextDouble() * 100;
        int i = 0;
        do {
            if(d > probability[i])
                i++;
            else break;
        } while (i < probability.length - 1);
        return fullUrlCollection[i];
    }

    public List<String> getFullUrlCollection() {
        return Arrays.asList(fullUrlCollection);
    }

    @Override
    public String getShortUrl() {
        return shortUrl;
    }
}
