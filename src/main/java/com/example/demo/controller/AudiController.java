
package com.example.demo.controller;
 
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.limit.RequestLimit;
import com.example.demo.user.UserDao;
import com.example.demo.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

 
@RestController
@RequestMapping("/audi")
public class AudiController {
 

    @Autowired
    UserService service;

    @RequestMapping("/test")
    public Map<String,Object> test(){
        Map<String,Object> map=new HashMap<>();
        map.put("msg", "访问成功！");
        map.put("code", 0);
        return map;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public Object NewUser(@RequestBody Map<String, Object> map) {
        UserDao user=new UserDao();
        user.FromMap(map);
        return service.add(user);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object saveInterested(@RequestBody Map<String, Object> map) {        
        UserDao user=new UserDao();
        user.FromMap(map);
        return service.update(user);

    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object deleteInterested(@RequestBody Map<String, Object> map) {        
        UserDao user=new UserDao();
        user.FromMap(map);
        return service.delete(user.getId());

    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequestLimit(count=10,time=60000)
    public Object interestedList(@RequestBody Map<String, Object> map) {
        String order = "";
        int page = 0;
        int limit = 10;
        if (map.containsKey("order"))
            order = map.get("order").toString();
        if (map.containsKey("page"))
            page = Integer.parseInt(map.get("page").toString());
        if (map.containsKey("limit"))
            limit = Integer.parseInt(map.get("limit").toString());
        return service.searchAll(order, page, limit);
    }

    @ResponseBody 
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Object searchList(@RequestBody Map<String, Object> map) {
        String order = "";
        String name = "";
        int page = 0;
        int limit = 10;
        if (map.containsKey("name"))
            name = map.get("name").toString();
        if (map.containsKey("order"))
            order = map.get("order").toString();
        if (map.containsKey("page"))
            page = Integer.parseInt(map.get("page").toString());
        if (map.containsKey("limit"))
            limit = Integer.parseInt(map.get("limit").toString());
        return service.searchName(name, page, limit);
    }

    @RequestMapping(value = "/searchOne", method = RequestMethod.POST)
    public Object searchOne(@RequestBody String id) {
        return service.searchId(id);
    }

    // 处理文件上传

    @ResponseBody // 返回json数据
    @RequestMapping(value = "/Upload", method = RequestMethod.POST)
    public String uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        String filePath = "D:/img";
        if (file.isEmpty()) {
            return "文件为空！";
        }
        try {
            uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            // TODO: handle exception
        }

        // 返回json

        return "上传成功";

    }

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {

        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + "/" + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

}


