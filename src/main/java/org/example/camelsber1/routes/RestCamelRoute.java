package org.example.camelsber1.routes;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.example.camelsber1.Enum.RoleEnum;
import org.example.camelsber1.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class RestCamelRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        JacksonDataFormat userArrayFormat = new JacksonDataFormat(UserDto[].class);
        userArrayFormat.disableFeature(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        restConfiguration().component("servlet")
                .bindingMode(RestBindingMode.off);

        rest("/users")
                .post()
                .consumes("application/json")
                .to("direct:processUsers");

        from("direct:processUsers")
                .unmarshal(userArrayFormat)
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        UserDto[] users = exchange.getIn().getBody(UserDto[].class);
                        List<UserDto> filtered = Arrays.stream(users)
                                .filter(u -> u.role() == RoleEnum.USER)
                                .collect(Collectors.toList());
                        ObjectMapper objectMapper = new ObjectMapper();
                        String json = objectMapper.writeValueAsString(filtered);

                        exchange.getIn().setBody(json);
                    }
                })

                .to("kafka:users?brokers=localhost:9092")
                .log("Отправлено отфильтрованное сообщение в Kafka: ${body}");
    }
}
