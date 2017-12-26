package com.aboutyou;

import com.aboutyou.service.parser.AboutYouSiteParser;
import com.aboutyou.service.parser.Parser;
import com.aboutyou.service.serializer.Serializer;
import com.aboutyou.service.serializer.XmlSerializer;
import com.aboutyou.service.writer.BufferedWriterImpl;
import com.aboutyou.service.writer.Writer;

public class App {

    public static void main(String[] args) {
        /* Setting initial time and initial memory */
        long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long startTime = System.currentTimeMillis();

        Parser parser = new AboutYouSiteParser();
        Serializer serializer = new XmlSerializer();
        Writer writer = new BufferedWriterImpl();

        writer.write(serializer.serialize(parser.parse(args[0])), "offerList");

        /* Setting end time and end memory */
        long endTime = System.currentTimeMillis();
        long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        /* Calculating total run time and used memory */
        long totalTime = endTime - startTime;
        long actualMemUsed = afterUsedMem - beforeUsedMem;
        System.out.println("Run-time: " + totalTime / 1000 + "sec");
        System.out.println("Memory Footprint: " + actualMemUsed / 1048576 + "mb");
    }

}
