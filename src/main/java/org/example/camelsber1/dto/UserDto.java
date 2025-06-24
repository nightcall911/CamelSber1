package org.example.camelsber1.dto;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.camelsber1.Enum.RoleEnum;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String name;

    private int age;

    private RoleEnum role;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public RoleEnum getRole() {
        return role;
    }
}
