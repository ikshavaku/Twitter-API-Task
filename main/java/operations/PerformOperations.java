package operations;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utilities.ConfigReader;
import org.json.simple.parser.JSONParser;
import org.apache.commons.codec.binary.Base64;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static io.restassured.RestAssured.*;
import java.util.*;
import java.util.stream.*;

public class PerformOperations {
    static ConfigReader cr = new ConfigReader();
    static CommonMethods cm = new CommonMethods();
    static String accessToken=null;

    public String performAuth() throws Exception{
        baseURI = cr.getBaseURI();
        System.out.println(baseURI);
        Response response = given().header("Authorization","Basic "+"WXJmQmVUSHJNVUN1U0Q0a2RBTUJGNjlXWDoxMFhiZWxiNmZ0SXNucU5WdnVrUEkxQndiTDlubjJRbm9YMFFoZWV0U1lvMHQzMmhKOA==")
                .and().header("contentType", "application/x-www-form-urlencoded;charset=UTF-8")
                .and().param("grant_type","client_credentials")
                .when().post("/oauth2/token").then().extract().response();
        System.out.println(response.getBody().asString());
        JsonPath jp = response.jsonPath();
        accessToken = jp.get("access_token");
        return accessToken;
    }

    public JsonPath getTweets(int count, String username) throws Exception{
        String baseURI = cr.getBaseURI();
        Response response = given().header("Authorization", "Bearer "+accessToken)
                .queryParam("screen_name" , username).and().queryParam("count",count)
                .and().queryParam("include_rts",false)
                .when().get("/1.1/statuses/user_timeline.json")
                .then().extract().response();
        JsonPath jsonPath = response.jsonPath();
        JSONParser jsonParser = new JSONParser();
        List<String> tweetIDs = jsonPath.getList("id");
        List<String> date = jsonPath.getList("created_at");
        List<String> text = jsonPath.getList("text");
        List<String> favCount = jsonPath.getList("favourites_count");
        List<String> retweetCountInt = jsonPath.getList("retweet_count");
        //List<Integer> retweetCountInt = retweetCount.stream().map(Integer::parseInt).collect(Collectors.toList());
        //Collections.sort(retweetCountInt);
        Iterator itr = retweetCountInt.iterator();
        int index=0,i=0;
        while (i<10){
            index=retweetCountInt.indexOf(Collections.max(retweetCountInt));
            System.out.println("Tweet ID : "+String.valueOf(tweetIDs.get(index))+" Tweet Date : "+date.get(index)+
                    " Tweet Text : "+text.get(index)+" Retweet Count : +retweetCount.get(index)");
            retweetCountInt.remove(Collections.max(retweetCountInt));
            i++;
        }
        JSONArray tweets = new JSONArray();
        List<String> userList = new ArrayList<>();
        tweets.addAll((List)jsonPath);
        Iterator it = tweets.iterator();
        while (it.hasNext()){
            userList.add(((JSONObject)(((JSONObject)it.next()).get("user"))).get("screen_name").toString());
        }
        /*cm.printList(tweetIDs);
        cm.printList(date);
        cm.printList(text);
        cm.printList(favCount);
        cm.printList(retweetCount);
        cm.printList(userList);*/
        return jsonPath;
    }
    public void getTopTweets(JsonPath jsonPath) throws Exception{

        //String user = (String) ((JSONObject)jsonParser.parse(jsonPath.toString())).get("screen_name");

        return;
    }

    public void getTweets(List users) throws Exception{
        String base= cr.getBaseURI()+"/1.1/friends/list.json";
        Iterator itr= users.iterator();
        List<Response> friendList =  new ArrayList<>();
        while (itr.hasNext()){
            Response response = given().header("Authorization", "Bearer "+accessToken)
                    .queryParam("screen_name",itr.next())
                    .and().queryParam("include_user_entities", true)
                    .when().get(base)
                    .then().extract().response();
            friendList.add(response);
        }
    }

    public void getTopFriends(List response){
        Iterator itr= response.iterator();
        int i=0;
        while (itr.hasNext()){
            if(i<=100){
                continue;
            }
            else {
                continue;
            }
        }
    }
}
