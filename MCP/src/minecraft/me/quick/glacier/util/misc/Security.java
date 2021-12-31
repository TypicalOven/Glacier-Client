package me.quick.glacier.util.misc;

import org.apache.commons.io.Charsets;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Security {

    public static boolean isVPN() {
       try {
           HttpClient client = HttpClientBuilder.create().build();
           HttpGet post = new HttpGet("https://ipqualityscore.com/api/json/ip/oOswzMILsf8QA7JGtaQDdXARfDtbKW1K/" + Identification.getIP());

           post.setHeader("Content-type", "application/json");

           HttpResponse response = client.execute(post);

           HttpEntity entity = response.getEntity();
           Header encodingHeader = entity.getContentEncoding();

           // you need to know the encoding to parse correctly
           Charset encoding = encodingHeader == null ? StandardCharsets.UTF_8 :
                   Charsets.toCharset(encodingHeader.getValue());

           // use org.apache.http.util.EntityUtils to read json as string
           String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);

           JSONObject o = new JSONObject(json);

           if(o.getBoolean("proxy") || o.getBoolean("vpn") || o.getBoolean("active_vpn")) {
               System.out.println("epic vpn $$");
               return true;
           } else return false;
       } catch (Exception e) {
           return false;
       }
    }

}
