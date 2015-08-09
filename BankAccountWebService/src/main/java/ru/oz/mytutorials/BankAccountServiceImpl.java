package ru.oz.mytutorials;

import ru.oz.mytutorials.service.BankAccountService;
import ru.oz.mytutorials.service.UserDetails;

import java.math.BigDecimal;

/**
 * User: Igor
 * Date: 09.08.2015
 */
public class BankAccountServiceImpl implements BankAccountService {
    @Override
    public UserDetails getUserDetails(String userMyName) {
        UserDetails usrDetails = new UserDetails();
        usrDetails.setName("Igor Iv");
        usrDetails.setBankBalance(new BigDecimal("100.00"));

        return usrDetails;
    }

    @Override
    public void addUserDetails(UserDetails userDetails) {
        System.out.println("Adding userDetails");
        throw new NullPointerException("Нульпуинтер");
    }
}
