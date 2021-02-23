package com.ccc.fizz.NIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

public class NIOServiceTest {
    public static void main(String[] args) throws Exception{
        //创建channel对象，绑定端口，并使用非阻塞模式
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(8888), 1024);
        channel.configureBlocking(false);

        //channel交给selector监听
        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);

        while(true) {
            selector.select(1000);
            //获取所有准备的channel，key中包含channel信息
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();

            for (SelectionKey selectionKey : selectionKeySet) {
                System.out.println("comein");
                //失效
                if (! selectionKey.isValid()) {
                    continue;
                }

                //可接受
                if (selectionKey.isAcceptable()) {
                    acceptConnection(selectionKey, selector);
                }

                //可读
                if (selectionKey.isReadable()) {
                    System.out.println(readFromSelectionkey(selectionKey));
                }
            }

            selectionKeySet.clear();
        }
    }

    public static void acceptConnection(SelectionKey selectionKey, Selector selector) throws Exception {
        System.out.println("accept connectio");
        ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
        //通过 ServerSocketChannel.accept() 方法监听新进来的连接。当 accept()方法返回的时候,它返回一个包含新进来的连接的 SocketChannel。因此, accept()方法会一直阻塞到有新连接到达。
        SocketChannel accept = ssc.accept();
        accept.configureBlocking(false);
        //为socket通道注册选择器，设置选择其关心READ行为
        accept.register(selector, SelectionKey.OP_READ);
    }

    public static String readFromSelectionkey(SelectionKey selectionKey) throws Exception{
        //从SelectionKey中包含选取出来的Channel的信息把Channel获取出来
        SocketChannel socketChannel = ((SocketChannel) selectionKey.channel());

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int len = socketChannel.read(buffer);

        if (len < 0) {
            socketChannel.close();
            selectionKey.cancel();
            return "";
        } else if (len == 0) {
            return "";
        }

        //将缓冲器变为读状态
        buffer.flip();
        doWrite(selectionKey, "Hello Nio");
        return new String(buffer.array(), 0, len);
    }

    public static void doWrite(SelectionKey selectionKey, String responseMessage) throws Exception{
        System.err.println("Output message...");
        SocketChannel socketChannel = ((SocketChannel) selectionKey.channel());
        ByteBuffer byteBuffer = ByteBuffer.allocate(responseMessage.getBytes().length);
        byteBuffer.put(responseMessage.getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
    }
}
