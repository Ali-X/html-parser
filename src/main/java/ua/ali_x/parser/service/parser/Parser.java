package ua.ali_x.parser.service.parser;

import ua.ali_x.parser.model.Offer;

import java.util.List;

public interface Parser {

    List<Offer> parse(String keyword);

}
