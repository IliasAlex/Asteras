/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Asteras;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

/**
 *
 * @author Ηλίας
 */
public class LuceneTester {

    String indexDir = "Index";
    String dataDir = "Data";
    Indexer indexer;
    Searcher searcher;

    long time;
    int k;

    HashMap<String, Author> matches = new HashMap<>();

    public void run(String query, String k) throws IOException, ParseException {
        this.k = Integer.parseInt(k);
        createIndex(indexDir, dataDir);
        search(query, this.k);
    }

    private boolean isEmpty(String dir) {
        File directory = new File(dir);
        if (directory.isDirectory()) {
            String[] files = directory.list();
            if (directory.length() > 0) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    private void createIndex(String iDir, String dDir) throws IOException {
        indexer = new Indexer(iDir);
        int numIndexed;
        long startTime = System.currentTimeMillis();
        numIndexed = indexer.createIndex(dDir, new TextFileFilter(".bib"));
        long endTime = System.currentTimeMillis();
        indexer.close();
//        System.out.println(numIndexed + " File(s) indexed, time taken: " + (endTime - startTime) + " ms");
    }

    private void search(String searchQuery, int k) throws IOException, ParseException {
        searcher = new Searcher(indexDir);
        long startTime = System.currentTimeMillis();
        TopDocs hits = searcher.search(searchQuery);
        long endTime = System.currentTimeMillis();
        time = endTime - startTime;

        System.out.println(hits.totalHits + " documents found. Time :" + time);
        int i = 1;
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            if (i > k) {
                break;
            } else {
                i++;
            }
            Document doc = searcher.getDocument(scoreDoc);
            System.out.println("File: " + doc.get(LuceneConstants.FILE_PATH));

            System.out.println("SCORE: " + scoreDoc.score);

            boolean flag = false;
            for (IndexableField field : doc.getFields("fieldName")) {
                String line = field.stringValue().toLowerCase();

                File temp_file = new File("Temp_Data/temp.bib");
                FileWriter myWriter = new FileWriter(temp_file);
                myWriter.write(field.stringValue());
                myWriter.close();

                createIndex("Temp_Index", "Temp_Data");
                Searcher srchr = new Searcher("Temp_Index");
                TopDocs hits_2 = srchr.search(searchQuery);
                for (ScoreDoc scorD : hits_2.scoreDocs) {
                    Explanation ex = srchr.indexSearcher.explain(srchr.query, scorD.doc);
                    if (ex.isMatch()) {
                        String author = doc.get(LuceneConstants.FILE_NAME).replace(".bib", "");
                        matches.put(author, new Author(author, scoreDoc.score, field.stringValue()));
                        flag = true;
                        break;
                    }
                }
                temp_file.delete();
                if (flag) {
                    break;
                }
            }
        }
        searcher.close();
    }

    public double getCosineSimilarity(String a1, String a2) throws IOException {
        CosineSimilarity cs = new CosineSimilarity();

        String text1 = Files.readAllLines(Paths.get("Data/" + a1 + ".bib")).stream().collect(Collectors.joining(" "));
        String text2 = Files.readAllLines(Paths.get("Data/" + a2 + ".bib")).stream().collect(Collectors.joining(" "));

        return cs.score(text1, text2);
    }

    public  ArrayList<Author> getCosineSimilarity(String a1) throws IOException {
        ArrayList<Author> scores = new ArrayList<>();
        
        CosineSimilarity cs = new CosineSimilarity();

        String text1 = Files.readAllLines(Paths.get("Data/" + a1 + ".bib")).stream().collect(Collectors.joining(" "));

        File folder = new File(dataDir);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile() && !file.getName().contains(a1)) {
                String text2 = Files.readAllLines(Paths.get(file.getPath())).stream().collect(Collectors.joining(" "));
                
                Author a = new Author(file.getName().replace(".bib", ""), cs.score(text1, text2), "");
                scores.add(a);                
            }
        }
        
        Collections.sort(scores);

        return scores;
    }
}
