package com.pms.easy_book.controller.admin_controller;

import com.pms.easy_book.dto.SummaryDTO;
import com.pms.easy_book.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api")
public class SummaryController {

    @Autowired
    private SummaryService summaryService;

    @GetMapping("/get/live/data")
    public ResponseEntity<SummaryDTO> getLiveData(){
        SummaryDTO liveSummary = summaryService.getLiveSummary();
        return ResponseEntity.status(HttpStatus.CREATED).body(liveSummary);
    }

}
