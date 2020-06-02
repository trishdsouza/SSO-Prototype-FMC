package com.freedommortgage.services.signing.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import com.freedommortgage.services.signing.model.Loan;
import com.freedommortgage.services.signing.model.SigningPackage;
import com.freedommortgage.services.signing.service.PackageService;

public class PackageServiceImpl implements PackageService {

    private static final int LOAN_NUMBER = 0;

    private static final int ADDRESS_STREET = 1;

    private static final int NAME = 2;

    private static final int SIGNING_FLAG = 3;

    private static final int PACKAGE_URL = 4;

    private static final int STATUS = 5;

    // Reads loans from a .CSV file - for testing only. Field order defined in the above constants.
    // Successive lines with the same loan number are combined into one loan.
    public List<Loan> loansFromFile(final String fileName) {

        final List<Loan> result = new ArrayList<>();

        final BufferedReader in;
        try {
            final Path path = FileSystems.getDefault().getPath(fileName);
            in = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        } catch (final IOException e) {
            System.out.println("IO exception - does the file exist?");
            return result;
        }
        String line;
        String prevLoanNumber = "aeruihf%";
        Loan loan = new Loan();
        try {
            while ((line = in.readLine()) != null) {
                final String[] tokens = line.split(",");

                if (!prevLoanNumber.equals(tokens[LOAN_NUMBER])) {
                    loan = new Loan();
                    loan.setLoanNumber(tokens[LOAN_NUMBER]);
                    loan.setAddressStreet(tokens[ADDRESS_STREET]);
                    result.add(loan);
                    prevLoanNumber = tokens[LOAN_NUMBER];
                }
                final SigningPackage pkg = new SigningPackage();
                pkg.setName(tokens[NAME]);
                pkg.setSigningFlag("true".equalsIgnoreCase(tokens[SIGNING_FLAG]));
                pkg.setPackageURL(tokens[PACKAGE_URL]);
                pkg.setConsentStatus(tokens[STATUS]);
                loan.getPackages().add(pkg);
            }
            in.close();
        } catch (final IOException e) {
            System.out.println("IO exception: " + e);
            return result;
        }
        return result;
    }
}
