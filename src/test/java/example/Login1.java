package example;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.TestData;
import resources.Utils;
import org.testng.annotations.Test;


import java.io.IOException;

import static io.restassured.RestAssured.given;

public class Login1 extends Utils
{
    Response response;
    RequestSpecification req;
    ResponseSpecification res;

    TestData data=new TestData();

    JsonPath js;
    String accessToken;

    @Test(priority=0)
    public void login() throws IOException {
        res = new ResponseSpecBuilder().expectStatusCode(201).expectContentType(ContentType.JSON).build();

        req = given().log().all().spec(RequestSpecification())
                .body(data.LoginCredentials(getPropertiesValue("username"), getPropertiesValue("password")));
        response = req.when().log().all().post("/api/v2/auth/login")
                .then().spec(res).extract().response();
        //assertEquals(response.getStatusCode(), 201);

        js = new JsonPath(response.asString());
        accessToken=js.getString("data.token");
        System.out.println("Response of Login:" + response.asString());
        System.out.println("Token is:"+accessToken);
    }

    @Test(priority=1)
    public void getProfile() throws IOException {

          /*response=given().log().all().spec(RequestSpecification()).header("Authorization","Bearer "+accessToken)
                  .when().log().all().get("/api/v2/auth/profile")
                  .then().log().all().assertThat().statusCode(200).extract().response();
          System.out.println("User profile is:"+response.asString());*/


          response=given().log().all().spec(RequestSpecification()).header("Authorization","Bearer "+ accessToken)
                  .when().log().all().get("/api/v2/auth/profile")
                  .then().log().all().assertThat().statusCode(200).extract().response();
          System.out.println("Profile details are: "+response.asString());
      }

}
