package com.zhiyou.bd14.bi.service.impl;

import com.zhiyou.bd14.bi.dao.HotelRoomTypeRateDao;
import com.zhiyou.bd14.bi.domain.HotelRoomTypeRate;
import com.zhiyou.bd14.bi.service.HotelRoomTypeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelRoomTypeRateServiceImpl implements HotelRoomTypeRateService{

    @Autowired
    private HotelRoomTypeRateDao hotelRoomTypeRateDao;


    @Override
    public List<HotelRoomTypeRate> findAll() {
        return hotelRoomTypeRateDao.findAll();
    }
}
