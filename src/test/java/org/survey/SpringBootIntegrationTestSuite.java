package org.survey;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
    ActuatorIT.class,
    LoginControllerIT.class,
    SpringBootWebIT.class,
    UserServiceIT.class
})
public class SpringBootIntegrationTestSuite {
}
