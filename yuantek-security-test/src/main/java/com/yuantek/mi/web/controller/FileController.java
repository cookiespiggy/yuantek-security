package com.yuantek.mi.web.controller;

import com.yuantek.mi.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.Instant;

import static com.yuantek.mi.utils.ObjectUtil.pretty;

@RestController
@RequestMapping("/file")
public class FileController {

    private final String folder = "/Users/mizhe/job/workspaces/yuantek-security/yuantek-security-test/src/main/java/com/yuantek/mi/web/controller";


    @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException {
        pretty(file);
        File localFile = new File(folder, Instant.now().toEpochMilli() + ".txt");
        //file.getInputStream() 可以用来上传到阿里云或FaseDFS等
        file.transferTo(localFile);
        return new FileInfo(localFile.getAbsolutePath());
    }

    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        try (
                InputStream inputStream = new FileInputStream(new File(folder, id + ".txt"));
                OutputStream outputStream = response.getOutputStream();
        ) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (Exception e) {

        }
    }

}
