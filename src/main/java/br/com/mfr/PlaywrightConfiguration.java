package br.com.mfr;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Configuration
public class PlaywrightConfiguration {

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public PlaywrightProvider playwrightPage() {
      return new PlaywrightProvider();
    }

    public static class PlaywrightProvider {

        private final Playwright playwright;
        private final Browser browser;
        private Page page;

        public PlaywrightProvider(){
            this.playwright = Playwright.create();
            this.browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false)
            );
        }

        public Page newPage() {
            if(this.page == null)
                this.page = browser.newPage();

            return this.page;
        }

        public void close() {
            browser.close();
            playwright.close();
        }
    }
}
