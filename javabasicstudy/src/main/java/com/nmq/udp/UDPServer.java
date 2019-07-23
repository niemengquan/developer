package com.nmq.udp;

import java.io.IOException;
import java.net.*;

/**
 * @author niemengquan
 * @create 2019/7/23
 * @modifyUser
 * @modifyDate
 */
public class UDPServer {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(8001)) {
            /**
             * 接收客户端发送的数据
             */
            byte[] data = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(data,0,data.length);
            System.out.println("服务器已经启动，等待客户端发送数据");
            socket.receive(datagramPacket);
            //开始读取数据
            String info = new String(data,0,datagramPacket.getLength());
            System.out.println("收到客户端消息：" + info);

            /**
             * 向客户端发送消息
             */
            InetAddress clientAddress = datagramPacket.getAddress();
            int clientPort = datagramPacket.getPort();
            byte[] bytes = "欢迎登录系统".getBytes();
            DatagramPacket sendClientPacket = new DatagramPacket(bytes,bytes.length,clientAddress,clientPort);
            socket.send(sendClientPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
