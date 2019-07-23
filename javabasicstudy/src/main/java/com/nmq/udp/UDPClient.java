package com.nmq.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * @author niemengquan
 * @create 2019/7/23
 * @modifyUser
 * @modifyDate
 */
public class UDPClient {
    public static void main(String[] args) {

        try (DatagramSocket socket = new DatagramSocket()) {
            /**
             * 向服务端发送消息
             */
            byte[] sendData = "用户名:admin;密码:123456".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, new InetSocketAddress("localhost",8001));
            socket.send(sendPacket);

            /**
             * 接收服务器端的数据
             */
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
            //接收服务端的数据
            socket.receive(receivePacket);
            // 读取接收到数据
            String info = new String(receiveData,0,receiveData.length);
            System.out.println("接收到服务端数据：" + info);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
