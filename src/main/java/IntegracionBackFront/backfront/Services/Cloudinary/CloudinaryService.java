package IntegracionBackFront.backfront.Services.Cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Service
public class CloudinaryService {

    //1. definir el tamaño de las imagenes en MB
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
    //2.definir las extensiones
    private static final String[]ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png"};
    //3. atributo clou
    private final Cloudinary cloudinary;
    //constructor de cloudinary
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file)throws IOException {
        validateImage(file);

    }

    private void validateImage(MultipartFile file) {
        //1.verificar si esta vacio
        if(file.isEmpty()){
            throw new IllegalArgumentException("El archivo esta vacio.");
        }
        //2.verificar si el tamaño excede el limite permitido
        if(file.getSize() > MAX_FILE_SIZE){
            throw new IllegalArgumentException("El tamaño del archivo no debe ser mayor a 5MB");
        }

        //3.verificar el nombre original del archivo
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null){
            throw new IllegalArgumentException("Nombre del archivo invalido");
        }

        //4.extraer y validar la extension del archivo
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(ALLOWED_EXTENSIONS).contains(extension)){
            throw new IllegalArgumentException("Solo se permiten archivos ");
        }

        if (!file.getContentType().startsWith("image/")){
            throw new IllegalArgumentException("El archivo debe ser imagen valida");
        }
    }
}

