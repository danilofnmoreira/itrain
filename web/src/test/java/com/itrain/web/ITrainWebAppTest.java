package com.itrain.web;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ITrainWebAppTest {

    @Test
    @DisplayName(value = "context load text")
    void context_load_test() {
        assertTrue(true);
    }

}
