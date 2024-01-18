package com.example.phuotogether.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Manual {
    private String title;
    private String content;
    private List<String> imageUrls;
    // Add other necessary fields for styles, etc.

    // Constructors
    public Manual(String content) {
        this.title = extractTitleFromContent(content);
        this.content = content;
        this.imageUrls = new ArrayList<>();
    }

    public Manual(String title, String content, List<String> imageUrls) {
        this.title = title;
        this.content = content;
        this.imageUrls = imageUrls;
    }

    private String extractTitleFromContent(String content) {
        String h1Pattern = "<h1>(.*?)</h1>";
        Pattern pattern = Pattern.compile(h1Pattern);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String h1Text = matcher.group(1);
            return h1Text;
        }
        return "";
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    // Add other getters and setters as needed for additional fields

    // You can also override toString() method for better readability during debugging
    @Override
    public String toString() {
        return "Manual{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imageUrls=" + imageUrls +
                // Add other fields to the toString() if necessary
                '}';
    }
}
