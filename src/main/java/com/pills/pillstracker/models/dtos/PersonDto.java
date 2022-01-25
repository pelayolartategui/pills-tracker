package com.pills.pillstracker.models.dtos;

import com.pills.pillstracker.validators.tags.ContactNumberConstraint;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
public class PersonDto {

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotBlank
    @ContactNumberConstraint
    private String contactNumber;

}
