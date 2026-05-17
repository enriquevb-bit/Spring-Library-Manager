package enriquevb.libraryauthserver.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PasswordGrantAuthenticationConverter implements AuthenticationConverter {

    private static final String PASSWORD_GRANT_TYPE = "password";

    @Override
    public Authentication convert(HttpServletRequest request) {
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!PASSWORD_GRANT_TYPE.equals(grantType)) {
            return null;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return null;
        }

        String scope = request.getParameter(OAuth2ParameterNames.SCOPE);
        Set<String> scopes = Collections.emptySet();
        if (StringUtils.hasText(scope)) {
            scopes = new HashSet<>(Set.of(scope.split(" ")));
        }

        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();

        return new PasswordGrantAuthenticationToken(clientPrincipal, username, password, scopes);
    }
}
