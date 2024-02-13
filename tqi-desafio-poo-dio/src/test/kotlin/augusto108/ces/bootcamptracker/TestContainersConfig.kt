package augusto108.ces.bootcamptracker

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.MapPropertySource
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.lifecycle.Startables

@ContextConfiguration(initializers = [TestContainersConfig.Initializer::class])
abstract class TestContainersConfig {

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            startContainer()

            val environment: ConfigurableEnvironment = applicationContext.environment
            val testContainer = MapPropertySource("testContainer", createConnectionConfiguration())

            environment.propertySources.addFirst(testContainer)
        }
    }

    companion object {

        private val mysql: MySQLContainer<*> = MySQLContainer("mysql:8.0.33")

        fun startContainer() {
            Startables.deepStart(mysql).join()
        }

        fun createConnectionConfiguration(): Map<String, Any> {
            return mapOf(
                Pair("spring.datasource.url", mysql.jdbcUrl),
                Pair("spring.datasource.username", mysql.username),
                Pair("spring.datasource.password", mysql.password)
            )
        }
    }
}