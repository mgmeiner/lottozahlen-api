package eu.mgmeiner.lottozahlenapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration Properties for 'LottozahlenAPI'
 * <p>
 * Java-Class is used for this because currently it seemed it is not 100% working correctly: <a href="https://github.com/spring-projects/spring-boot/issues/8762">GitHub Issue</a>
 * Also IntelliJ seems to have problems to provide autocompletion for the properties when using a Kotlin-Class.
 */
@Configuration
@ConfigurationProperties("draw-api")
public class LottozahlenAPIConfigProps {
    private String westlottoRSSFeedUrl;
    private UpdateSchedulingConfigProps autoUpdate;

    class UpdateSchedulingConfigProps {
        private boolean enabled;
        private String cronSchedule;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getCronSchedule() {
            return cronSchedule;
        }

        public void setCronSchedule(String cronSchedule) {
            this.cronSchedule = cronSchedule;
        }
    }

    public String getWestlottoRSSFeedUrl() {
        return westlottoRSSFeedUrl;
    }

    public void setWestlottoRSSFeedUrl(String westlottoRSSFeedUrl) {
        this.westlottoRSSFeedUrl = westlottoRSSFeedUrl;
    }

    public UpdateSchedulingConfigProps getAutoUpdate() {
        return autoUpdate;
    }

    public void setAutoUpdate(UpdateSchedulingConfigProps autoUpdate) {
        this.autoUpdate = autoUpdate;
    }
}
