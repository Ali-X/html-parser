package com.aboutyou.service.parser;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class AboutYouSiteParserTest {

    @Test
    public void testParsing() {
        String keyword = "abc";
        Parser parser = new AboutYouSiteParser();
        int listSize = parser.parse(keyword).size();
        assertFalse(listSize == 0);
    }

}
