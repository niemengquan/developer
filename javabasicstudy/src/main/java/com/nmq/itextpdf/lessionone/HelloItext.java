package com.nmq.itextpdf.lessionone;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 *
 * Itext 生成pdf 插入图片 入门
 * @author 聂孟泉
 * @create 2018-06-28
 * @modifyUser
 * @modifyDate
 */
public class HelloItext {
    public static void main(String[] args) {
        // PDF文件和图片文件路径

        String filePath = "D://2015//test.pdf";

        String imagePath = "D://2015//test02.jpg";


        // 先创建一个Document文档对象

        Document document = new Document();

        try {
            PdfWriter.getInstance(document,new FileOutputStream(filePath));
            // 添加PDF文档的某些信息，比如作者，主题等等

            document.addAuthor("niemengquan");

            document.addSubject("HELLO PDF.");

            // 设置文档的大小
            document.setPageSize(PageSize.A4);
            // 打开文档
            document.open();
            // 写入一段文字
            document.add(new Paragraph("JUST TEST ..."));
            // 读取一个图片
            Image image = Image.getInstance(imagePath);
            float height = image.getHeight();
            float width = image.getWidth();
            System.out.println("heigth："+"----"+height);
            System.out.println("width："+"-----"+width);
            //设置图片居中显示
            image.setAlignment(Image.MIDDLE);
            //合理压缩，h>w，按w压缩，否则按w压缩
            //int percent=getPercent(heigth, width);
            //统一按照宽度压缩
            int percent=getPercent(height, width);
            System.out.println("--"+percent);
            //按百分比显示图片的比例
            image.scalePercent(percent);
            // 插入一个图片
            document.add(image);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 关闭打开的pdf文档
            document.close();
        }

    }
    /**
     * 第一种解决方案
     * 在不改变图片形状的同时，判断，如果h>w，则按h压缩，否则在w>h或w=h的情况下，按宽度压缩
     * @param h
     * @param w
     * @return
     */

    public static int getPercent(float h,float w)
    {
        int p=0;
        float p2=0.0f;
        if(h>w)
        {
            p2=297/h*100;
        }
        else
        {
            p2=210/w*100;
        }
        p=Math.round(p2);
        return p;
    }


}
