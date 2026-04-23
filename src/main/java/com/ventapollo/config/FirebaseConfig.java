package com.ventapollo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.json.path}")
    private String jsonPath;

    @Value("${firebase.json.file}")
    private String jsonFile;

    @Value("${firebase.bucket.name}")
    private String bucketName;

    @PostConstruct
    public void inicializar() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            // Primero intenta leer desde resources (classpath)
            InputStream credenciales = getClass()
                    .getClassLoader()
                    .getResourceAsStream(jsonPath + "/" + jsonFile);

            // Si no está en classpath, intenta como ruta absoluta
            if (credenciales == null) {
                throw new IllegalStateException(
                    "No se encontró el archivo de credenciales Firebase en classpath: " 
                    + jsonPath + "/" + jsonFile);
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(credenciales))
                    .setStorageBucket(bucketName)
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("✅ Firebase inicializado correctamente. Bucket: " + bucketName);
        }
}
}
