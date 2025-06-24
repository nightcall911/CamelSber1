package org.example.camelsber1.routes;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.example.camelsber1.dto.UserDto;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.camelsber1.Enum.RoleEnum.USER;

@Component
public class RestCamelRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        JacksonDataFormat userListFormat = new JacksonDataFormat(UserDto.class);
        userListFormat.useList();

        restConfiguration()
                .component("servlet")
                .contextPath("/api")
                .bindingMode(org.apache.camel.model.rest.RestBindingMode.off);

        rest("/users")
                .post()
                .consumes("application/json")
                .produces("application/json")
                .to("direct:filter-users");

        from("direct:filter-users")
                .unmarshal(userListFormat)
                .process(exchange -> {
                    List<UserDto> users = exchange.getIn().getBody(List.class);

                    List<UserDto> filtered = users.stream()
                            .filter(u -> USER.equals(u.getRole()))
                            .collect(Collectors.toList());

                    exchange.getMessage().setBody(filtered);
                })
                .marshal(userListFormat)
                .to("mock:kafka")
                .log("Отфильтрованные пользователи отправлены в mock-kafka: ${body}");
    }
}
