package bc_demo.consistencyWS;

import bc_demo.encrypt.SimpleMerkleTree;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.testng.util.Strings;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


/**
 * 基于Spring Boot 2.0的WebSocket客户端
 *
 * @author ZhaoGao
 * @date 2020/8/12 - 18:10 - JavaProjects
 */
@Component
public class P2pPointPBFTClient {
    //日志记录
    private Logger logger = LoggerFactory.getLogger(P2pPointPBFTClient.class);

    //P2P网格中的节点既是服务端，又是客户端。作为服务端运行在7001端口，同时作为客户通过ws://localhost:7001连接到服务端
    private String wsUrl = "ws://localhost:7001/";

    //所有客户端WebSocket的连接池缓存
    private List<WebSocket> localSockets = new ArrayList<>();

    public List<WebSocket> getLocalSockets() {
        return localSockets;
    }

    public void setLocalSockets(List<WebSocket> localSockets) {
        this.localSockets = localSockets;
    }

    /**
     * 连接到服务端
     */
    @PostConstruct
    @Order(2)
    public void connectPeer() {
        try {
            //创建WebSocket 的客户端
            final WebSocketClient socketClient = new WebSocketClient(new URI(wsUrl)) {

                /**
                 * Called after an opening handshake has been performed and the given websocket is ready to be written on.
                 *
                 * @param handshakeData The handshake of the websocket instance
                 */
                @Override
                public void onOpen(ServerHandshake handshakeData) {
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
                    logger.info("北京客户端收到北京服务服务端发送的消息" + message);

                    //如果收到的不是JSON化数据，则说明不是PBFT阶段
                    if (!message.startsWith("{")) {
                        return;
                    }

                    //如果收到的是JSON化数据 则说明是PBFT阶段
                    //如果是JSON化数据，则进入到了PBFT投票阶段
                    JSONObject json = JSON.parseObject(message);
                    if (!json.containsKey("code")) {
                        logger.info("背景客户端收到非JSON化数据");
                    }

                    int code = json.getIntValue("code");
                    if (code == VoteEnum.PRE_PREPARE.getCode()) {
                        //校验hash
                        VoteInfo voteInfo = JSON.parseObject(message,  VoteInfo.class);
                        if (!voteInfo.getHash().equals(SimpleMerkleTree.getTreeNodeHash(voteInfo.getList()))) {
                            logger.info("北京客户端收到北京服务端错误的JSON化数据");
                            return;
                        }

                        //校验成功，检查节点确认个数是否有效
                        if (getConnectedNodeCount() >= getLeastNodeCount()) {
                            sendMessage(this, "北京客户端开始区块入库啦");
                            logger.info("北京客户端开始区块入库啦");
                        }
                    }
                }

                /**
                 * Called after the websocket connection has been closed.
                 *
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
            //客户端开始连接服务器
            socketClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private int getConnectedNodeCount() {
        //本机测试时，写死为1 实际开发部署多个节点时，按实际情况返回
        return 1;
    }

    //PBFT消息节点最少确认个数计算
    private int getLeastNodeCount() {
        //本机测试时，写死为1，实际开发部署多个节点时，PBFT算法中拜占庭节点数量为f
        //总节点数量为2f+1
        return 1;
    }


    //根据VoteEnum构建对应状态的VoteInfo
    private VoteInfo createVoteInfo(VoteEnum ve) {
        VoteInfo vi = new VoteInfo();
        vi.setCode(ve.getCode());

        List<String> list = new ArrayList<>();
        list.add("AI");
        list.add("BlockChain");

        vi.setList(list);
        vi.setHash(SimpleMerkleTree.getTreeNodeHash(list));

        return vi;
    }


    /**
     * 向服务端发送消息，当前WebSocket的原车搞Socket地址就是服务器
     */
    public void sendMessage(WebSocket ws, String message) {
        logger.info("发送给" + ws.getRemoteSocketAddress().getPort() +"的P2P消息：" + message);
        ws.send(message);
    }


    /**
     * 向所有连杰锅的服务端广播消息
     */
    public void broadcast(String message) {
        if (localSockets.size() == 0 || Strings.isNullOrEmpty(message)) {
            return;
        }
        logger.info("开始广播");
        for (WebSocket ws : localSockets) {
            this.sendMessage(ws, message);
        }
        logger.info("广播结束");
    }


}
