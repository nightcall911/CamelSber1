package org.example.camelsber1.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.example.camelsber1.Enum.RoleEnum;


@JsonIgnoreProperties(ignoreUnknown = true)

public record UserDto (String name, int age, RoleEnum role) {
}
