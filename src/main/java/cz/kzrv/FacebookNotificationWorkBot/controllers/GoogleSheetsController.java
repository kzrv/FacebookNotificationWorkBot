package cz.kzrv.FacebookNotificationWorkBot.controllers;

import cz.kzrv.FacebookNotificationWorkBot.services.MessageDailyRequestService;
import cz.kzrv.FacebookNotificationWorkBot.services.SheetService;
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
    private final MessageDailyRequestService messageDailyRequestService;
    @Autowired
    public GoogleSheetsController(SheetService googleSheetsService, TomorrowService tomorrowService, MessageDailyRequestService messageDailyRequestService) {
        this.googleSheetsService = googleSheetsService;
        this.tomorrowService = tomorrowService;
        this.messageDailyRequestService = messageDailyRequestService;
    }

    @GetMapping(value="ping")
    public String getSpreadsheetValues() throws IOException, GeneralSecurityException {
        googleSheetsService.getTomorrowShifts();
        return "OK";
    }
    @GetMapping("ping1")
    public String getSpreadsheetValues1() {
        tomorrowService.sendNotificationForTomorrow();
        return "OK";
    }
    @GetMapping("check_tokens")
    public String checkTokens(){
        messageDailyRequestService.checkTokens();
        return "OK";
    }

}