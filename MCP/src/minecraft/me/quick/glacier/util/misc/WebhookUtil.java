package me.quick.glacier.util.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class WebhookUtil {

    public static String bugReport = "https://discord.com/api/webhooks/913124060928565249/1TjETX_Q7aMmDzFEIPbckzmR7hHP-bXjkKNJE6HvfD_k1w_0ai9TFljyAjGx11nSjJrP";
    public static String clientLaunch = "https://discord.com/api/webhooks/913124124971372635/LwtO9omDtB5ceY11PMylTzcXn_RgX2WaiKDNPwpK_K_grU0kikn5hQv_cs-GptDW6vC5";
    public static String anticheatLogs = "https://discord.com/api/webhooks/913124196266172436/bugMTBrkusW2GU78q2CzKzrM2px3c4W3fXwh8tGLmtePo7zBU5rrTuo7bDcCJ3KqgPuP";

    public static void sendMessage(String message, String webhook) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(webhook);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            String postData = URLEncoder.encode("content", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8");
            out.print(postData);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append("/n").append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println(result.toString());
    }

}
