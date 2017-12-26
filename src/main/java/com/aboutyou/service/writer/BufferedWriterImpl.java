package com.aboutyou.service.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BufferedWriterImpl implements Writer {

    public void write(StringBuilder string, String filename) {
        File file = new File(filename + ".xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(string.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
