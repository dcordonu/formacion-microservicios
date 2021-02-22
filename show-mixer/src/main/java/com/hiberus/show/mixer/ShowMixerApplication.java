package com.hiberus.show.mixer;

import com.hiberus.show.mixer.binding.BinderProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(BinderProcessor.class)
public class ShowMixerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShowMixerApplication.class, args);
    }
}
