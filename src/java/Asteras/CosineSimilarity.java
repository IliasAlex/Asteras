/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Asteras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ηλίας
 */
public class CosineSimilarity {

    private static class Values {

        private int val1;
        private int val2;

        private Values(int v1, int v2) {
            this.val1 = v1;
            this.val2 = v2;
        }

        public void updateValues(int v1, int v2) {
            this.val1 = v1;
            this.val2 = v2;
        }
    }

    public double score(String text1, String text2) {
        //1. Identify distinct words from both documents
        String[] text1Words = text1.split(" ");
        String[] text2Words = text2.split(" ");
        Map<String, Values> wordFreqVector = new HashMap<>();
        List<String> distinctWords = new ArrayList<>();

        //prepare word frequency vector by using Text1
        for (String text : text1Words) {
            String word = text.trim();
            if (!word.isEmpty()) {
                if (wordFreqVector.containsKey(word)) {
                    Values vals1 = wordFreqVector.get(word);
                    int freq1 = vals1.val1 + 1;
                    int freq2 = vals1.val2;
                    vals1.updateValues(freq1, freq2);
                    wordFreqVector.put(word, vals1);
                } else {
                    Values vals1 = new Values(1, 0);
                    wordFreqVector.put(word, vals1);
                    distinctWords.add(word);
                }
            }
        }

        //prepare word frequency vector by using Text2
        for (String text : text2Words) {
            String word = text.trim();
            if (!word.isEmpty()) {
                if (wordFreqVector.containsKey(word)) {
                    Values vals1 = wordFreqVector.get(word);
                    int freq1 = vals1.val1;
                    int freq2 = vals1.val2 + 1;
                    vals1.updateValues(freq1, freq2);
                    wordFreqVector.put(word, vals1);
                } else {
                    Values vals1 = new Values(0, 1);
                    wordFreqVector.put(word, vals1);
                    distinctWords.add(word);
                }
            }
        }

        //calculate the cosine similarity score.
        double vectAB = 0.0000000;
        double vectA = 0.0000000;
        double vectB = 0.0000000;
        for (int i = 0; i < distinctWords.size(); i++) {
            Values vals12 = wordFreqVector.get(distinctWords.get(i));
            double freq1 = vals12.val1;
            double freq2 = vals12.val2;
            vectAB = vectAB + freq1 * freq2;
            vectA = vectA + freq1 * freq1;
            vectB = vectB + freq2 * freq2;
        }
        return ((vectAB) / (Math.sqrt(vectA) * Math.sqrt(vectB)));
    }
}
