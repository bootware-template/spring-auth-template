package jp.bootware.template.springauthbackend;

import jp.bootware.template.springauthbackend.infrastructure.authentication.AuthenticationProperty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringAuthBackendApplicationTests {

	@Autowired
	AuthenticationProperty authenticationProperty;

	@Test
	void contextLoads() {
	}

}
