package bc_demo.p2pwebsocket;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.testng.util.Strings;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * 基于Spring boot 2.0的websocket端
 *
 * @author ZhaoGao
 * @date 2020/8/10 - 19:24 - JavaProjects
 */
public class P2pPointClient {

    //日志记录
    private Logger logger =
            LoggerFactory.getLogger(P2pPointClient.class);

    //P2P网络的节点既是服务器又是客户端 作为服务端运行在7001端口
    //P2PpointServer的port字段 同时作为客户端通过 xs://localhost:7001
    //连接到服务端
    private String wsUrl = "ws://localhost:7001/";

    //所有客户端的WebSocket连接池缓存
    private List<WebSocket> localSockets = new ArrayList<>();

    public List<WebSocket> getLocalSockets() {
        return localSockets;
    }

    public void setLocalSockets(List<WebSocket> list) {
        localSockets = list;
    }

    /**
     * 连接到服务端
     */
    @PostConstruct
    @Order(2)
    public void connectPeer() {
        try {
            //创建WebSocket客户端
            final WebSocketClient socketClient = new WebSocketClient(new URI(wsUrl)) {

                /**
                 * Called after an opening handshake has been performed and the given websocket is ready to be written on.
                 *
                 * @param handshakedata The handshake of the websocket instance
                 */
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    sendMessage(this, "北京客户端成功创建客户端");

                    localSockets.add(this);
                }

                /**
                 * Callback for string messages received from the remote host
                 *
                 * @param message The UTF-8 decoded message that was received.
                 **/
                @Override
                public void onMessage(String message) {
                    logger.info("北京客户端收到北京服务端发送的消息" + message);
                }

                /**
                 * Called after the websocket connection has been closed.
                 *
                 * @param code   The codes can be looked up here:
                 * @param reason Additional information string
                 * @param remote
                 **/
                @Override
                public void onClose(int code, String reason, boolean remote) {
                    logger.info("北京客户端关闭");
                    localSockets.remove(this);
                }

                /**
                 * Called when errors occurs. If an error causes the websocket connection to fail {@link #onClose(int, String, boolean)} will be called additionally.<br>
                 * This method will be called primarily because of IO or protocol errors.<br>
                 * If the given exception is an RuntimeException that probably means that you encountered a bug.<br>
                 *
                 * @param ex The exception causing this error
                 **/
                @Override
                public void onError(Exception ex) {
                    logger.info("北京客户端报错");
                    localSockets.remove(this);
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    /**
     * 向服务端发送消息，当前WebSocket的远程Socket地址是服务端
     *
     */
    public void sendMessage(WebSocket ws, String message) {
        logger.info("发送给" + ws.getRemoteSocketAddress().getPort() + "的P2P消息是： " + message);
        ws.send(message);
    }

    /**
     * 向所有连接过的服务端广播消息
     *
     * 所谓广播 就是一个一个给节点发消息？？？？？ 这么草率？？
     */
    public void broadcast(String message) {
        if (localSockets.size() == 0 || Strings.isNullOrEmpty(message)) {
            return;
        }
        logger.info("非常欢迎的告诉你已经开始广播啦");
        for (WebSocket socket : localSockets) {
            this.sendMessage(socket,  message);
        }
        logger.info("广播完毕");
    }

}
