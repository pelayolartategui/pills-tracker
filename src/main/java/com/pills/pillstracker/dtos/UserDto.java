package com.pills.pillstracker.dtos;

import com.pills.pillstracker.validators.tags.ContactNumberConstraint;
import com.pills.pillstracker.validators.tags.PasswordMatches;
import lombok.Data;
import lombok.ToString.Exclude;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@PasswordMatches
public class UserDto {

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotBlank
    @ContactNumberConstraint
    private String contactNumber;

    @Exclude
    @NotBlank
    @Size(max = 120)
    private String password;

    @Exclude
    @NotBlank
    @Size(max = 120)
    private String matchingPassword;

}
