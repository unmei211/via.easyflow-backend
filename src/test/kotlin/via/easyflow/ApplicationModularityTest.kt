package via.easyflow

import org.junit.jupiter.api.Test
import org.springframework.modulith.core.ApplicationModules
import org.springframework.modulith.docs.Documenter

class ApplicationModularityTest {
    private val modules: ApplicationModules = ApplicationModules.of(EasyflowApplication::class.java)

    @Test
    fun verifiesModularStructure() {
        modules.verify()
    }

    @Test
    fun createModuleDocumentation() {
        Documenter(modules)
            .writeDocumentation()
            .writeIndividualModulesAsPlantUml()
    }

    @Test
    fun createApplicationModuleModel() {
        modules.forEach(System.out::println)
    }
}