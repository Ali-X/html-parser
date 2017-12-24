package ua.ali_x.parser.service.parser;

import com.sun.istack.internal.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ua.ali_x.parser.model.Offer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AboutYouSiteParser implements Parser {

    private Random randomGenerator = new Random();

    @Override
    public List<Offer> parse(String keyword) {
        //counter for HTTP request
        int requests = 0;
        List<String> links;
        List<Offer> offerList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("http://www.aboutyou.de/suche?term=" + keyword).get();
            ++requests;
            links = getOfferPagesLinks(doc);
            for (String link : links) {
                //breaks in 1-3 seconds
                breakPause();
                Document item = Jsoup.connect(link).get();
                ++requests;
                Offer offer = new Offer();
                offer.setName(getName(item));
                offer.setBrand(getBrand(item));
                List<String> colors = new ArrayList<>();
                List<String> colorLinks = getColorLinks(item);
                for (String color : colorLinks) {
                    //breaks in 1-3 seconds
                    breakPause();
                    colors.add(getColor(color));
                    ++requests;
                }
                offer.setColors(colors);
                offer.setPrice(getPrice(item));
                offer.setInitialPrice(getInitialPrice(item));
                offer.setDescription(getDescription(item));
                offer.setArticleID(getArticle(item));
                offer.setShippingCosts(getShippingPrice(item));
                offerList.add(offer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Amount of triggered HTTP request: " + requests);
        System.out.println("Amount of extracted products: " + offerList.size());
        return offerList;
    }

    private String getDescription(@NotNull Document item) {
        String result;
        result = item.select("p[class^=productDescriptionText]").html();
        if (result.isEmpty()) {
            //todo description
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(item.select("p[class=subline_19eqe01]").html());
            stringBuilder.append(": ");
            stringBuilder.append(item.select("ul[class=orderedList_1q5u47i] > div > li").html());
            stringBuilder.append("\n");
            stringBuilder.append(item.select("p[class=subline_19eqe01]").html());
            stringBuilder.append(": ");
            result = stringBuilder.toString();
        }
        return result;
    }

    private String getColor(String link) {
        //todo colors
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

    private String getInitialPrice(@NotNull Document item) {
        //todo price
        String result;
        result = item.select("span[class^=beforePrice], span[class^=originalPrice]").html();
        return result;
    }

    private String getPrice(@NotNull Document item) {
        //todo price
        String result;
        result = item.select("span[class^=finalPrice], span[class^=price]").html();
        return result;
    }

    private String getArticle(@NotNull Document item) {
        //todo article
        String result;
        result = item.select("p[class^=articleNumber], div[class=container_iv4rb4]").html();
        return result;
    }

    private String getShippingPrice(@NotNull Document item) {
        //todo shipping price
        String result;
        result = item.select("").html();
        return result;
    }

    private List<String> getColorLinks(@NotNull Document item) {
        return item.select("div[class=thumbsWrapper_1rutc76] > a[href]")
                .eachAttr("abs:href");
    }

    private String getName(@NotNull Document item) {
        String result;
        result = item.select("div[class=name_1jqcvyg]").html();
        return result;
    }

    private String getBrand(@NotNull Document item) {
        String result;
        result = item.select("a[class=brand_1h3c7xk] > img").attr("alt");
        return result;
    }

    private List<String> getOfferPagesLinks(@NotNull Document document) {
        return document.select("div[class=product-image loaded] > a[href]").eachAttr("abs:href");
    }

    private void breakPause() {
        try {
            Thread.sleep(randomGenerator.nextInt((3000 - 1000) + 1) + 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
