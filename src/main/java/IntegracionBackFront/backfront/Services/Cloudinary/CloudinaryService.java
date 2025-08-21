package IntegracionBackFront.backfront.Services.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Service
public class CloudinaryService {

    //1.Constante para definir el tamaño de de los archivos (5MB)
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    //2.Definir las extensiones de archivos permitidas
    private static final String[]ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png"};

    //3. Cliente de clou inyectado como dependencia
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    //Subir imagenes a la raiz de clou
    public String uploadImage(MultipartFile file)throws IOException {
        validateImage(file);
        Map<?, ?> uploadResult = cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.asMap(
                        "resourse_type", "auto",
                        "quality", "auto:good"
                ));
        return (String) uploadResult.get("secure_url");
    }

    private void validateImage(MultipartFile file) {
        //1.verificar si esta vacio
        if(file.isEmpty())throw new IllegalArgumentException("El archivo no puede estar vacio.");
        //2.verificar si el tamaño excede el limite permitido
        if(file.getSize() > MAX_FILE_SIZE)throw new IllegalArgumentException("El tamaño del archivo no debe ser mayor a 5MB");
        String originalFilename = file.getOriginalFilename();
        if(originalFilename == null) throw new IllegalArgumentException("Nombre no valido");
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(ALLOWED_EXTENSIONS).contains(extension))throw new IllegalArgumentException("Archivo no permitido");
        if (!file.getContentType().startsWith("image/"))throw new IllegalArgumentException("El archivo debe ser una imagen valida");

    }


    //Subir imagenes a una carpeta de clou

}
