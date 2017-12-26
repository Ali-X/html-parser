package com.aboutyou.service.parser;

import com.aboutyou.model.Offer;

import java.util.List;

public interface Parser {

    List<Offer> parse(String keyword);

}
