plugins {
    id("com.microej.gradle.application") version "0.19.0"
}

group="com.mycompany"
version="0.1.0-RC"

microej {
  applicationEntryPoint = "com.mycompany.application.Main"
}

dependencies {
    implementation("ej.api:edc:1.3.5")

    //Uncomment the microejVee dependency to set the VEE Port or Kernel to use
    //microejVee("com.mycompany:myvee:1.0.0")
}

testing {
   suites {
      val test by getting(JvmTestSuite::class) {
         microej.useMicroejTestEngine(this)

         dependencies {
             implementation(project())
             implementation("ej.api:edc:1.3.5")
             implementation("ej.library.test:junit:1.7.1")
             implementation("org.junit.platform:junit-platform-launcher:1.8.2")
         }
      }
   }
}
