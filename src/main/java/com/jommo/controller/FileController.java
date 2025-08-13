package com.jommo.controller;

import com.jommo.common.Result;
import com.jommo.util.AliOssUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@CrossOrigin
public class FileController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        //把文件的内容存储到本地磁盘上
        String originalFilename = file.getOriginalFilename();
        //保证文件的名字是唯一的，从而防止文件覆盖
        String filename = (UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."))).replaceAll("-", "");
        //file.transferTo(new File("D:/Documents/files/" + filename));
        String url = AliOssUtil.uploadFile(filename, file.getInputStream());
        return Result.success(url);
    }

    @PostMapping("/deleteFile")
    public Result deleteFile(@RequestParam String url) {
        if (AliOssUtil.deleteFile(url.substring(url.lastIndexOf("/") + 1))) {
            return Result.success();
        }
        return Result.error("操作异常");
    }


}
