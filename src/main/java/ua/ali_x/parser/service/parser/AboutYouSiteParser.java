package ua.ali_x.parser.service.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import ua.ali_x.parser.model.Offer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AboutYouSiteParser implements Parser {
    @Override
    public List<Offer> parse(String keyword) {
        //counter for HTTP request
        int requests = 0;
        Document doc;
        List<String> links;
        List<Offer> offerList = new ArrayList<>();
        try {
            doc = Jsoup.connect("http://www.aboutyou.de/suche?term=" + keyword).get();
            ++requests;
            Elements offers = doc.select("div[class=product-image loaded] > a[href]");
            links = offers.eachAttr("abs:href");
            for (String link : links) {
                Document item = Jsoup.connect(link).get();
                ++requests;
                Offer offer = new Offer();
                offer.setName(item.select("div[class=name_1jqcvyg]").html());
                offer.setBrand(item.select("a[class=brand_1h3c7xk] > img").attr("alt"));
                List<String> colors = new ArrayList<String>();
                List<String> colorLinks = item.select("div[class=thumbsWrapper_1rutc76] > a[href]")
                        .eachAttr("abs:href");
                //todo colors
                for (String color : colorLinks) {
                    colors.add(Jsoup.connect(color)
                            .get()
                            .select("div[class=rc-tooltip-inner]")
                            .html());
                    ++requests;
                }
                offer.setColors(colors);
                offer.setPrice(item.select("span[class^=finalPrice], span[class^=price]").html());
                //todo price
                offer.setInitialPrice(item.select("span[class^=beforePrice], span[class^=originalPrice]").html());
                //todo description
                offer.setDescription(item.select("p[class^=productDescriptionText]").html());
                //todo article
                offer.setArticleID(item.select("p[class^=articleNumber], div[class=container_iv4rb4]").html());
                //todo shipping price
                offer.setShippingCosts(null);
                offerList.add(offer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Amount of triggered HTTP request: " + requests);
        System.out.println("Amount of extracted products: " + offerList.size());
        return offerList;
    }
}
