package utils;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags = "@Deneme",
        plugin = {"pretty"},
        features = "src/test/resources/features",
        glue = {
                "base",
                "steps",
        },
        publish = true
)

public class CucumberRunner {
}
