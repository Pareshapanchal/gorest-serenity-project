package com.gorest.gorestinfo;

import com.gorest.testbase.TestBase;
import com.gorest.utils.TestUtils;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import net.thucydides.core.annotations.WithTag;
import net.thucydides.core.annotations.WithTags;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SerenityRunner.class)
public class UserCRUDTest extends TestBase {

    static String name ="Sheela "+ TestUtils.randomStr(4);
    static String email = "Ram"+TestUtils.getRandomValue()+"@gmail.com";
    static String gender="female";
    static String status="active";
    static int userid ;
    String token ="31cfd320cc497555dcf88ee9cf1981adf94b200d3fadd4ccf32d595e3e844b34";
    @Steps
    UserSteps userSteps;
    @WithTags({

            @WithTag("REGRESSION"),
            @WithTag("SANITY"),
    })
    @Title("Get all the user list on the page 1 to per_page 20")
    @Test
    public void test001(){
        ValidatableResponse response = userSteps.getAllTheUserOnPage1(token);
        response.log().all().statusCode(200);

    }
    @WithTags({

            @WithTag("REGRESSION"),
            @WithTag("SMOKE"),
    })
    @Title("This test will add new user in the application")
    @Test
    public void test002(){
      Response response = userSteps.createANewUser(token,name,email,status,gender);
       response.then().log().all().statusCode(201);
       response.then().body("name",equalTo(name));
       response.then().body("email",equalTo(email));
       response.then().statusLine("HTTP/1.1 201 Created");

        userid = response.then().extract().path("id");
        String actualName = response.then().extract().path("name");
        String actualEmail = response.then().extract().path("email");
        Assert.assertEquals(name,actualName);
        Assert.assertEquals(email,actualEmail);
    }
    @WithTags({

            @WithTag("REGRESSION"),
            @WithTag("SMOKE"),
    })
    @Title("This test will verify the new user added in the application by user id from application")
    @Test
    public void test003(){
         userSteps.getSingleUserById(userid,token).log().all().statusCode(200);

    }
    @WithTags({

            @WithTag("REGRESSION"),
            @WithTag("SMOKE"),
    })
    @Title("This method will update the user information and verify the user by id in the application")
   @Test
    public void test004(){

        email = "sita"+TestUtils.getRandomValue()+"@gmail.com";
        gender="male";

        ValidatableResponse response=  userSteps.updateUser(userid,token,name,email,status,gender).log().all().statusCode(200);
          response.body("email",equalTo(email));
          response.body("gender",equalTo(gender));

      userSteps.getSingleUserById(userid,token).log().all().statusCode(200);


    }
    @WithTags({

            @WithTag("REGRESSION"),
            @WithTag("SMOKE"),
    })
    @Title("This test will delete user information from the application and verify user deleted successfully")
   @Test
    public void test005(){
        userSteps.deleteUser(userid,token).log().all().statusCode(204);
        userSteps.getSingleUserById(userid,token).log().all().statusCode(404);

    }
}
