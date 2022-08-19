package com.emrebaglayici.myhremrebaglayici.Controllers;

import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.CandidatesService;
import com.emrebaglayici.myhremrebaglayici.Entities.Concretes.Candidates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/candidate")
public class CandidateController {

    private CandidatesService candidatesService;


    @Autowired
    public CandidateController(CandidatesService candidatesService) {
        this.candidatesService = candidatesService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Candidates candidate){
        return ResponseEntity.ok(this.candidatesService.add(candidate));
    }
}
