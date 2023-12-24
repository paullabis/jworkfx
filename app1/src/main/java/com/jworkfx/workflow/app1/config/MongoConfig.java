package com.jworkfx.workflow.app1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.jworkfx.workflow.execution.dao")
public class MongoConfig {

}
