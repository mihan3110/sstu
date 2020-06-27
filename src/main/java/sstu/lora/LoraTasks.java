package sstu.lora;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sstu.lora.dao.DbConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

@Component
public class LoraTasks {


    @Scheduled(fixedRate = 1000)
    public void Simple() throws IOException {
        ArrayList<String> list = new ArrayList<String>();
        DbConnection.prepareData();
       list =  DbConnection.sendDataToWS();
        for (int i=0; i<list.size(); i++) {
            // HttpURLConnection httpConnection = (HttpURLConnection) new URL("https://zinc-crystal-cafe.glitch.me/add?data=220,24,5").openConnection();
            URLConnection connection = new URL(list.get(i)).openConnection();
            System.out.println(list.get(i));
            InputStream is = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);
            char[] buffer = new char[256];
            int rc;

            StringBuilder sb = new StringBuilder();

            while ((rc = reader.read(buffer)) != -1)
                sb.append(buffer, 0, rc);

            reader.close();

            System.out.println(sb);
        }
    }
}
