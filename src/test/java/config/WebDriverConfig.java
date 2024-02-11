package config;

import org.aeonbits.owner.Config;

import java.net.URL;


@Config.Sources({
        "classpath:${env}.properties"
})
public interface WebDriverConfig extends Config {

    @Key("baseUrl")
    String baseUrl();

    @Key("browser")
    String browser();

    @Key("browserVersion")
    String browserVersion();

    @Key("browserSize")
    String browserSize();

    @Key("isRemote")
    @DefaultValue("false")
    boolean isRemote();

    @Key("remoteUrl")
    URL remoteUrl();

}