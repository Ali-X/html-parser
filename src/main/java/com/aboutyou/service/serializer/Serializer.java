package com.aboutyou.service.serializer;

import com.aboutyou.model.Offer;

import java.util.List;

public interface Serializer {

    StringBuilder serialize(List<Offer> list);

}
