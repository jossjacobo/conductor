package io.ddavison.conductor;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class CustomCapabilitiesTest {

    @Test
    public void custom_capabilities_are_being_added() {
        ConductorConfig config = new ConductorConfig("/test_yaml/simple_defaults.yaml");
        ChromeOptions options = new ChromeOptions();
        DriverUtil.setCustomCapabilities(config, options);

        Map<String, String> expectedCapabilities = new HashMap<>();
        expectedCapabilities.put("fizz", "buzz");
        expectedCapabilities.put("foo", "bar");

        Assertions.assertThat(options.asMap())
                .containsAllEntriesOf(expectedCapabilities);
    }

    @Test
    public void current_scheme_custom_capabilities_are_being_added() {
        ConductorConfig config = new ConductorConfig("/test_yaml/all.yaml");
        ChromeOptions options = new ChromeOptions();
        DriverUtil.setCustomCapabilities(config, options);

        Map<String, String> expectedCapabilities = new HashMap<>();
        expectedCapabilities.put("foo", "bar");
        expectedCapabilities.put("fizz", "buzz");
        expectedCapabilities.put("bar", "foo");

        Assertions.assertThat(options.asMap())
                .containsAllEntriesOf(expectedCapabilities);
    }
}
