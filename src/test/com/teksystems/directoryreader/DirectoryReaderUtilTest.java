package com.teksystems.directoryreader;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class DirectoryReaderUtilTest {


    private final ByteArrayOutputStream out;
    private final ByteArrayOutputStream err;


    public DirectoryReaderUtilTest() {
        out = new ByteArrayOutputStream();
        err = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));

    }

    @Test
    public void testDefault() throws IOException {

        try{
            new File("./testdirectory/Main Project/Project 2").mkdirs();
            new File("./testdirectory/Main Project/Project 2/WordFile2.docx").createNewFile();
            new File("./testdirectory/Main Project/Project 2/ExcelFile2.xlsx").createNewFile();
            new File("./testdirectory/Main Project/Project 1").mkdirs();
            new File("./testdirectory/Main Project/Project 1/ExcelFile1.xlsx").createNewFile();
            new File("./testdirectory/Main Project/Project 1/WordFile1.docx").createNewFile();
            new File("./testdirectory/Main Project/Project 1/Project A").mkdirs();
            new File("./testdirectory/Main Project/Project 1/Project A/PPTFile1.pptx").createNewFile();


            String defaultOutput =
                "- Project: Main Project - URL: Main Project\n" +
                "  - Project: Project 2 - URL: Main Project/Project 2\n" +
                "    - Document: WordFile2.docx - Extension: .docx - URL: Main Project/Project 2/WordFile2.docx\n" +
                "    - Document: ExcelFile2.xlsx - Extension: .xlsx - URL: Main Project/Project 2/ExcelFile2.xlsx\n" +
                "  - Project: Project 1 - URL: Main Project/Project 1\n" +
                "    - Document: ExcelFile1.xlsx - Extension: .xlsx - URL: Main Project/Project 1/ExcelFile1.xlsx\n" +
                "    - Document: WordFile1.docx - Extension: .docx - URL: Main Project/Project 1/WordFile1.docx\n" +
                "    - Project: Project A - URL: Main Project/Project 1/Project A\n" +
                "      - Document: PPTFile1.pptx - Extension: .pptx - URL: Main Project/Project 1/Project A/PPTFile1.pptx\n";

            DirectoryReaderUtil reader = new DirectoryReaderUtil();
            reader.listAllFilesRecursively("./testdirectory/Main Project");

            assertEquals(defaultOutput, out.toString());
        }finally{
            FileUtils.deleteDirectory(new File("./testdirectory"));
        }

    }

    @Test
    public void testNotExistingDirectory() {

        DirectoryReaderUtil reader = new DirectoryReaderUtil();
        reader.listAllFilesRecursively("some non existing dir");

        assertTrue(out.toString().contains("not exist"));

    }

    @Test
    public void testNull() {
        DirectoryReaderUtil reader = new DirectoryReaderUtil();
        reader.listAllFilesRecursively(null);

        assertTrue(out.toString().isEmpty());

    }



}