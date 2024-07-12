package org.laorui_out.habit_former.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
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
        try{
            // 这样就可以收到文件了，files.length == 1
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String newFileName = System.currentTimeMillis() + "_" + originalFilename;
            String path = request.getServletContext().getRealPath("/uploadFile/");
            saveFile(file, path, newFileName);
            System.out.println(path);
            return "http://121.40.132.245:8080/habit_former/uploadFile/" + newFileName;

        }catch(Exception e){
            System.out.println(e.getMessage());
            return "上传失败";
        }

    }

    public void saveFile(MultipartFile picture, String path, String newName) throws IOException {
        File dir = new File(path);
        //判断目录是否存在，不存在则重建
        if(!dir.exists()){
            dir.mkdir();
        }

        File file = new File(path + newName);
        picture.transferTo(file);
    }
}
