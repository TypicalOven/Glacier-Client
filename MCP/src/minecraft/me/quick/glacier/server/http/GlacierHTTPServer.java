package me.quick.glacier.server.http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import me.quick.glacier.util.misc.Identification;
import org.apache.commons.io.Charsets;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
public class GlacierHTTPServer {

    public void login() throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("http://localhost:41968/login");

        post.setHeader("Content-type", "application/json");

        JSONObject sendObj = new JSONObject();
        //sendObj.append("uuid", Minecraft.getMinecraft().getNetHandler().getGameProfile().getId());
        sendObj.append("uuid", "testuuid");
        sendObj.append("hwid", Identification.getHWID());
        sendObj.append("ip", Identification.getIP());

        StringEntity jsonEntity = new StringEntity(sendObj.toString());
        post.setEntity(jsonEntity);
        HttpResponse response = client.execute(post);

        HttpEntity entity = response.getEntity();
        Header encodingHeader = entity.getContentEncoding();

        // you need to know the encoding to parse correctly
        Charset encoding = encodingHeader == null ? StandardCharsets.UTF_8 :
                Charsets.toCharset(encodingHeader.getValue());

        // use org.apache.http.util.EntityUtils to read json as string
        String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);

        System.out.println(json);

    }

}
