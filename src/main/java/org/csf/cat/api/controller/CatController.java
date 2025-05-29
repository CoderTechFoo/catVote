package org.csf.cat.api.controller;

import org.csf.cat.api.model.CatResponse;
import org.csf.cat.exception.CatNotFoundException;
import org.csf.cat.mapper.CatMapper;
import org.csf.cat.model.Cat;
import org.csf.cat.service.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cats")
public class CatController {

    private final CatService catService;

    private final CatMapper catMapper;

    @Autowired
    public CatController (CatService catService,
                          CatMapper catMapper) {
        this.catService = catService;
        this.catMapper = catMapper;
    }

    @GetMapping("/top-vote")
    public ResponseEntity<List<CatResponse>> getTopCatsByVote(@RequestParam(defaultValue = "11") int limit) {
        List<Cat> cats = catService.retrieveTopVotes(limit);
        return ResponseEntity.of(Optional.ofNullable(catMapper.toResponseList(cats)));
    }

    @GetMapping("/")
    public ResponseEntity<List<CatResponse>> retrieveCats (@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "2") int size) {
        List<Cat> cats = catService.retrieveCats(page, size);
        return ResponseEntity.of(Optional.ofNullable(catMapper.toResponseList(cats)));
    }

    @PostMapping("/{id}/vote")
    public ResponseEntity<Void> vote (@PathVariable String id) {
        catService.vote(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @ExceptionHandler(CatNotFoundException.class)
    public ResponseEntity<String> handleCatNotFound(CatNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
