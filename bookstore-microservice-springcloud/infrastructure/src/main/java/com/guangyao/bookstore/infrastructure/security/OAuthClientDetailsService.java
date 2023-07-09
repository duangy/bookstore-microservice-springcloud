package com.guangyao.bookstore.infrastructure.security;

import com.guangyao.bookstore.domain.security.GrantType;
import com.guangyao.bookstore.domain.security.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

@Named
public class OAuthClientDetailsService implements ClientDetailsService {

    private static class Client {
        String clientId;
        String clientSecret;
        String[] grantTypes;
        String[] scopes;

        public Client(String clientId, String clientSecret, String[] grantTypes, String[] scopes) {
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            this.grantTypes = grantTypes;
            this.scopes = scopes;
        }
    }

    private static final List<Client> clients = Arrays.asList(
            new Client("bookstore_frontend", "bookstore_secret", new String[]{GrantType.PASSWORD, GrantType.REFRESH_TOKEN}, new String[]{Scope.BROWSER}),
            new Client("account", "account_secret", new String[]{GrantType.CLIENT_CREDENTIALS}, new String[]{Scope.SERVICE}),
            new Client("warehouse", "warehouse_secret", new String[]{GrantType.CLIENT_CREDENTIALS}, new String[]{Scope.SERVICE}),
            new Client("payment", "payment_secret", new String[]{GrantType.CLIENT_CREDENTIALS}, new String[]{Scope.SERVICE}),
            new Client("security", "security_secret", new String[]{GrantType.CLIENT_CREDENTIALS}, new String[]{Scope.SERVICE})
    );

    @Inject
    private PasswordEncoder passwordEncoder;

    private ClientDetailsService clientDetailsService;

    @PostConstruct
    public void init() throws Exception {
        InMemoryClientDetailsServiceBuilder builder = new InMemoryClientDetailsServiceBuilder();
        clients.forEach(client -> {
            builder.withClient(client.clientId)
                    .secret(passwordEncoder.encode(client.clientSecret))
                    .scopes(client.scopes)
                    .authorizedGrantTypes(client.grantTypes);
        });
        clientDetailsService = builder.build();
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return clientDetailsService.loadClientByClientId(clientId);
    }
}
