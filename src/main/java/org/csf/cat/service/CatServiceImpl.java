package org.csf.cat.service;

import org.csf.cat.exception.CatNotFoundException;
import org.csf.cat.model.Cat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class CatServiceImpl implements CatService {
    @Override
    public List<Cat> retrieveCats(int page, int pageSize) {
        return List.of();
    }

    @Override
    public List<Cat> retrieveTopVotes(int topLimit) {
        return List.of();
    }

    @Override
    public void vote(String catId) throws CatNotFoundException {

    }
}
