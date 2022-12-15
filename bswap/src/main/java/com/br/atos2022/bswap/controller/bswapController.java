package com.br.atos2022.bswap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class bswapController {
    

    @RequestMapping(value="/bswap",method = RequestMethod.GET)
	public String bswap() {
		log.info("Bswap Page");
        return "bswap";
    }


}
