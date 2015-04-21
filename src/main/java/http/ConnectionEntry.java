package http;

/**
 * Created by Galabut on 19.04.2015.
 */
public class ConnectionEntry {
    private long requestNumber;
    private String ip;
    private String url;
    private String date;
    private int sentBytes;
    private int receivedBytes;
    private long speed;

    public ConnectionEntry() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSentBytes() {
        return sentBytes;
    }

    public void setSentBytes(int sentBytes) {
        this.sentBytes = sentBytes;
    }

    public int getReceivedBytes() {
        return receivedBytes;
    }

    public void setReceivedBytes(int receivedBytes) {
        this.receivedBytes = receivedBytes;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public long getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(long requestNumber) {
        this.requestNumber = requestNumber;
    }
}
