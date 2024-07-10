package org.laorui_out.habit_former.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

//这里的地址还是要相对修改一下，包括保存的地址！！！！！！
@RestController
public class FileHandler {
    @PostMapping("/uploadFile")
    public String uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException{
        // 这样就可以收到文件了，files.length == 1
        String path = request.getServletContext().getRealPath("/uploadFile/");
        saveFile(file, path);
        System.out.println(path);
        return path + file.getOriginalFilename();
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
