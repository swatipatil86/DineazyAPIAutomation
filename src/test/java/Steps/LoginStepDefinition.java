package Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.TestData;
import resources.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginStepDefinition extends Utils
{
    Response response;
    RequestSpecification req;
    ResponseSpecification res;
    TestData data=new TestData();
    JsonPath js;
    String accessToken;
    String otp;
    String uname;
    String phone;

                            /*Login to Dineazy*/

    @Given("user login with credentials {string} and {string}")
    public void user_login_with_credentials_and(String username, String password) throws IOException {
        res = new ResponseSpecBuilder().expectStatusCode(201).expectContentType(ContentType.JSON).build();
        req = given().log().all().spec(RequestSpecification()).body(data.LoginCredentials(username,password));
        response = req.when().log().all().post("/api/v2/auth/login")
                .then().spec(res).extract().response();
        js = new JsonPath(response.asString());
        accessToken = js.getString("data.token");
        System.out.println("The token is: "+accessToken);
    }

    @Given("user login to dineazy")
    public void user_login_to_dineazy() throws IOException {
        res = new ResponseSpecBuilder().expectStatusCode(201).expectContentType(ContentType.JSON).build();
        req = given().log().all().spec(RequestSpecification()).body(data.LoginCredentials(getPropertiesValue("username"),getPropertiesValue("password")));
        response = req.when().log().all().post("/api/v2/auth/login")
                .then().spec(res).extract().response();
        js = new JsonPath(response.asString());
        accessToken = js.getString("data.token");
        System.out.println("The token is: "+accessToken);
    }

                        /*Get Profile */

    @When("user gets the profile")
    public void user_gets_the_profile() throws IOException {
        response=given().log().all().spec(RequestSpecification()).header("Authorization","Bearer "+accessToken)
                .when().log().all().get("/api/v2/auth/profile")
                .then().log().all().assertThat().statusCode(200).extract().response();

    }
    @Then("user should verify that {string} is found")
    public void user_should_verify_that_is_found(String expectedFirstName)
    {
        js = new JsonPath(response.asString());
        String actualFirstName = js.getString("data.firstName");
        //System.out.println("Found the profile "+actualFirstName);
        assertEquals(expectedFirstName,actualFirstName);
    }
                        /* Send OTP*/
    @When("user send OTP")
    public void user_send_otp() throws IOException {
        res = new ResponseSpecBuilder().expectStatusCode(201).expectContentType(ContentType.JSON).build();
        req = given().log().all().spec(RequestSpecification()).body(data.OTPDetails(getPropertiesValue("firstName"),getPropertiesValue("lastName"),getPropertiesValue("username"),getPropertiesValue("password"),getPropertiesValue("phone")));
        response = req.when().log().all().post("/api/v2/auth/otp/send")
                .then().log().all().spec(res).extract().response();
        js=new JsonPath(response.asString());
        uname=js.getString("data.email");
        phone=js.getString("data.phone");

        otp=js.getString("data.otp");
        assertEquals(response.getStatusCode(),201);
    }

                            /*Verify OTP*/
    @Then("user should verify OTP sent is correct")
    public void user_should_verify_otp_sent_is_correct() throws IOException {
        res = new ResponseSpecBuilder().expectStatusCode(201).expectContentType(ContentType.JSON).build();
        req = given().log().all().spec(RequestSpecification()).body(data.VerifyOtpPayload(uname,phone,otp));
        response=req.when().log().all().post("/api/v2/auth/otp/verify")
                .then().log().all().spec(res).extract().response();
        js=new JsonPath(response.asString());
        assertEquals(response.getStatusCode(),201);
    }

                    /*Create Profile*/

    @Given("user enters {string},{string},{string},{string} and {string} details")
    public void user_enters_and_details(String firstName,String lastName,String email,String password,String phone) throws IOException
    {
        req=given().log().all().spec(RequestSpecification()).header("Authorization","Bearer "+accessToken)
                .body(data.CreateProfileData(firstName,lastName,email,password,phone));
        res = new ResponseSpecBuilder().expectStatusCode(201).expectContentType(ContentType.JSON).build();
        response=req.when().log().all().post("/api/v2/auth/profile")
                .then().log().all().spec(res).extract().response();
    }

    @Then("user should verify that profile created successfully")
    public void user_should_verify_that_profile_created_successfully()
    {
        js = new JsonPath(response.asString());
        String msg=js.getString("message");
        System.out.println(msg);
    }

                                /*Logout */

    @When("user logout from application")
    public void user_logout_from_application() throws IOException
    {
        res = new ResponseSpecBuilder().expectStatusCode(201).expectContentType(ContentType.JSON).build();

        req=given().log().all().spec(RequestSpecification()).header("Authorization","Bearer "+accessToken);
             response=req.when().log().all().post("/api/v2/auth/logout")
                .then().log().all().spec(res).extract().response();
    }
    @Then("user should verify that user is logged out successfully")
    public void user_should_verify_that_user_is_logged_out_successfully()
    {
        js=new JsonPath(response.asString());
        String logoutMsg=js.getString("message");
        System.out.println(logoutMsg);
    }

                                    /* Register*/

    @Given("user enters the {string},{string},{string},{string} and {string} details")
    public void user_enters_the_and_details(String firstName, String lastName, String email, String password, String phone) throws IOException {
        res=new ResponseSpecBuilder().expectStatusCode(300).expectContentType(ContentType.JSON).build();
        req=given().log().all().spec(RequestSpecification())
                .body(data.RegisterPayload(firstName,lastName,email,password,phone));
        response=req.when().log().all().post("/api/v2/auth/register")
                .then().log().all().spec(res).extract().response();


    }

    @Then("user should verify that User Resister successfully")
    public void user_should_verify_that_user_resister_successfully()
    {
        js=new JsonPath(response.asString());
        String msg=js.getString("message");
        System.out.println(msg);

    }


 /*  @Given("^user login with Credentials \"([^\"]*)\" and \"([^\"]*)\"$")
    public void user_login_with_credentials_something_and_something(String username, String pwd) throws Throwable {

       RestAssured.baseURI = "https://dineazy-api-dev.elaachi.com";

          String res = given().log().all().header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"email\": \"swati.p@itprofound.com\",\n" +
                        "  \"password\": \"Anish##0104\"\n" +
                        "}")
                .when().post("/api/v2/auth/login")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();
        System.out.println("Response of Login:" + res);
        js = new JsonPath(res);
        accessToken = js.getString("data.token");
        System.out.println("The token is:"+accessToken);

        System.out.println("Login with credentials "+username +" and "+pwd );}*/

   /* @When("^user gets the profile$")
    public void user_gets_the_profile() throws Throwable {
        RestAssured.baseURI = "https://dineazy-api-dev.elaachi.com";

        String profile=given().log().all().header("Content-Type","application/json").header("Authorization","Bearer "+token)
                .when().get("/api/v2/auth/profile")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        System.out.println("Your Profile Details:" + profile);


        System.out.println("GetProfile");
    }

    @Then("^user should verify that \"([^\"]*)\" is found$")
    public void user_should_verify_that_something_is_found(String profile) throws Throwable {
        System.out.println("Found the profile" +profile);
    }*/

}
