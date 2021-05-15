package cs353.proje.usecases.common.controller;

import cs353.proje.usecases.common.dto.Response;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class DefaultController {


    @GetMapping("/")
    public String defaultString()
    {
        return "default";
    }

}
