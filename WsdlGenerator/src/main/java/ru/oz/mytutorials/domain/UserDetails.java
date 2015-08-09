package ru.oz.mytutorials.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

/**
 * User: Igor
 * Date: 08.08.2015
 */
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "UserDetails")
public class UserDetails {
    private String name;
    private BigDecimal bankBalance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(BigDecimal bankBalance) {
        this.bankBalance = bankBalance;
    }
}
