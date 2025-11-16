package com.hms.user.dto;

import com.hms.user.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;
    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&^#_\\-])[A-Za-z\\d@$!%*?&^#_\\-]{8,}$",message = "Password should contain atleast 1 uppercase, 1 lowercase, 1 digit and 1 special character")
    private String password;
    private Roles role;
    private Long profileId;

    public User toEntity(){
        return new User(this.id,this.name,this.email,this.password,this.role,this.profileId);
    }
    
}
