package enriquevb.libraryauthserver.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.Set;

public class PasswordGrantAuthenticationToken extends AbstractAuthenticationToken {

    private final Authentication clientPrincipal;
    private final String username;
    private final String password;
    private final Set<String> scopes;

    public PasswordGrantAuthenticationToken(Authentication clientPrincipal,
                                            String username,
                                            String password,
                                            Set<String> scopes) {
        super(Collections.emptyList());
        this.clientPrincipal = clientPrincipal;
        this.username = username;
        this.password = password;
        this.scopes = scopes;
    }

    @Override
    public Object getPrincipal() {
        return clientPrincipal;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getScopes() {
        return scopes;
    }
}
