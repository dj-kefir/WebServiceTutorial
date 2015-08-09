package ru.oz.mytutorials.service;

import ru.oz.mytutorials.domain.UserDetails;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * User: Igor
 * Date: 08.08.2015
 */
@WebService
public interface BankAccountService {

    public UserDetails getUserDetails(@WebParam(name = "userMyName")final String userName);

    public void addUserDetails(@WebParam(name = "userDetails") UserDetails userDetails);

}
