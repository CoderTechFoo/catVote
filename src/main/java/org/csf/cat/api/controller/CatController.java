package org.csf.cat.api.controller;

import org.csf.cat.api.model.CatResponse;
import org.csf.cat.exception.CatNotFoundException;
import org.csf.cat.service.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cats")
public class CatController {

    private final CatService catService;

    @Autowired
    public CatController (CatService catService) {
        this.catService = catService;
    }

    @GetMapping("/top-vote")
    public ResponseEntity<List<CatResponse>> getTopCatByVote (@RequestParam(defaultValue = "11") int limit) {
        return null;
    }

    @GetMapping("/")
    public ResponseEntity<List<CatResponse>> retrieveCats (@RequestParam(defaultValue = "2") int page, @RequestParam(defaultValue = "0") int size) {
        return null;
    }

    @PostMapping("/{id}/vote")
    public ResponseEntity<Void> vote (@PathVariable String catId) {
        return null;
    }

    @ExceptionHandler(CatNotFoundException.class)
    public ResponseEntity<String> handleCatNotFound(CatNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
