package com.bguf.util;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author
 * @Description
 * @Date 10:48 下午 2020/10/13
 */
@Component
public class FdfsFileUpload
{
    @Autowired
    private FastFileStorageClient fastFileStorageClient;
    private String server = "http://47.100.92.117:9999/";

    public String upload(MultipartFile file) throws Exception
    {
        String filename = file.getOriginalFilename();
        String extName = filename.substring(filename.lastIndexOf(".")+1);
        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), extName, null);
        return server+storePath.getFullPath();
    }
}
