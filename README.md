En este proyecto ya se configuraron los archivos libs.version.toml
  Se agregan las versiones 
    firebaseBom = "33.1.1"
    services = "4.4.2"
    crashlytics = "3.0.2"
  Se agregaron las librerías 
    firebase-bom = { module = "com.google.firebase:firebase-bom", version.ref = "firebaseBom" }
    firebase-crashlytics = {module = "com.google.firebase:firebase-crashlytics" }
  Y se agregaron los pluguins
    googleServices = { id = "com.google.gms.google-services", version.ref = "services"}
    crashlytics = {id= "com.google.firebase.crashlytics", version.ref = "crashlytics"}
    kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
  Todo esto en su respectivo apartado

También se editó el archivo build.gradle..kts (Modeule:app)
  Se agregaron los pruguins en su apartado correspondiente
    alias(libs.plugins.googleServices)
    alias(libs.plugins.crashlytics)
  y las dependencias
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)

También se editó el archivo build.gradle..kts (Project:<Nombre del app>)
  Se agregaron los pruguins en su apartado correspondiente
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.crashlytics) apply false

Se configuró el proyecto en firebase con el id de aplicación y se generó el archivo google-services.json 
El cual se ingresó en la vista de proyecto dentro de la carpeta app

Hasta este punto se ejecutó la aplicación y en la consolo de firebase apareción una leyenda que confirmaba que se detectó la aplicación, sólo hacía falta ejecutar una falla de prueba.
Y se ingresó el código sugerido por firebase para ejecutar la prueba en el archivo MainActivity.kt
   val crashButton = Button(this)
        crashButton.text = "Test Crash"
        crashButton.setOnClickListener {
            throw RuntimeException("Test Crash") // Force a crash
        }

        addContentView(crashButton, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))

Para oder ejecutarlo se solicitará el import de 2 librerías, el mismo IDE lo sugiere.

