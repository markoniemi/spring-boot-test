package org.survey;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
    HomeControllerIT.class,
    LoginControllerIT.class,
    SpringBootWebIT.class,
    UserServiceIT.class
})
public class SpringBootIntegrationTestSuite {
}
