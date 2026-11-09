package com.fluffyyarn.store.customer.infrastructure.lookup;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = AuthClient.class)
class LookupConfig {
}
