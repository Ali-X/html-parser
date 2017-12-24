package ua.ali_x.parser.service.serializer;

import org.junit.Test;
import ua.ali_x.parser.model.Offer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class XmlSerializerTest {

    @Test
    public void testSerialization() {
        List<Offer> offerList = new ArrayList<>();
        Offer offer = new Offer();
        offer.setDescription("description");
        offer.setBrand("brand");
        offer.setName("name");
        offer.setArticleID("article");
        offer.setInitialPrice("12");
        offer.setPrice("11");
        offer.setShippingCosts("10");
        offerList.add(offer);
        Serializer serializer = new XmlSerializer();
        assertNotNull(serializer.serialize(offerList));
    }

}
