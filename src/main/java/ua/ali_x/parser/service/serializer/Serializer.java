package ua.ali_x.parser.service.serializer;

import ua.ali_x.parser.model.Offer;

import java.util.List;

public interface Serializer {

    StringBuilder serialize(List<Offer> list);

}
