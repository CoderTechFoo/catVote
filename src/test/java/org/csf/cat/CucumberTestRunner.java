package org.csf.cat;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "org.csf.cat.glue",
        plugin = {"pretty", "summary"}
)
public class CucumberTestRunner {
}
