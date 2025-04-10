package com.yoanesber.graphql_employee_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import graphql.scalars.ExtendedScalars;

/**
 * This class is responsible for configuring custom GraphQL scalars.
 * Note: You can also register other scalar types like GraphQLBigDecimal, GraphQLBigInteger, etc.
 */

@Configuration
public class GraphQLScalarConfig {
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
            .scalar(ExtendedScalars.Date)         // Register scalar Date
            .scalar(ExtendedScalars.DateTime)    // Optional: Register DateTime too
            .scalar(ExtendedScalars.GraphQLLong); // Optional: Register GraphQLLong too
    }
}
