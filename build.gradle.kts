plugins {
    id("com.microej.gradle.application") version "1.7.0"

    // Uncomment this plugin and the spdxSbom block at the end of this file to generate an SBOM in SPDX format
    // See https://docs.microej.com/en/latest/SDK6UserGuide/generateSbom.html
    //id("org.spdx.sbom") version "0.12.0"
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

//Uncomment this block and the org.spdx.sbom plugin above to generate an SBOM in SPDX format,
//then run "./gradlew spdxSbom". The SBOM files are written under "build/spdx/".
//See https://docs.microej.com/en/latest/SDK6UserGuide/generateSbom.html
//spdxSbom {
//    targets {
//        create("release") {
//            configurations.set(listOf("microejSbomClasspath"))
//
//            document {
//                name.set("My Product SBOM")
//                namespace.set("https://my.company.org/spdx/")
//                creator.set("Organization: My Company")
//                packageSupplier.set("Organization: My Company")
//            }
//
//            // Uncomment the line below to ignore SDK 5 modules
//            //ignoreNonMavenDependencies.set(true)
//        }
//    }
//}
