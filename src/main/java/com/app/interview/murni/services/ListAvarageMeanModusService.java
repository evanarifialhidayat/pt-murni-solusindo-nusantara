package com.app.interview.murni.services;

import java.util.List;

import com.app.interview.murni.model.ListAvarageMeanModus;
import com.app.interview.murni.model.UserLogin;
public interface ListAvarageMeanModusService {
    List findAllAvarageScore();
    List findAllModusEmotion();
    List findAllWhereTanggalYangSama();
    ListAvarageMeanModus insertData(ListAvarageMeanModus gate);
}
