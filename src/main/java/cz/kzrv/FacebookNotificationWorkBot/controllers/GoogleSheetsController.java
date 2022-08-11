package cz.kzrv.FacebookNotificationWorkBot.controllers;

import cz.kzrv.FacebookNotificationWorkBot.services.SheetService;
import cz.kzrv.FacebookNotificationWorkBot.services.TodayService;
import cz.kzrv.FacebookNotificationWorkBot.services.TomorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping(value = "/api/v1/googlesheets")
public class GoogleSheetsController {

    private final SheetService googleSheetsService;
    private final TomorrowService tomorrowService;
    private final TodayService todayService;
    @Autowired
    public GoogleSheetsController(SheetService googleSheetsService, TomorrowService tomorrowService, TodayService todayService) {
        this.googleSheetsService = googleSheetsService;
        this.tomorrowService = tomorrowService;
        this.todayService = todayService;
    }

    @GetMapping(value="ping")
    public String getSpreadsheetValues() throws IOException, GeneralSecurityException {
        googleSheetsService.getSpreadsheetValues();
        return "OK";
    }
    @GetMapping("ping1")
    public String getSpreadsheetValues1() throws IOException, GeneralSecurityException {
        tomorrowService.sendNotificationForTomorrow();
        return "OK";
    }
    @GetMapping("ping2")
    public String getSpreadsheetValues2() throws IOException, GeneralSecurityException {
        todayService.sendNotificationForToday();
        return "OK";
    }
}