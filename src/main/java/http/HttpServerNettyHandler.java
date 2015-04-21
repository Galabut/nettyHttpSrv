package http;


import http.utils.ConnectionUtils;
import http.utils.HttpUtils;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;

import java.util.HashMap;
import java.util.Iterator;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by Galabut on 18.04.2015.
 */
public class HttpServerNettyHandler extends ChannelInboundHandlerAdapter {

    private static final String HELLO = "Hello World!";
    private static final String redirection = "/redirect?url=";

    private static HashMap<String, Integer> ips = new HashMap<>();
    private static HashMap<String, Integer> redirects = new HashMap<>();
    private static ConnectionEntry[] connections = new ConnectionEntry[16];

    private static int requestCounter = 0;
    private static int openConnections;

    private static ConnectionUtils utils = new ConnectionUtils();

    private static String urlRedirect;
    private static int sentBytes;
    private static long speed;
    static Object sync = new Object();

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        long startTime = System.currentTimeMillis();
        try {
            if (msg instanceof HttpRequest) {
                synchronized (sync) {
                    openConnections++;
                }
                ConnectionEntry conn = new ConnectionEntry();
                requestCounter++;
                conn.setRequestNumber(requestCounter);

                connections[requestCounter % 16] = conn;

                HttpRequest req = (HttpRequest) msg;

                conn.setUrl(req.getUri());
                conn.setIp(utils.ipAddressDefine(ctx));
                conn.setReceivedBytes(utils.countBytesReceived((HttpRequest) msg));
                conn.setDate(utils.defineDate());
                utils.countUnique(ips, conn.getIp());

                if (HttpHeaders.is100ContinueExpected(req)) {
                    ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
                }
                boolean keepAlive = HttpHeaders.isKeepAlive(req);
                FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK);
//-----------------hello---------------
                if (conn.getUrl().equals("/hello")) {
                    response.content().writeBytes(HELLO.getBytes());
                    response.headers().set(CONTENT_TYPE, "text/html;charset=UTF-8");
                    response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//-----------------redirection---------------

                } else if (conn.getUrl().startsWith(redirection)) {
                    urlRedirect = conn.getUrl().substring(redirection.length());
                    if (!urlRedirect.startsWith("http://")) {
                        urlRedirect = "http://" + urlRedirect;
                    }
                    if (urlRedirect != null) {
                        utils.countUnique(redirects, urlRedirect);
                    }

                    response.setStatus(MOVED_PERMANENTLY);
                    response.headers().set(LOCATION, urlRedirect);
                    response.headers().set(CONTENT_LENGTH, response.content().readableBytes());

//-----------------status-----------------
                } else if (conn.getUrl().equals("/status")) {
                    String firstTable = String.format(HttpUtils.firstTable, requestCounter, ips.size(), openConnections);
                    String secondTable = HttpUtils.secondTableHeader;

                    Iterator<String> itr = ips.keySet().iterator();
                    while (itr.hasNext()) {
                        String ip = itr.next();
                        secondTable += String.format(HttpUtils.secondTableContent, ip, ips.get(ip), conn.getDate());
                    }
                    String thirdTable = HttpUtils.thirdTableHeader;
                    int connRequestNumber = (int) conn.getRequestNumber();
                    for (int j = 0; j < connections.length; j++) {
                        int i = (connRequestNumber + 16) % 16;
                        if (connections[i] != null) {
                            thirdTable += String.format(HttpUtils.thirdTableContent, connections[i].getIp(),
                                    connections[i].getUrl(), connections[i].getDate(),
                                    connections[i].getSentBytes(), connections[i].getReceivedBytes(),
                                    connections[i].getSpeed());
                            connRequestNumber--;
                        }
                    }
                    String fourthTable = HttpUtils.fourthTableHeader;
                    Iterator<String> itrR = redirects.keySet().iterator();

                    while (itrR.hasNext()) {
                        String url = itrR.next();
                        fourthTable += String.format(HttpUtils.forthTableContent, url, redirects.get(url));
                    }
                    String integratedWithAllTables =
                            String.format(HttpUtils.baseHTML, firstTable, secondTable, thirdTable, fourthTable);

                    response.content().writeBytes(integratedWithAllTables.getBytes());
                    response.headers().set(CONTENT_TYPE, "text/html;charset=UTF-8");
                    response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
                } else {
                    response.setStatus(NOT_FOUND);
                    response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
                }
                long endTime = System.currentTimeMillis();
                //-----speedBytes----
                if ((endTime - startTime != 0)) {
                    speed = sentBytes * 1000 / ((endTime - startTime));
                } else {
                    speed = 0;
                }
                //-----bytes sent--
                sentBytes = response.content().readableBytes();
                conn.setSentBytes(sentBytes);
                conn.setSpeed(speed);

                synchronized (sync) {
                    openConnections--;
                }
                if (!keepAlive) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}