plugins {
    id("com.microej.gradle.application") version "1.3.1"
}

group="com.mycompany"
version="0.1.0-RC"

microej {
    applicationEntryPoint = "com.mycompany.myapplication.Main"

    // Uncomment to use "prod" architecture when using a VEE Port (defaults to "eval")
    // architectureUsage = "prod"
}

dependencies {
    implementation("ej.api:edc:1.3.7")
    implementation("ej.api:bon:1.4.4")

    //Uncomment the microejVee dependency to set the VEE Port or Kernel to use
    //microejVee("com.mycompany:myvee:1.0.0")
}

testing {
   suites {
      val test by getting(JvmTestSuite::class) {
         microej.useMicroejTestEngine(this)

         dependencies {
             implementation(project())
             implementation("ej.api:edc:1.3.7")
             implementation("ej.api:bon:1.4.4")
             implementation("ej.library.test:junit:1.12.0")
         }
      }
   }
}
