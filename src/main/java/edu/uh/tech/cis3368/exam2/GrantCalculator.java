package edu.uh.tech.cis3368.exam2;

import java.math.BigDecimal;

public class GrantCalculator {

    private final BigDecimal grantValue;

    /**
     * Calculates the grant value
     * */
    public GrantCalculator(BigDecimal grantAmount) {
        BigDecimal netGrant = new BigDecimal("0.6");
        grantValue = grantAmount.multiply(netGrant);
    }

    /**
     * Calculates the student loaded cost and returns the
     * calculated value
     * */
    public static Comparable<BigDecimal> calculateLoadedCost(BigDecimal studentHourlyRate,
                                                             int hoursPerMonth) {
        BigDecimal hoursWorked = studentHourlyRate.multiply(new BigDecimal(hoursPerMonth));
        BigDecimal studentLoadedCost = hoursWorked.multiply(new BigDecimal("1.15"));
        System.out.println(studentLoadedCost);
        return studentLoadedCost;
    }

    // Returns 60% of the grant value
    public BigDecimal getNet() {
        return grantValue;
    }
}
