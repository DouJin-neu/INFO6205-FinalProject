package edu.neu.coe.info6205.sort.counting;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:edu/neu/coe/info6205/com.info6205.team21.sort/com.info6205.team21.counting/lsdsort/LSDStringSort.feature",
        glue = "classpath:edu.neu.coe.info6205.com.info6205.team21.sort.com.info6205.team21.counting.LSDStringSortStepDefinition",
        plugin = "html:target/LSDStringSort-report", strict = true
)
public class LSDStringSortTestRunner {

}
