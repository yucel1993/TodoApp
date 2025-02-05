package com.yucel.todo.appConfig;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class TodoConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
