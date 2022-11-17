pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "exchange-rate"
include (":app")
include(":core:network")
include(":core:model")
include(":feature:rates")
include(":feature:converter")
include(":core:data")
