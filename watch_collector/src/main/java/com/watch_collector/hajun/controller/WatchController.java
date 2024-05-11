package com.watch_collector.hajun.controller;


import com.watch_collector.hajun.domain.Watch;
import com.watch_collector.hajun.service.WatchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WatchController {
    WatchService watchService;

    public WatchController(WatchService watchService){
        this.watchService = watchService;
    }


}
