package com.gorest.gorestinfo;

import com.gorest.testbase.TestBase;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

//@UseTestDataFrom("src/test/java/resources/testdata/userinfo.csv")
//@RunWith(SerenityParameterizedRunner.class)
public class CreateUserDataDrivenTest extends TestBase {
  private String name ;
    private String email ;
    private String gender;
    private String status;
    private int userid;
  String token ="31cfd320cc497555dcf88ee9cf1981adf94b200d3fadd4ccf32d595e3e844b34";

    @Steps
    UserSteps userSteps;
    @Title("This test will add new user in the application")
    @Test
    public void test001(){

     Response response= userSteps.createANewUser(token,name,email,status,gender);
        response.then().log().all().statusCode(201);
        response.then().body("name",equalTo(name));
        response.then().body("email",equalTo(email));
        response.then().statusLine("HTTP/1.1 201 Created");

        userid = response.then().extract().path("id");
        System.out.println(userid);
        String actualName = response.then().extract().path("name");
        String actualEmail = response.then().extract().path("email");
        Assert.assertEquals(name,actualName);
        Assert.assertEquals(email,actualEmail);
    }
  @Title("Get all the user list on the page 1 to per_page 20")
  @Test
  public void test002(){
    ValidatableResponse response = userSteps.getAllTheUserOnPage1(token);
    response.log().all().statusCode(200);
  }
  @Title("This test will delete user information from the application and verify user deleted successfully")
  @Test
  public void test003(){
    userSteps.deleteUser(userid,token).log().all().statusCode(204);
    userSteps.getSingleUserById(userid,token).log().all().statusCode(404);

  }
}
