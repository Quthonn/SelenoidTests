import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class SelenoidTests {
    private static String Login;
    private static String Password;
    @Test
    void checkTotal() {
        given()
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(5))
                .body("browsers.chrome", hasKey("127.0"));
    }

    @Test
    void checkWdHubStatus() {
        Dotenv dotenv = Dotenv.load();
        Login = dotenv.get("login");
        Password = dotenv.get("password");
        given()
                .log().all()
                .auth().basic(Login, Password)
                .when()
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("value.ready", is(true));
    }
}
