package bc_demo.p2pwebsocket;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.testng.util.Strings;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * 基于springboot 2.0的Websocke服务器
 *
 * @author ZhaoGao
 * @date 2020/8/8 - 16:23 - JavaProjects
 */

@Component
public class P2pPointServer {
    //日志记录
    private Logger logger = LoggerFactory.getLogger(P2pPointServer.class);

    //本机Server的Websocket端口
    //多机测试的时候可以改变这个值
    private int port = 7001;

    //所有连接到服务端的WebSocket缓存器
    private List<WebSocket> localSockets = new ArrayList<>();

    public List<WebSocket> getLocalSockets() {
        return localSockets;
    }

    public void setLocalSockets(List<WebSocket> localSockets) {
        this.localSockets = localSockets;
    }

    /**
     * 初始化 P2P Server端
     *
     */
    @PostConstruct
    @Order(1)
    public void initServer() {
        /**
         * 初始化WebSocket的服务端定义内部类对象socketServer
         * 源于Websocket： new
         * InetSocketAddress(port)是WebSocketServer构造器的参数
         * InetSocketAddress是（IP地址+端口号）烈性 即端口地址类型
         *
         */
        final WebSocketServer socketServer = new WebSocketServer(new InetSocketAddress(port)) {

            /**
             *重写五个方法 事件触发的时候触发这五个方法
             */

            @Override
            public void onOpen(WebSocket webSocket, ClientHandshake handshake) {
                sendMessage(webSocket, "北京服务端成功创建连接");
                //当成功创建一个WebSocket连接时，将该连接加入连接池
                localSockets.add(webSocket);
            }

            @Override
            public void onClose(WebSocket webSocket, int code, String reason, boolean remote) {
                //断开连接触发
                logger.info(webSocket.getRemoteSocketAddress() + "客户端与服务端断开连接！");

                //客户端断开连接时，WebSocket连接池删除该链接
                localSockets.remove(webSocket);
            }

            @Override
            public void onMessage(WebSocket webSocket, String message) {
                logger.info("服务端接收到客户端消息" + message);
                sendMessage(webSocket, "收到消息");
            }

            @Override
            public void onError(WebSocket webSocket, Exception ex) {
                logger.info(webSocket.getRemoteSocketAddress() + "客户端连接错误！");
                localSockets.remove(webSocket);
            }


            @Override
            public void onStart() {
                logger.info("北京的WebSocket Server端启动...");
            }
        };

        socketServer.start();
        logger.info("北京客户端监听 socketServer端口:" + port );

    }


    /**
     * 向连接到本机的某客户端发送消息
     *
     * @param websocket
     * @param message
     */
    public void sendMessage(WebSocket websocket, String message) {
        logger.info("发送给" + websocket.getRemoteSocketAddress() + "的P2P消息是：" + message);
        websocket.send(message);
    }

    public void broadcast(String message) {
        if (localSockets.size() == 0 || Strings.isNullOrEmpty(message)) {
            return;
        }

        logger.info("很荣幸广播给所有已经连接上的客户端们");

        for (WebSocket webSocket : localSockets) {
            this.sendMessage(webSocket, message);
        }
        logger.info("广播已经结束啦！");
    }

}
