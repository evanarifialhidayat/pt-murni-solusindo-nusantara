package com.app.interview.murni.services.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.interview.murni.model.ListAvarageMeanModus;
import com.app.interview.murni.repo.ListAvarageMeanModusRepo;
import com.app.interview.murni.services.ListAvarageMeanModusService;

@Service
@Transactional
public class ListAvarageMeanModusImpl implements ListAvarageMeanModusService{
	
	@Autowired ListAvarageMeanModusRepo avarageMeanModusRepo;
	@Autowired	private EntityManager em;

//	@Override
//	public List findAllData() {
//		return (List) userLoginRepo.findAll();
//	}


	@Override
	public List findAllAvarageScore() {
		// TODO Auto-generated method stub
		return (List) avarageMeanModusRepo.findAllAvarageScore();
	}

	@Override
	public List findAllModusEmotion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findAllWhereTanggalYangSama() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListAvarageMeanModus insertData(ListAvarageMeanModus gate) {
		ListAvarageMeanModus obj = avarageMeanModusRepo.save(gate);	
		return obj;
	}

	@Override
	public List<ListAvarageMeanModus> getAllAvarageScore() {
		// TODO Auto-generated method stub
		return null;
	}

}
