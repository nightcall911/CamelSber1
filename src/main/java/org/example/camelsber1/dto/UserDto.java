package org.example.camelsber1.dto;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.camelsber1.Enum.RoleEnum;


@JsonIgnoreProperties(ignoreUnknown = true)

public record UserDto (String name, int age, RoleEnum role) {
}
