package com.reda.customer.testContConfig;

import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class TestContainerTest extends AbstractTestcontainers{

    @Test
    void canStartPostgresDB() {
        assertThat(postgresqlcontainer.isRunning()).isTrue();
        assertThat(postgresqlcontainer.isCreated()).isTrue();
    }

}
