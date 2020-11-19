package com.bguf;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BlogApplicationTests
{
    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Test
    void contextLoads() throws Exception
    {
        ClassPathResource classPathResource = new ClassPathResource("static/images/qq.png");
        String fileName = classPathResource.getFilename();
        String extName = fileName.substring(fileName.lastIndexOf(".")+1);
        InputStream inputStream = classPathResource.getInputStream();
        StorePath storePath = fastFileStorageClient.uploadFile(inputStream, classPathResource.getFile().length(), extName, null);
        System.out.println("fullpath:"+storePath.getFullPath());
        System.out.println("path:"+storePath.getPath());
        System.out.println("group:"+storePath.getGroup());
    }

    @Test
    public void test1()
    {
        Person person = new Person();
        person.content = "jintian";
        Person person1 = new Person();
        person1.content = "mingtian";
        Person person2 = new Person();
        person2.content = "houtian";
        List<Person> list = new ArrayList<>();
        list.add(person);
        list.add(person1);
        list.add(person2);
        for (Person p : list)
        {
           p.des = "ok";
        }
        System.out.println(list.toString());
    }

    class Person
    {
        private String content;
        private String des;

        @Override
        public String toString()
        {
            return "Person{" +
                    "content='" + content + '\'' +
                    ", des='" + des + '\'' +
                    '}';
        }
    }
}