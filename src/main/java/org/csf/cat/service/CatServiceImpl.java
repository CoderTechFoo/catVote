package org.csf.cat.service;

import org.csf.cat.dao.CatDao;
import org.csf.cat.exception.CatNotFoundException;
import org.csf.cat.model.Cat;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class CatServiceImpl implements CatService {
    private final CatDao catDao;

    CatServiceImpl (CatDao catDao) {
        this.catDao = catDao;
    }

    @Override
    public List<Cat> retrieveCats(int page, int pageSize) {
        return catDao.findAllByOrderByIdAsc(PageRequest.of(page, pageSize)).getContent();
    }

    @Override
    public List<Cat> retrieveTopVotes(int topLimit) {
        return catDao.findTopVote(PageRequest.of(0, topLimit));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void vote(String catId) throws CatNotFoundException {
        catDao.incrementVoteCount(catId);
    }
}
