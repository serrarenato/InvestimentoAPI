package org.example.investimentoapi.dto;

import lombok.Data;
import java.util.Set;

@Data
public class AuthRequest {
    private String username;
    private String password;
    private Set<String> roles;
}
