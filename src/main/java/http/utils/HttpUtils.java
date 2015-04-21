package http.utils;

public class HttpUtils {
    public static String baseHTML=
            "<html>\n" +
            "<head>\n" +
            "    <title></title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<table>%s</table>\n" +
            "<p></p>\n" +
            "<table border=\"1\">%s</table>\n" +
            "<p></p>\n" +
            "<table border=\"1\">%s</table>\n" +
            "<p></p>\n" +
            "<table border=\"1\">%s</table>\n" +
            "</body>\n" +
            "</html>";

    public static String firstTable =
                    "    <tr>\n" +
                    "        <th>Total Requests</th>\n" +
                    "        <td>%d</td>\n" + //add int
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <th>Unique IP Address Requests</th>\n" +
                    "        <td>%d</td>\n" +//add int
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <th>Open Connections</th>\n" +
                    "        <td>%d</td>\n" +//add int
                    "    </tr>\n" ;

    public static String secondTableHeader =
            "        <th>IP Address</th>\n" +
            "        <th> Amount of Requests</th>\n" +
            "        <th>Last Request</th>\n";
    public static String secondTableContent =
            "    <tr>\n" +
            "        <td>%s</td>\n" +
            "        <td>%d</td>\n" +
            "        <td>%s</td>\n" +
            "    </tr>\n";

    public static String thirdTableHeader =
            "    <tr>\n" +
   //         "        <th>Req№</th>\n" +
            "        <th>IP Address</th>\n" +
            "        <th> URL</th>\n" +
            "        <th>TimeStamp</th>\n" +
            "        <th>Sent Bytes</th>\n" +
            "        <th>Received Bytes</th>\n" +
            "        <th>Speed(bytes/sec)</th>\n" +
            "    </tr>\n";
    public static String thirdTableContent = "<tr>\n" +
    //        "        <td>%s</td>\n" +
            "        <td>%s</td>\n" +
            "        <td>%s</td>\n" +
            "        <td>%s</td>\n" +
            "        <td>%d</td>\n" +
            "        <td>%d</td>\n" +
            "        <td>%d</td>\n" +
            "    </tr>\n";

    public static String fourthTableHeader =
            "  <tr>\n" +
            "  <th>url</th>\n" +
            "  <th>Amount of Redirects</th>\n" +
            "  </tr>\n";
    public static String forthTableContent =
            "    <tr>\n" +
            "        <td>%s</td>\n" +
            "        <td>%d</td>\n" +
            "    </tr>\n";

}





