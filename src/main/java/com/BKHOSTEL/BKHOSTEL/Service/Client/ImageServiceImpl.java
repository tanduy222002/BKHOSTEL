package com.BKHOSTEL.BKHOSTEL.Service.Client;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    private Cloudinary cloudinary;

    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public void upLoadImageWithFile( MultipartFile file) throws IOException {
        try{
        Map data= cloudinary.uploader().upload(file.getBytes(), Map.of());
            System.out.println(data);
        }
        catch(IOException e){
            throw new RuntimeException("Error when uploading image file");
        };

    }

    @Override
    public String upLoadImageWithBase64(String base64Image) throws IOException {
        try{
            // prefix "data:image/jpeg;base64," + code
            Map data= cloudinary.uploader().upload(base64Image, Map.of());
            System.out.println(data);
            return data.get("secure_url").toString();
        }
        catch(IOException e){
            throw new IOException("Error when uploading image file");
        }
    }

    @Override
    public void deleteImage(String id) {

    }
}
