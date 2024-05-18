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
	
	@Query("SELECT new com.app.interview.murni.model.ListAvarageMeanModus(e.name , avg(e.score)) FROM ListAvarageMeanModus e GROUP BY e.name ") 
	List<ListAvarageMeanModus> findAllAvarageScore();
	
	@Query(nativeQuery = true, value = "WITH x AS (select name,emotion,count(*) as cnt from public.avgmeanmodus group by emotion,name),	 "
			+ "y AS (select name,emotion,cnt,RANK() OVER(ORDER BY cnt DESC) as rnk	from x)  "
			+ "select name , emotion , rnk from y where rnk=1 ")
	List<Object[]> findAllModusEmotion();
	
	
	@Query(nativeQuery = true, value = "select name,created, round(avg(score),2) "
			+ "from public.avgmeanmodus group by name,created ")
	List<Object[]> findAllAvarageScoreNameTanggal();
	
	@Query(nativeQuery = true, value = "WITH x AS (select name,emotion,count(*) as cnt,created from public.avgmeanmodus group by emotion,name,created), "
			+ "y AS (select name,emotion,cnt,created,RANK() OVER(ORDER BY cnt DESC) as rnk	from x)  "
			+ "select created,name , emotion , rnk from y where rnk=1 ")
	List<Object[]> findAllModusEmotionNameTanggal();
	
}
