package org.laorui_out.habit_former.poster.controller.controller;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@RestController
public class FileController {
    //private static final String UPLOADED_FOLDER = System.getProperty("user.dir")+"/upload/";
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String upLoad(MultipartFile picture, HttpServletRequest request) throws IOException {
        //System.out.println("文件大小"+photo.getSize());
        //System.out.println(photo.getContentType());
        //System.out.println(photo.getOriginalFilename());
        //System.out.println(System.getProperty("user.dir")); 这条属性实际上用不到
        //获得web服务器的运行目录，获取服务器对应的路径，用httpservletrequest request
        String path = request.getServletContext().getRealPath("/upload/");
        System.out.println(path);
        //把上述图片文件保存到对应路径
        saveFile(picture,path);
        return path;
    }

    public void saveFile(MultipartFile picture,String path) throws IOException {
        //判断目录是否存在，不存在则重建
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdir();
        }
        File file = new File(path + picture.getOriginalFilename());
        picture.transferTo(file);
    }
}