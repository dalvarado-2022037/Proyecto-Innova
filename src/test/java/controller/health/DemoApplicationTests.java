package controller.health;

import org.douglasalvarado.DemoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringBootTest(classes = DemoApplication.class)
class DemoApplicationTests {

	@MockBean
    private UserDetailsService userDetailsService;

	@Test
	void contextLoads() {
	}

}
