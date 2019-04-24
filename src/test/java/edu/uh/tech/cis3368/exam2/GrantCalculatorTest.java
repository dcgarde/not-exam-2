package edu.uh.tech.cis3368.exam2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GrantCalculatorTest {

    @Test
    public void netGrantShouldBe60percentOfTotalGrant(){
        BigDecimal grant = new BigDecimal("100000");
        BigDecimal expected = new BigDecimal("60000");
        GrantCalculator grantCalculator = new GrantCalculator(grant);
        assertEquals(0, expected.compareTo(grantCalculator.getNet()));
    }

    @Test
    public void calculateStudentLoadedCost(){
        BigDecimal studentHourlyRate = new BigDecimal("15.00");
        var hoursPerMonth = 60;
        //assert(false); // remove this
//        // loaded cost for a month should be amount earned * 1.15
// UNCOMMENT THE FOLLOWING LINES
        assertEquals(0,GrantCalculator.calculateLoadedCost(studentHourlyRate,hoursPerMonth)
                .compareTo(new BigDecimal("1035.00")));





    }
}
