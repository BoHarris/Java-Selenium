package test;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Epic("Public APIs")
@Feature("JSONPlaceholder")
@Owner("Bokha")
public class ApiTest{

    @Story("GET /post/{id}")
    @Severity(SeverityLevel.MINOR)
    @Test(description = "GET /post/1 returns 200 and id=1")
    public void getPost_200_ok(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        given()
                .when()
                    .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }
}