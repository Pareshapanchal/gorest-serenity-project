package com.gorest.gorestinfo;

import com.gorest.constants.EndPoints;
import com.gorest.model.UserPojo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class UserSteps {
    @Step("Get all the user on the page 1")
    public ValidatableResponse getAllTheUserOnPage1(String token){
        return SerenityRest.given().log().all()
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("Authorization","Bearer "+token)
                .when()
                .get(EndPoints.GET_ALL_USER)
                .then();
    }
    @Step("Get single user infromation by userid : {0}")
    public ValidatableResponse getSingleUserById(int userid,String token){
        return SerenityRest.given().log().all()
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("Authorization","Bearer "+token)
                .when()
                .get(EndPoints.GET_USER_WITH_ID+"/"+userid)
                .then();
    }
    @Step("Add a new user name :{0}, email :{1}, status :{2}, gender :{3} ")
    public Response createANewUser(String token, String name, String email, String status, String gender){
        UserPojo userPojo = UserPojo.getUserPojo(name, email, status, gender);
        return SerenityRest.given().log().all()
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("Authorization","Bearer "+token)
                .contentType(ContentType.JSON)
                .when()
                .body(userPojo)
                .post(EndPoints.CREATE_USER);




    }
    @Step("Update a user by userid : {0}, email :{2}")
    public ValidatableResponse updateUser(int userid,String token,String name, String email,String status,String gender){
        UserPojo userPojo = UserPojo.getUserPojo(name,email,status,gender);

        return SerenityRest.given().log().all()
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("Authorization","Bearer "+token)
                .when()
                .body(userPojo)
                .put(EndPoints.UPDATE_USER_WITH_ID+"/"+userid)
                .then();
    }
    @Step("Delete a user by userid")
    public ValidatableResponse deleteUser(int userid,String token){
        return SerenityRest.given().log().all()
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("Authorization","Bearer "+token)
                .when()
                .delete(EndPoints.DELETE_USER_WITH_ID+"/"+userid)
                .then();
    }
}
