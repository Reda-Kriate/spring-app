package com.reda;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class De3bolController {

    record De3bol(String result){};

    @GetMapping("/de3bol")
    public De3bol getDe3bol(){
        return new De3bol("hbiiiiiiiib gelbiiiiiiiiii 7ammoud anas mzaater hbibnaaa et de3bol lkbir hbibnaa optional 🥰");
    }
}
