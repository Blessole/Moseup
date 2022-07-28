package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.moseup.service.admin.AsyncService;

// 비동기 처리
@Controller
@RequiredArgsConstructor
public class AsyncController {

    Logger logger = LoggerFactory.getLogger(AsyncController.class);


    final AsyncService asyncService;

    private final AsyncService service;

    @GetMapping("/async")
    public String goAsync(Model model) {
        service.onAsync();
        String str = "Hello Spring Boot Async!!";
        model.addAttribute("str", str);
        logger.info(str);
        logger.info("==================================");
        return str;
    }

    @GetMapping("/sync")
    public String goSync(Model model) {
        service.onSync();
        String str = "Hello Spring Boot Sync!!";
        model.addAttribute("str", str);
        logger.info(str);
        logger.info("==================================");
        return str;
    }

}
