package com.example.myStore.Controller.Utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {

    public static Map<String, String> getErrors(BindingResult bindingResult) {

        /*Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        return bindingResult.getFieldErrors().stream().collect(collector);*/

        Map<String, String> result = new TreeMap<>();

        for (FieldError f : bindingResult.getFieldErrors()) {

            if (!result.containsKey(f.getField() + "Error")) {
                result.put(f.getField() + "Error", f.getDefaultMessage());
            }
        }
        return result;
    }
}
