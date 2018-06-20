package com.n26.n26.controller;

import com.n26.n26.model.Statistics;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.Assertions;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.response.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.restassured.RestAssured;
import com.n26.n26.model.Transaction;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.Instant;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class TransactionControllerTest {
    @Value("${local.server.port}")
    private int port;

    private String transactionOlderThan60Seconds;
    private String transactionWithin60Seconds;
    private String transactionWayInTheFuture;
    private String transaction2Within60Seconds;

    @Before
    public void setUp(){
        try {
            transactionWithin60Seconds = new ObjectMapper().writeValueAsString(new Transaction(22.3D, Instant.now().minusSeconds(10L).toEpochMilli()));
            transaction2Within60Seconds = new ObjectMapper().writeValueAsString(new Transaction(20.7D, Instant.now().minusSeconds(10L).toEpochMilli()));

            transactionOlderThan60Seconds = new ObjectMapper().writeValueAsString(new Transaction(24.3D, Instant.now().minusSeconds(70L).toEpochMilli()));
            transactionWayInTheFuture = new ObjectMapper().writeValueAsString(new Transaction(20.1D, Instant.now().plusSeconds(200L).toEpochMilli()));
            RestAssured.port = port;
        } catch (JsonProcessingException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void test201(){
        Response resp = given().contentType("application/json")
                .body(transactionWithin60Seconds).when().post("/transactions");

        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.SC_CREATED);
    }

    @Test
    public void test204(){
        Response resp = given().contentType("application/json")
                .body(transactionOlderThan60Seconds).when().post("/transactions");

        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void test400(){
        Response resp = given().contentType("application/json")
                .body(transactionWayInTheFuture).when().post("/transactions");

        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void testStats(){
        Response resp = given().contentType("application/json")
                .body(transactionWithin60Seconds).when().post("/transactions");
        resp = given().contentType("application/json")
                .body(transaction2Within60Seconds).when().post("/transactions");
        try {
            Statistics statistics = new ObjectMapper().readValue(given().contentType("application/json")
                    .when().get("/statistics").getBody().asString(), Statistics.class);
            Assertions.assertThat(statistics.getSum()).isEqualTo(43D);
            Assertions.assertThat(statistics.getAvg()).isEqualTo(21.5D);
            Assertions.assertThat(statistics.getMax()).isEqualTo(22.3D);
            Assertions.assertThat(statistics.getMin()).isEqualTo(20.7D);
            Assertions.assertThat(statistics.getCount()).isEqualTo(2);

        } catch (IOException e) {

        }

    }
    @Test
    public void testStatsOlderThan60Seconds(){
        for (int i = 0; i < 2; i++){
            given().contentType("application/json")
                    .body(transactionOlderThan60Seconds).when().post("/transactions");
        }
        try {
            Statistics statistics = new ObjectMapper().readValue(given().contentType("application/json")
                    .when().get("/statistics").getBody().asString(), Statistics.class);
            Assertions.assertThat(statistics.getSum()).isEqualTo(0D);
            Assertions.assertThat(statistics.getAvg()).isEqualTo(0D);
            Assertions.assertThat(statistics.getMax()).isEqualTo(0D);
            Assertions.assertThat(statistics.getMin()).isEqualTo(0D);
            Assertions.assertThat(statistics.getCount()).isEqualTo(0);

        } catch (IOException e) {

        }
   }
    @Test
    public void testStatsInTheFuture(){
        for (int i = 0; i < 2; i++){
            given().contentType("application/json")
                    .body(transactionWayInTheFuture).when().post("/transactions");
        }
        try {
            Statistics statistics = new ObjectMapper().readValue(given().contentType("application/json")
                    .when().get("/statistics").getBody().asString(), Statistics.class);
            Assertions.assertThat(statistics.getSum()).isEqualTo(0D);
            Assertions.assertThat(statistics.getAvg()).isEqualTo(0D);
            Assertions.assertThat(statistics.getMax()).isEqualTo(0D);
            Assertions.assertThat(statistics.getMin()).isEqualTo(0D);
            Assertions.assertThat(statistics.getCount()).isEqualTo(0);

        } catch (IOException e) {

        }
    }

    @Test
    public void testAllPossible(){
            given().contentType("application/json")
                    .body(transactionWayInTheFuture).when().post("/transactions");
        given().contentType("application/json")
                .body(transactionOlderThan60Seconds).when().post("/transactions");
        given().contentType("application/json")
                .body(transactionWithin60Seconds).when().post("/transactions");
        given().contentType("application/json")
                .body(transaction2Within60Seconds).when().post("/transactions");
        try {
            Statistics statistics = new ObjectMapper().readValue(given().contentType("application/json")
                    .when().get("/statistics").getBody().asString(), Statistics.class);
            Assertions.assertThat(statistics.getSum()).isEqualTo(43D);
            Assertions.assertThat(statistics.getAvg()).isEqualTo(21.5D);
            Assertions.assertThat(statistics.getMax()).isEqualTo(22.3D);
            Assertions.assertThat(statistics.getMin()).isEqualTo(20.7D);
            Assertions.assertThat(statistics.getCount()).isEqualTo(2);

        } catch (IOException e) {

        }
    }
}

