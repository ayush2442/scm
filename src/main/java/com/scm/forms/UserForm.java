package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Minimum 3 char is required")
    private String name;

    @Email(message = "Invalid Email Address")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password cannot be Empty")
    @Size(min = 6, message = "Minimum 6 char is required")
    private String password;

    @NotBlank(message = "About is required")    
    private String about;

    @NotBlank(message = "Phone Number cannot be blank")
    @Size(min = 10, max = 13, message = "Invalid Phone Number")
    private String phoneNumber;
    
    
}
