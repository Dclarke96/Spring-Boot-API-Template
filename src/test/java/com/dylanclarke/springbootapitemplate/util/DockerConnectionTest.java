package com.dylanclarke.springbootapitemplate.util;

import org.junit.jupiter.api.Test;
import org.testcontainers.DockerClientFactory;

class DockerConnectionTest {

    @Test
    void dockerConnection() {
        System.out.println(
            DockerClientFactory.instance()
                .client()
                .infoCmd()
                .exec()
                .getServerVersion()
        );
    }
}
