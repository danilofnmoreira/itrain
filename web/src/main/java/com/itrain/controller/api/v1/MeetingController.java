package com.itrain.controller.api.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = { "/api/v1" })
@RestController
public class MeetingController {

    @GetMapping(path = { "/meet" })
    public String getMethodName() {
        return "meetmeetmeetmeetmeetmeetmeetmeetmeet";
    }

}