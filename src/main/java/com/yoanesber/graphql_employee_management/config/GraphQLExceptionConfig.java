package com.yoanesber.graphql_employee_management.config;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;

/*
 * This class is responsible for handling exceptions that occur during GraphQL data fetching.
 * It customizes the error messages returned to the client based on the type of exception.
 * It also provides a way to handle validation errors using Jakarta Bean Validation (JSR 380).
 */

@Configuration
public class GraphQLExceptionConfig extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        // If it's a validation failure by bean validation (javax/jakarta.validation)
        if (ex instanceof ConstraintViolationException cve) {
            StringBuilder sb = new StringBuilder("Validation error(s): ");
            cve.getConstraintViolations().forEach(violation -> {
                sb.append("[")
                    .append(violation.getPropertyPath())
                    .append("] ")
                    .append(violation.getMessage())
                    .append("; ");
            });

            return GraphqlErrorBuilder.newError(env)
                    .message(sb.toString())
                    .errorType(ErrorType.ValidationError)
                    .path(env.getExecutionStepInfo().getPath())
                    .build();
        }

        // If it's an runtime exception (e.g., null pointer, etc.)
        if (ex instanceof RuntimeException runtimeEx) {
            return GraphqlErrorBuilder.newError(env)
                    .message(runtimeEx.getMessage())
                    .errorType(ErrorType.DataFetchingException)
                    .path(env.getExecutionStepInfo().getPath())
                    .build();
        }

        // If it's an illegal argument exception (e.g., invalid argument, etc.)
        if (ex instanceof IllegalArgumentException illegalArgEx) {
            return GraphqlErrorBuilder.newError(env)
                    .message(illegalArgEx.getMessage())
                    .errorType(ErrorType.InvalidSyntax)
                    .path(env.getExecutionStepInfo().getPath())
                    .build();
        }

        // If it's a GraphQL error (e.g., invalid query, etc.)
        if (ex instanceof graphql.GraphQLError) {
            return GraphqlErrorBuilder.newError(env)
                    .message(ex.getMessage())
                    .errorType(ErrorType.InvalidSyntax)
                    .path(env.getExecutionStepInfo().getPath())
                    .build();
        }

        // If it's a generic GraphQL validation error
        if (ex.getMessage() != null && ex.getMessage().contains("Validation error")) {
            return GraphqlErrorBuilder.newError(env)
                    .message("The input is invalid. Please check required fields and data types.")
                    .errorType(ErrorType.ExecutionAborted)
                    .path(env.getExecutionStepInfo().getPath())
                    .build();
        }

        return null; // fallback to default handling
    }

    @Override
    protected List<GraphQLError> resolveToMultipleErrors(Throwable ex, DataFetchingEnvironment env) {
        // If it's a validation failure by bean validation (javax/jakarta.validation)
        if (ex instanceof ConstraintViolationException validationEx) {
            return validationEx.getConstraintViolations()
                    .stream()
                    .map(violation -> GraphqlErrorBuilder.newError(env)
                        .message("[" + violation.getPropertyPath() + "] " + violation.getMessage())
                        .errorType(ErrorType.ValidationError)
                        .path(env.getExecutionStepInfo().getPath())
                        .build())
                    .collect(Collectors.toList());
        }

        // If it's an runtime exception (e.g., null pointer, etc.)
        if (ex instanceof RuntimeException runtimeEx) {
            return List.of(GraphqlErrorBuilder.newError(env)
                    .message(runtimeEx.getMessage())
                    .errorType(ErrorType.DataFetchingException)
                    .path(env.getExecutionStepInfo().getPath())
                    .build());
        }

        // If it's an illegal argument exception (e.g., invalid argument, etc.)
        if (ex instanceof IllegalArgumentException illegalArgEx) {
            return List.of(GraphqlErrorBuilder.newError(env)
                    .message(illegalArgEx.getMessage())
                    .errorType(ErrorType.InvalidSyntax)
                    .path(env.getExecutionStepInfo().getPath())
                    .build());
        }

        // If it's a GraphQL error (e.g., invalid query, etc.)
        if (ex instanceof graphql.GraphQLError) {
            return List.of(GraphqlErrorBuilder.newError(env)
                    .message(ex.getMessage())
                    .errorType(ErrorType.InvalidSyntax)
                    .path(env.getExecutionStepInfo().getPath())
                    .build());
        }

        // If it's a generic GraphQL validation error
        if (ex.getMessage() != null && ex.getMessage().contains("Validation error")) {
            return List.of(GraphqlErrorBuilder.newError(env)
                    .message("The input is invalid. Please check required fields and data types.")
                    .errorType(ErrorType.ExecutionAborted)
                    .path(env.getExecutionStepInfo().getPath())
                    .build());
        }
        
        return null;
    }
}
