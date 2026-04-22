package com.ventapollo.service;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FirebaseStorageService {

    @Value("${firebase.storage.path}")
    private String storagePath;

    @Value("${firebase.bucket.name}")
    private String bucketName;

    /**
     * Sube una imagen a Firebase Storage y retorna la URL pública permanente.
     *
     * @param archivo  El MultipartFile recibido desde el formulario
     * @param carpeta  Sub-carpeta dentro del bucket (ej: "productos", "usuarios")
     * @return URL pública de la imagen subida
     */
    public String subirImagen(MultipartFile archivo, String carpeta) throws IOException {
        // Genera un nombre único para evitar colisiones
        String extension = obtenerExtension(archivo.getOriginalFilename());
        String nombreArchivo = carpeta + "/" + UUID.randomUUID() + extension;

        // Obtiene el bucket configurado en FirebaseConfig
        var bucket = StorageClient.getInstance().bucket();

        // Crea el blob con el tipo de contenido del archivo original
        BlobInfo blobInfo = BlobInfo.newBuilder(bucket.getName(), nombreArchivo)
                .setContentType(archivo.getContentType())
                .build();

        // Sube el archivo
        Blob blob = bucket.getStorage().create(blobInfo, archivo.getBytes());

        // Hace la imagen pública (acceso de lectura para todos)
        blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        // Retorna la URL pública de Google Cloud Storage
        return String.format(
                "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media",
                bucketName,
                nombreArchivo.replace("/", "%2F")
        );
    }

    /**
     * Elimina una imagen de Firebase Storage usando su URL pública.
     * Útil al editar un producto: borrar la imagen vieja antes de subir la nueva.
     */
    public void eliminarImagen(String urlPublica) {
        try {
            if (urlPublica == null || urlPublica.isBlank()) return;

            // Extrae el nombre del archivo desde la URL
            String nombreArchivo = urlPublica
                    .replaceAll(".*firebasestorage\\.googleapis\\.com/v0/b/[^/]+/o/", "")
                    .replaceAll("\\?.*", "")
                    .replace("%2F", "/");

            var bucket = StorageClient.getInstance().bucket();
            var blob = bucket.get(nombreArchivo);
            if (blob != null && blob.exists()) {
                blob.delete();
                System.out.println("🗑️ Imagen eliminada de Firebase: " + nombreArchivo);
            }
        } catch (Exception e) {
            System.err.println("⚠️ No se pudo eliminar imagen de Firebase: " + e.getMessage());
        }
    }

    private String obtenerExtension(String nombreOriginal) {
        if (nombreOriginal != null && nombreOriginal.contains(".")) {
            return nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
        }
        return ".jpg";
    }
}
