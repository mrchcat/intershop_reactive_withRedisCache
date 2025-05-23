package com.github.mrchcat.client;

import com.github.mrchcat.dto.Payment;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentClientImplTest {

    final static MockWebServer server = new MockWebServer();
    PaymentClient paymentClient;

    @BeforeAll
    static void setUp() throws IOException {
        server.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        server.shutdown();
    }

    @BeforeEach
    void initialize() {
        paymentClient = new PaymentClientImpl(server.url("/").toString());
    }


    @Test
    void getBalanceTest() throws InterruptedException {
        UUID clientId = UUID.randomUUID();
        server.enqueue(new MockResponse().setResponseCode(404));
        paymentClient.getBalance(clientId).block();
        RecordedRequest recordedRequest = server.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/api/v1/balance/" + clientId, recordedRequest.getPath());
    }

    @Test
    void getPaymentTest() throws InterruptedException {
        UUID payerId = UUID.fromString("db1673e9-bf97-4dfa-acdb-88a280d4d144");
        UUID recipientId = UUID.fromString("623ff0e5-2069-4051-88dc-fea52a85ffab");
        BigDecimal amount = BigDecimal.valueOf(100, 2);
        Payment payment = Payment.builder()
                .paymentId(UUID.randomUUID())
                .payer(payerId)
                .recipient(recipientId)
                .amount(amount)
                .build();
        server.enqueue(new MockResponse().setResponseCode(404));
        paymentClient.createPayment(payment).block();
        RecordedRequest recordedRequest = server.takeRequest();
        assertEquals("POST", recordedRequest.getMethod());
    }

}