package com.freedommortgage.services.signing.service;

import java.util.List;
import com.freedommortgage.services.signing.model.Loan;

public interface PackageService {
    
    List<Loan> loansFromFile(String fileName);
        
}
