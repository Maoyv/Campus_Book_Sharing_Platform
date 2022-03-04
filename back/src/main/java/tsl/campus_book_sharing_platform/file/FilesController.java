package tsl.campus_book_sharing_platform.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tsl.campus_book_sharing_platform.uitl.ResultFormat;
import tsl.campus_book_sharing_platform.uitl.ResultUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TSL
 * 文件上传下载测试
 */
@RestController
@RequestMapping("/file")
public class FilesController {

    @Value("${file.upload.url}")
    private String uploadFilePath;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResultFormat httpUpload(@RequestParam("file") MultipartFile[] file){
        // 文件名
        String fileName = file[0].getOriginalFilename();
        List<String> name = Arrays.asList(fileName.split("\\."));
        if("txt".equals(name.get(1)) == false){
            return ResultUtil.error(-1, "文件格式错误");
        }
        if(file[0].getSize() > 1024*1024*15){
            return ResultUtil.error(-1, "文件过大");
        }
        File dest = new File(uploadFilePath + '/' + fileName);
        if (dest.exists()) {
            return ResultUtil.error(-1, "该名称的电子书已存在，请勿重复上传");
        }
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file[0].transferTo(dest);
        } catch (Exception e) {
            return ResultUtil.error(-1, "程序错误，请重新上传");
        }
        String files;
        if(file[0].getSize() < 1024){
            files = file[0].getSize()+"b";
        }else if(file[0].getSize() < 1024*1024){
            files = (file[0].getSize()/1024)+"."+(file[0].getSize()%10*10/1024)+"kb";
        }else{
            files = (file[0].getSize()/1024/1024)+"."+(file[0].getSize()%1024*10/1024)+"Mb";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("result", "电子书上传成功");
        map.put("ebookName", fileName);
        map.put("ebookSize", files);
        return ResultUtil.success(map);
    }

    @RequestMapping(value = "/downloads", method = RequestMethod.GET)
    public ResultFormat fileDownLoads(String fileName) {
        if(fileName == null || "".equals(fileName)){
            return ResultUtil.error(-1, "无法下载空文件");
        }
        File file = new File(uploadFilePath + '/' + fileName);
        if (!file.exists()) {
            return ResultUtil.error(-1, "下载的电子书文件不存在");
        } else {
            return ResultUtil.success("下载的电子书文件存在");
        }
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public String fileDownLoad(HttpServletResponse response, String fileName) {
        File file = new File(uploadFilePath + '/' + fileName);
        if (!file.exists()) {
            return "下载文件不存在";
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            return "下载失败";
        }
        return "下载成功";
    }
}
