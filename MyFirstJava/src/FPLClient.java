import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FPLClient {
    public FPLStatic GetStatic() {
        try {
            URL url = new URL("https://fantasy.premierleague.com/api/bootstrap-static/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                var gson = new Gson();
                FPLStatic fplStatic = gson.fromJson(content.toString(), FPLStatic.class);
                return fplStatic;
            } else {
                System.out.println("Request failed with status: " + status);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
