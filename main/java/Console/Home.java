package Console;

import io.restassured.path.json.JsonPath;
import operations.*;

import java.util.List;

public class Home {

    public static void main(String[] args) {
        PerformOperations po = new PerformOperations();
        try {
            String at = po.performAuth();
            JsonPath jp = po.getTweets(100,"BarackObama");
            //List users = po.getTopTweets(jp);
        }
        catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
