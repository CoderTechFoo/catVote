package org.csf.cat.service;

import org.csf.cat.exception.CatNotFoundException;
import org.csf.cat.model.Cat;

import java.util.List;

public interface CatService {
    List<Cat> retrieveCats (int page, int pageSize);

    List<Cat> retrieveTopVotes (int topLimit);

    void vote (String catId) throws CatNotFoundException;
}
