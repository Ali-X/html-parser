package com.aboutyou.service.parser;

import com.aboutyou.model.Offer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AboutYouSiteParser implements Parser {

    private Random randomGenerator = new Random();

    @Override
    public List<Offer> parse(String keyword) {
        int numRequests = 0;
        List<String> links;
        List<Offer> offerList = new ArrayList<>();
        String nextPageLink = "";
        try {
            Document doc = Jsoup.connect("http://www.aboutyou.de/suche?term=" + keyword).get();
            ++numRequests;
            do {
                links = getOfferPagesLinks(doc);
                for (String link : links) {
                    simulateHumanWaitTime();
                    Document item = Jsoup.connect(link).get();
                    ++numRequests;
                    Offer offer = new Offer();
                    offer.setName(getName(item));
                    offer.setBrand(getBrand(item));
                    List<String> colors = new ArrayList<>();
                    List<String> colorLinks = getColorLinks(item);
                    for (String color : colorLinks) {
                        simulateHumanWaitTime();
                        colors.add(getColor(color));
                        ++numRequests;
                    }
                    offer.setColors(colors);
                    offer.setPrice(getPrice(item));
                    offer.setInitialPrice(getInitialPrice(item));
                    offer.setDescription(getDescription(item));
                    offer.setArticleID(getArticle(item));
                    offer.setShippingCosts(getShippingPrice(item));
                    offerList.add(offer);
                }
                nextPageLink = getNextPageLink(doc);
                if (!nextPageLink.isEmpty()) {
                    doc = Jsoup.connect(nextPageLink).get();
                    ++numRequests;
                }
            } while (!nextPageLink.isEmpty());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Amount of triggered HTTP request: " + numRequests);
        System.out.println("Amount of extracted products: " + offerList.size());
        return offerList;
    }

    private String getNextPageLink(Document doc) {
        String result;
        result = doc.select("li.nextButton_3hmsj > a[href]").attr("abs:href");
        return result;
    }

    private String getDescription(Document item) {
        String result;
        String temp;
        result = item.select("p[class^=productDescriptionText]").html();
        if (result.isEmpty()) {
            //todo arrange description elements
            StringBuilder stringBuilder = new StringBuilder();
            Elements elements = item.select("div.container_iv4rb4");
            for (Element element : elements) {
                temp = element.select("p.subline_19eqe01").html() + ":\n";
                temp = removeComments(temp);
                stringBuilder.append(temp);
                Elements list = elements.select("li");
                for (Element listElement : list) {
                    temp = listElement.html() + "\n";
                    temp = removeComments(temp);
                    stringBuilder.append(temp);
                }
            }
            result = stringBuilder.toString();
        }
        return result;
    }

    private String getColor(String link) throws IOException {
        //todo colors (JSoup doesn't parse react pages)
        try {
            return Jsoup.connect(link)
                    .get()
                    .select("div[class=rc-tooltip-inner]")
                    .html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getInitialPrice(Document item) {
        String result;
        result = item.select("span[class^=beforePrice]").html();
        if (result.isEmpty())
            result = item.select("span[class^=originalPrice]").html();
        return result;
    }

    private String getPrice(Document item) {
        String result;
        result = item.select("span[class^=finalPrice]").html();
        if (result.isEmpty())
            result = item.select("span[class^=price]").html();
        return result;
    }

    private String getArticle(Document item) {
        String result;
        result = item.select("p[class^=articleNumber]").html();
        if (result.isEmpty()) {
            result = item.select("div[class=container_iv4rb4]").first().ownText();
            result = result.replaceAll("Artikel-Nr: ", "");
        }
        return result;
    }

    private String getShippingPrice(Document item) {
        //todo shipping price is absent on site
        String result;
        result = item.select("span[class^=shippingPrice]").html();
        return result;
    }

    private List<String> getColorLinks(Document item) {
        return item.select("div[class=thumbsWrapper_1rutc76] > a[href]")
                .eachAttr("abs:href");
    }

    private String getName(Document item) {
        String result;
        result = item.select("div[class=name_1jqcvyg]").html();
        return result;
    }

    private String getBrand(Document item) {
        String result;
        result = item.select("a[class=brand_1h3c7xk] > img").attr("alt");
        return result;
    }

    private List<String> getOfferPagesLinks(Document document) {
        //todo add condition check for different tags
        return document.select("div[class=product-image loaded] > a[href]").eachAttr("abs:href");
    }

    private void simulateHumanWaitTime() {
        try {
            Thread.sleep(randomGenerator.nextInt((3000 - 1000) + 1) + 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String removeComments(String text) {
        text = text.replaceAll("<!--(.*?)-->", "");
        text = text.replaceAll("(?m)^[ \t]*\r?\n", "");
        return text;
    }
}
