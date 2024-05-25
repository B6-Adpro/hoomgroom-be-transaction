package hoomgroom.product.Auth.dto;

import hoomgroom.product.Auth.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String firstname;
    private String lastname;
    private Role role;
}