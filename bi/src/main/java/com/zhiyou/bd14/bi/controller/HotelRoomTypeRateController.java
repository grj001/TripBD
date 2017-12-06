package com.zhiyou.bd14.bi.controller;

import com.zhiyou.bd14.bi.domain.HotelRoomTypeRate;
import com.zhiyou.bd14.bi.service.HotelRoomTypeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HotelRoomTypeRateController {

    @Autowired
    private HotelRoomTypeRateService hotelRoomTypeRateService;

    @RequestMapping("/hotelrtrate")
    public String getHotelData(Model model){

        List<HotelRoomTypeRate> list = hotelRoomTypeRateService.findAll();
        model.addAttribute("hotelrtInfos",list);
        return "hotelrtrate";
    }














}
