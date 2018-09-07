package io.ddavison.conductor;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class CustomOverrideCapabilities extends Locomotive {

    private String envCurrentSchemes, envBaseUrl;

    @BeforeMethod(alwaysRun = true)
    public void before() {
        envCurrentSchemes = System.getProperty(ConductorConfig.CONDUCTOR_CURRENT_SCHEMES);
        envBaseUrl = System.getProperty(ConductorConfig.CONDUCTOR_BASE_URL);

        System.clearProperty(ConductorConfig.CONDUCTOR_CURRENT_SCHEMES);
        System.clearProperty(ConductorConfig.CONDUCTOR_BASE_URL);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (envCurrentSchemes != null) {
            System.setProperty(ConductorConfig.CONDUCTOR_CURRENT_SCHEMES, envCurrentSchemes);
        }
        if (envBaseUrl != null) {
            System.setProperty(ConductorConfig.CONDUCTOR_BASE_URL, envBaseUrl);
        }
    }

    @Override
    protected DesiredCapabilities onCapabilitiesCreated(DesiredCapabilities desiredCapabilities) {
        desiredCapabilities.setCapability("customOverrideCapName", "Cappy");
        return super.onCapabilitiesCreated(desiredCapabilities);
    }

    @Test
    public void capabilities_include_custom_override_capabilities() {
        DesiredCapabilities capabilities = this.onCapabilitiesCreated(buildCapabilities(this.configuration));

        Map<String, String> expectedCapabilities = new HashMap<>();
        expectedCapabilities.put("customOverrideCapName", "Cappy");

        Assertions.assertThat(capabilities.asMap())
                .containsAllEntriesOf(expectedCapabilities);
    }
}
