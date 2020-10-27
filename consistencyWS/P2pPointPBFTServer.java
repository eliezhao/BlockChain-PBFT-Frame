package bc_demo.consistencyWS;

import bc_demo.encrypt.SimpleMerkleTree;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.testng.util.Strings;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhaoGao
 * @date 2020/8/12 - 15:53 - JavaProjects
 */
public class P2pPointPBFTServer {

    //日志记录
    private final Logger logger = LoggerFactory.getLogger(P2pPointPBFTServer.class);

    //本机Server的Websocket端口
    //多机测试时可改变该值
    private final int port = 7001;

    //所有连接到服务端的WebSocket缓存器
    private List<WebSocket> localSockets = new ArrayList<>();

    public List<WebSocket> getLocalSockets() {
        return localSockets;
    }

    public void setLocalSockets(List<WebSocket> localSockets) {
        this.localSockets = localSockets;
    }

    /**
     * 初始化P2P Server端
     */
    @PostConstruct
    @Order(1)
    public void initServer() {

        /**
         * 初始化WebSocket的服务端，定义内部类对象socketServer，
         * 源于WebSocketServer
         * InetSocketAddress(port)是WebSocketServer构造器的参数 InetSocketAddress是
         * IP地址+端口号类型 即端口地址类型
         */
        final WebSocketServer socketServer = new WebSocketServer(new InetSocketAddress(port)) {
            //创建连接时触发
            @Override
            public void onOpen(WebSocket webSocket, ClientHandshake handshake) {
                sendMessage(webSocket, "北京服务端成功创建连接");

                //成功创建一个WebSocket连接时，将该链接加入连接池
                localSockets.add(webSocket);
            }

            //断开链接时触发
            @Override
            public void onClose(WebSocket webSocket, int code, String reason, boolean remote) {
                logger.info(webSocket.getRemoteSocketAddress() + "客户端与服务器断开连接！");

                //当客户端断开连接时，WebSocket连接池删除该链接
                localSockets.remove(webSocket);
            }

            @Override
            public void onMessage(WebSocket webSocket, String message) {
                logger.info("北京服务端接收到客户端消息：" + message);

                //收到入库的消息则不再发送
                if ("北京客户端开始区块入库啦".equals(message)) {
                    return;
                }

                //如果收到的不是JSON化数据，则说明仍处在双方建立连接的过程中
                //目前连接已经建立完毕，发起投票
                if (!message.startsWith("{")) {
                    VoteInfo vi = createVoteInfo(VoteEnum.PRE_PREPARE);

                    sendMessage(webSocket, JSON.toJSONString(vi));
                    logger.info("北京服务端发送到客户端的PBFT消息：" + JSON.toJSONString(vi));
                    return;
                }

                //如果是JSON化数据，则表明已经进入到PBFT投票阶段
                JSONObject json = JSON.parseObject(message);
                if (!json.containsKey("code")) {
                    logger.info("北京客户端收到非JSON化数据");
                }

                int code = json.getIntValue("code");
                if (code == VoteEnum.PREPARE.getCode()) {
                    //校验hash
                    VoteInfo voteInfo = JSON.parseObject(message, VoteInfo.class);
                    if (!voteInfo.getHash().equals(SimpleMerkleTree.getTreeNodeHash(voteInfo.getList()))) {
                        logger.info("北京服务端收到错误的JSON化数据");
                        return;
                    }

                    //校验成功，发送下一个状态的数据
                    VoteInfo vi = createVoteInfo(VoteEnum.COMMIT);
                    sendMessage(webSocket,  JSON.toJSONString(vi));

                    logger.info("北京服务端发送到客户端PBFT信息：" +
                            JSON.toJSONString(vi));
                }

            }

            @Override
            public void onError(WebSocket webSocket, Exception ex) {
                logger.info(webSocket.getRemoteSocketAddress() + "客户端连接错误!");
                localSockets.remove(webSocket);
            }

            @Override
            public void onStart() {
                logger.info("北京服务端监听serverSocket端口" + port);
            }
        };
    }

    //根据VoteEnum构建对应的VoteInfo
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
     * 向连接到本机的某客户端发送消息
     *
     *
     */
    public void sendMessage(WebSocket ws, String message) {
        logger.info("发送给 " + ws.getRemoteSocketAddress().getPort() +
                "的P2P消息是：" + message);
        ws.send(message);
    }

    /**
     * 向所有连接到本机的客户端广播消息
     */
    public void broadcast(String message) {
        if (localSockets.size() == 0 || Strings.isNullOrEmpty(message)) {
            return;
        }


        logger.info("准备广播：");
        for (WebSocket ws : localSockets) {
            ws.send(message);
        }
        logger.info("广播结束");
    }





}
