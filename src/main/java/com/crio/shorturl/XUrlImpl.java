package com.crio.shorturl;

import java.util.HashMap;
import java.util.Map;

public class XUrlImpl implements XUrl {

    private Map<String, String> longToShortUrlMap;
    private Map<String, String> shortToLongUrlMap;
    private Map<String, Integer> hitCountMap;

    public XUrlImpl() {
        longToShortUrlMap = new HashMap<>();
        shortToLongUrlMap = new HashMap<>();
        hitCountMap = new HashMap<>();
    }

    @Override
    public String registerNewUrl(String longUrl) {
        // Check if the longUrl is already registered
        if (longToShortUrlMap.containsKey(longUrl)) {
            // Increment the hit count when the URL is looked up
            hitCountMap.put(longUrl, hitCountMap.getOrDefault(longUrl, 0) + 1);
            return longToShortUrlMap.get(longUrl);
        }

        // Generate a unique alphanumeric string for the short URL
        String shortUrl = generateShortUrl();

        // Register the mapping
        longToShortUrlMap.put(longUrl, shortUrl);
        shortToLongUrlMap.put(shortUrl, longUrl);
        int updatedHitCount = hitCountMap.getOrDefault(longUrl, 0) + 1; // Increment hit count
        hitCountMap.put(longUrl, updatedHitCount);

        System.out.println("Hit count after registration: " + updatedHitCount); // Updated hit count

        return shortUrl;
    }



    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        // Check if the shortUrl is already present
        if (shortToLongUrlMap.containsKey(shortUrl)) {
            return null; // Short URL already exists
        }

        // Register the mapping
        longToShortUrlMap.put(longUrl, shortUrl);
        shortToLongUrlMap.put(shortUrl, longUrl);

        return shortUrl;
    }

    @Override
    public String getUrl(String shortUrl) {
        return shortToLongUrlMap.get(shortUrl);
    }

    @Override
    public Integer getHitCount(String longUrl) {
        Integer hitCount = hitCountMap.getOrDefault(longUrl, 0);
        System.out.println("Hit count retrieved for " + longUrl + ": " + hitCount); // Add this line
        return hitCount;
    }

    @Override
    public String delete(String longUrl) {
        // Retrieve shortUrl and remove the mapping
        String shortUrl = longToShortUrlMap.remove(longUrl);
        if (shortUrl != null) {
            shortToLongUrlMap.remove(shortUrl);
            hitCountMap.remove(longUrl);
        }
        return shortUrl;
    }

    private String generateShortUrl() {
        // Implement logic to generate a unique alphanumeric string
        // For simplicity, you can use a random or sequential approach
        // Make sure the generated short URL is 9 characters in length
        // and does not contain any special characters
        // Example: http://short.url/abcdefghi
        return "http://short.url/" + generateRandomString(9);
    }

    private String generateRandomString(int length) {
        // Implement logic to generate a random alphanumeric string of the given length
        // For simplicity, you can use characters [a-zA-Z0-9]
        // This is just a placeholder, and you may need to implement a more robust solution
        StringBuilder randomString = new StringBuilder();
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            randomString.append(characters.charAt(index));
        }
        return randomString.toString();
    }
}
