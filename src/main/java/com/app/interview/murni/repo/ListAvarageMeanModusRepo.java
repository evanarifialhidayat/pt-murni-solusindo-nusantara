package com.app.interview.murni.repo;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.interview.murni.model.ListAvarageMeanModus;

@Repository
public interface ListAvarageMeanModusRepo extends DataTablesRepository<ListAvarageMeanModus, Long> {
	ListAvarageMeanModus findByName(String Name);
	
	@Query("SELECT e FROM ListAvarageMeanModus e ") 
	List<ListAvarageMeanModus> findAllData();
	
	@Query("SELECT avg(e.score) FROM ListAvarageMeanModus e GROUP BY e.name ") 
	List<ListAvarageMeanModus> findAllAvarageScore();
	
}
