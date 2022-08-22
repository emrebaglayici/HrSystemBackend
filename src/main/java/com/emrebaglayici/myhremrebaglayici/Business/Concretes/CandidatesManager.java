//package com.emrebaglayici.myhremrebaglayici.Business.Concretes;
//
//import com.emrebaglayici.myhremrebaglayici.Business.Abstracts.CandidatesService;
//import com.emrebaglayici.myhremrebaglayici.Repository.CandidatesRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CandidatesManager implements CandidatesService {
//
//    private CandidatesRepository candidatesRepository;
//    //use final for candidatesDoa
//
//    @Autowired
//    public CandidatesManager(CandidatesRepository candidatesRepository) {
//        this.candidatesRepository = candidatesRepository;
//    }
//
////    @Override
////    public Result add(Candidates candidates) {
////        candidatesRepository.save(candidates);
////        return new SuccessResult("Candidates added");
////    }
//
////    @Override
////    public Result add(Candidates candidate) {
////        this.candidatesRepository.save(candidate);
////        return new SuccessDataResult<>("Candidates added");
////    }
////
////    @Override
////    public Result deleteById(Integer id) {
////        this.candidatesRepository.deleteById(id);
////        return new SuccessDataResult<>("Candidate deleted");
////    }
////
////    @Override
////    public DataResult<List<Candidates>> getAll() {
////        return new SuccessDataResult<List<Candidates>>(
////                this.candidatesRepository.findAll(),"Candidates are listed"
////        );
////    }
////
////    @Override
////    public Result setName(Integer id,String name) {
////        Candidates candidates;
////        candidates=this.candidatesRepository.findById(id).get();
////        candidates.setName(name);
////        this.candidatesRepository.save(candidates);
////        return new SuccessResult("Candidates name changed");
////    }
//
//}
