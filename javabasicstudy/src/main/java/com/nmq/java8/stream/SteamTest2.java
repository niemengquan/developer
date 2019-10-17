package com.nmq.java8.stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * @author niemengquan
 * @create 2019/5/28
 * @modifyUser
 * @modifyDate
 */
public class SteamTest2 {
    public static void main(String[] args) throws IOException {
        Path path = FileSystems.getDefault().getPath("D:/2015", "access.txt");
        Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8);
//        lines.forEach(word -> System.out.println(word));
        Stream<String> words = lines.flatMap(line -> Stream.of(line.split(" +")));
        words.forEach(word -> System.out.println(word));
        String line = "0:0:0:0:0:0:0:1 - - [26/Mar/2019:11:20:50 +0800] \"GET / HTTP/1.1\" 404 -";
        String[] split = line.split(" +");
        System.out.println(split.toString());
    }
}