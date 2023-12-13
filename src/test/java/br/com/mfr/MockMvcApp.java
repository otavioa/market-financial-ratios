package br.com.mfr;

import de.flapdoodle.embed.mongo.spring.autoconfigure.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

import static br.com.mfr.MockMvcProfile.MOCK_MVC_PROFILE;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SpringBootTest
@ActiveProfiles(MOCK_MVC_PROFILE)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, EmbeddedMongoAutoConfiguration.class})
public @interface MockMvcApp {

}
