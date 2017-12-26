package com.aboutyou.service.serializer;

import com.aboutyou.model.Offer;

import java.util.List;

public class XmlSerializer implements Serializer {

    StringBuilder stringBuilder = new StringBuilder();

    public StringBuilder serialize(List<Offer> list) {
        stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        stringBuilder.append("<offers>\n");
        for (Offer offer : list) {
            stringBuilder.append("<offer>\n");
            stringBuilder.append("<name>" + offer.getName() + "</name>\n");
            stringBuilder.append("<brand>" + offer.getBrand() + "</brand>\n");
            stringBuilder.append("<colors>\n");
            if (offer.getColors() != null) {
                for (String color : offer.getColors()) {
                    stringBuilder.append("<color>" + color + "</color>\n");
                }
            }
            stringBuilder.append("</colors>\n");
            stringBuilder.append("<price>" + offer.getPrice() + "</price>\n");
            stringBuilder.append("<initialPrice>" + offer.getInitialPrice() + "</initialPrice>\n");
            stringBuilder.append("<description>" + offer.getDescription() + "</description>\n");
            stringBuilder.append("<articleID>" + offer.getArticleID() + "</articleID>\n");
            stringBuilder.append("<shippingCosts>" + offer.getShippingCosts() + "</shippingCosts>\n");
            stringBuilder.append("</offer>\n");
        }
        stringBuilder.append("</offers>\n");
        return stringBuilder;
    }
}
