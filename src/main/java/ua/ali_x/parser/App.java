package ua.ali_x.parser;

import ua.ali_x.parser.service.parser.AboutYouSiteParser;
import ua.ali_x.parser.service.parser.Parser;
import ua.ali_x.parser.service.serializer.Serializer;
import ua.ali_x.parser.service.serializer.XmlSerializer;
import ua.ali_x.parser.service.writer.BufferedWriterImpl;
import ua.ali_x.parser.service.writer.Writer;
//todo article number (different tags)
//todo color can't find tag
//todo price (different tags)
//todo shipping price (no tag)

//todo unit tests
//todo user emulation

public class App {

    public static void main(String[] args) {
        /* Setting
         * initial time and
         * initial memory
         */
        long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long startTime = System.currentTimeMillis();

        Parser parser = new AboutYouSiteParser();
        Serializer serializer = new XmlSerializer();
        Writer writer = new BufferedWriterImpl();

        writer.write(serializer.serialize(parser.parse(args[0])), "offerList");

        /* Setting
         * end time and
         * end memory
         */
        long endTime = System.currentTimeMillis();
        long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        /* Calculating
         * total run time and
         * used memory
         */
        long totalTime = endTime - startTime;
        long actualMemUsed = afterUsedMem - beforeUsedMem;
        System.out.println("Run-time: " + totalTime);
        System.out.println("Memory Footprint: " + actualMemUsed);
    }

}
