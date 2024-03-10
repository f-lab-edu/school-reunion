package flab.schoolreunion.board.config;

import flab.schoolreunion.board.dto.board.Target;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new TargetConverter());
    }
}

class TargetConverter implements Converter<String, Target> {
    @Override
    public Target convert(String target) {
        return Target.valueOf(target.toUpperCase());
    }
}