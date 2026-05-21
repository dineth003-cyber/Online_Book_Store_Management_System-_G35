package org.example.customer_app.service;

import java.io.*;
import java.util.*;

public class ReviewService {

    private static final String FILE = "reviews.txt";

    // ✅ CREATE
    public void addReview(String bookId, String user, int rating, String comment) {
        try (FileWriter fw = new FileWriter(FILE, true)) {
            fw.write(bookId + "," + user + "," + rating + "," + comment + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ READ
    public List<String> getReviews() {
        List<String> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
        }

        return list;
    }

    // ✅ DELETE
    public void deleteReview(String user) {
        List<String> updated = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.contains("," + user + ",")) {
                    updated.add(line);
                }
            }
        } catch (Exception e) {}

        try (FileWriter fw = new FileWriter(FILE)) {
            for (String s : updated) {
                fw.write(s + "\n");
            }
        } catch (Exception e) {}
    }
}