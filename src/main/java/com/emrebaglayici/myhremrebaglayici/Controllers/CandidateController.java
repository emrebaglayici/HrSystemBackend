//package com.emrebaglayici.myhremrebaglayici.Controllers;
//
//import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.CandidatesService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//
//
//@RestController
//@RequestMapping("/api/candidate")
//public class CandidateController {
//
//    private CandidatesService candidatesService;
//
//
//    @Autowired
//    public CandidateController(CandidatesService candidatesService) {
//        this.candidatesService = candidatesService;
//    }
//
////    @PostMapping(value = "/add")
////    public ResponseEntity<?> add(@RequestBody Candidates candidates) {
////        return ResponseEntity.ok(this.candidatesService.add(candidates));
////    }
////
//////    @PostMapping("/add")
//////    public ResponseEntity<?> add(@RequestBody Candidates candidate){
//////        return ResponseEntity.ok(this.candidatesService.add(candidate));
//////    }
//////
//////    @DeleteMapping("/{id}")
//////    public ResponseEntity<?> delete(@PathVariable Integer id){
//////        return ResponseEntity.ok(this.candidatesService.deleteById(id));
//////    }
//////
//////    @GetMapping("/getAll")
//////    public DataResult<List<Candidates>> getAll(){
//////        return this.candidatesService.getAll();
//////    }
//////
//////    @PutMapping("/updateCandidatesName")
//////    public Result setName(@RequestParam Integer id,@RequestParam String name){
//////        return this.candidatesService.setName(id,name);
//////    }
//}
