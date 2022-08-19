package com.emrebaglayici.myhremrebaglayici.DateAccess;

import com.emrebaglayici.myhremrebaglayici.Entities.Concretes.Candidates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatesDao extends JpaRepository<Candidates,Integer> {
}
