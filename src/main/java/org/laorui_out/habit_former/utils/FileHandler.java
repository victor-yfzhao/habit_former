package org.laorui_out.habit_former.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileHandler {
    public String upLoad(MultipartFile picture, HttpServletRequest request) throws IOException {
        String path = request.getServletContext().getRealPath("/user_icon/");

        saveFile(picture, path);
        return path;
    }

    public void saveFile(MultipartFile picture, String path) throws IOException {
        File dir = new File(path);

        //判断目录是否存在，不存在则重建
        if(!dir.exists()){
            dir.mkdir();
        }

        File file = new File(path + picture.getOriginalFilename());
        picture.transferTo(file);
    }
}
