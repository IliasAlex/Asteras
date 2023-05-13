/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Asteras;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {

    private IndexWriter writer;

    public Indexer(String indexDirectoryPath) throws IOException {
        // this directory will contain the indexes
        Path indexPath = Paths.get(indexDirectoryPath);

        if (!Files.exists(indexPath)) {
            Files.createDirectory(indexPath);
        }
        
        File[] files = indexPath.toFile().listFiles();

        // delete each file from the directory
        for (File file : files) {
            if( !file.isDirectory())
                file.delete();
        }
        
        // Path indexPath = Files.createTempDirectory(indexDirectoryPath);
        Directory indexDirectory = FSDirectory.open(indexPath);
        // create the indexer
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        writer = new IndexWriter(indexDirectory, config);
    }

    public void close() throws CorruptIndexException, IOException {
        writer.close();
    }

    private Document getDocument(File file) throws IOException {
        Document document = new Document();
        // index file contents
        BufferedReader br = new BufferedReader(new FileReader(file));

        while ((br.read()) != -1) {
            String currentLine = br.readLine().toString();
            currentLine = currentLine.replaceFirst("[a-zA-Z]+: ", " ");
            Field contentField = new Field(LuceneConstants.CONTENTS, currentLine, TextField.TYPE_STORED);
            
            // index file name
            Field fileNameField = new Field(LuceneConstants.FILE_NAME, file.getName(), StringField.TYPE_STORED);
            // index file path
            Field filePathField = new Field(LuceneConstants.FILE_PATH, file.getCanonicalPath(), StringField.TYPE_STORED);
            document.add(contentField);
            document.add(fileNameField);
            document.add(filePathField);
        }

        br.close();
        return document;
    }

    private void indexFile(File file) throws IOException {
        Document document = getDocument(file);
        writer.addDocument(document);
    }

    public int createIndex(String dataDirPath, FileFilter filter) throws
            IOException {
        String currentPath = new java.io.File(".").getCanonicalPath();

        //get all files in the data directory
        File[] files = new File(dataDirPath).listFiles();
        for (File file : files) {
            if (!file.isDirectory()
                    && !file.isHidden()
                    && file.exists()
                    && file.canRead()
                    && filter.accept(file)) {
                indexFile(file);
            }
        }
        return writer.numRamDocs();
    }
}
