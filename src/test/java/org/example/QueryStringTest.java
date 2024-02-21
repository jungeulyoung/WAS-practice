package org.example;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class QueryStringTest {

    // List<QueryString>
    @DisplayName("")
    @Test
    void createTest() {
        QueryString  queryString =  new QueryString("operand1", "11");

        assertThat(queryString).isNotNull();
    }
}
