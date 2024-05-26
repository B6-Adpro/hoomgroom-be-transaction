package hoomgroom.transaction.Auth.dto;

import hoomgroom.transaction.Auth.model.Role;
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