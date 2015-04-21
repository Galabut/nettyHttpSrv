package http.utils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Galabut on 20.04.2015.
 */
public class ConnectionUtils {
    private String url;

    private String ipAddress;
    private int bytesReceived;
    private String date1;

    public String ipAddressDefine(ChannelHandlerContext context) {
        InetSocketAddress socketAddress = (InetSocketAddress) context.channel().remoteAddress();
        InetAddress inetaddress = socketAddress.getAddress();
        return ipAddress = inetaddress.getHostAddress();
    }

    public int countBytesReceived(HttpRequest msg) {
        String receivedMsg = msg.toString();
        String noClassMsg = receivedMsg.substring(receivedMsg.indexOf("\n") + 1);
        return bytesReceived = noClassMsg.length();
    }

    public String defineDate() {
        DateFormat date = new SimpleDateFormat("MM-dd HH:mm:ss");
        return date1 = date.format(new Date());
    }

    public  void countUnique(HashMap<String, Integer> countUniqueMap,String s) {
        if (!countUniqueMap.containsKey(s)) {
            countUniqueMap.put(s, 1);
        } else {
            countUniqueMap.put(s, countUniqueMap.get(s) + 1);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getBytesReceived() {
        return bytesReceived;
    }

    public void setBytesReceived(int bytesReceived) {
        this.bytesReceived = bytesReceived;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

}
