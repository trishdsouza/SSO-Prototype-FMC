package com.freedommortgage.services.signing.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.freedommortgage.services.signing.model.Loan;
import com.freedommortgage.services.signing.service.PackageService;
import com.freedommortgage.services.signing.service.impl.PackageServiceImpl;

@RestController
@RequestMapping("/package")
public class PackageController {

    private PackageService service = new PackageServiceImpl();

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Loan> getList(final HttpServletResponse response) {

        return service.loansFromFile("packages.csv");
    }

    @GetMapping(value = "/list/{loanNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Loan> getListForLoan(@PathVariable final String loanNumber) {

        return service.loansFromFile("packages1.csv");
    }

    @GetMapping(value = "/test")
    @ResponseStatus(HttpStatus.OK)
    public String showHello() {

        return "<h2>Goodbye World!</h2>";
    }

    @GetMapping(value = "/test/{text}")
    @ResponseStatus(HttpStatus.OK)
    public String showPath(@PathVariable final String text) {

        return "<h2>You entered</h2> " + text;
    }
}
