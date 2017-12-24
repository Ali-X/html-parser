package ua.ali_x.parser.service.serializer;

import ua.ali_x.parser.model.Offer;

import java.util.List;

public class XmlSerializer implements Serializer {

    StringBuilder string = new StringBuilder();

    public StringBuilder serialize(List<Offer> list) {
        string.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        string.append("<offers>\n");
        for (Offer offer : list) {
            string.append("<offer>\n");
            string.append("<name>" + offer.getName() + "</name>\n");
            string.append("<brand>" + offer.getBrand() + "</brand>\n");
            string.append("<colors>\n");
            if (offer.getColors() != null) {
                for (String color : offer.getColors()) {
                    string.append("<color>" + color + "</color>\n");
                }
            }
            string.append("</colors>\n");
            string.append("<price>" + offer.getPrice() + "</price>\n");
            string.append("<initialPrice>" + offer.getInitialPrice() + "</initialPrice>\n");
            string.append("<description>" + offer.getDescription() + "</description>\n");
            string.append("<articleID>" + offer.getArticleID() + "</articleID>\n");
            string.append("<shippingCosts>" + offer.getShippingCosts() + "</shippingCosts>\n");
            string.append("</offer>\n");
        }
        string.append("</offers>\n");
        return string;
    }
}
