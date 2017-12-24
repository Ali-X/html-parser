package ua.ali_x.parser.service.writer;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BufferedWriterTest {

    @Test
    public void testWriting() {
        StringBuilder stringBuilder = new StringBuilder("test");
        Writer writer = new BufferedWriterImpl();
        writer.write(stringBuilder, "test");
        try {
            BufferedReader reader
                    = new BufferedReader(new FileReader("test.xml"));
        } catch (FileNotFoundException e) {
            assertThat(e.getMessage(), is("find is found"));
        }
    }

}
