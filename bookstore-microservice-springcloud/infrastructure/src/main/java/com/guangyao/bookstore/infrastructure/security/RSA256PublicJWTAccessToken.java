package com.guangyao.bookstore.infrastructure.security;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.FileCopyUtils;

import javax.inject.Named;
import java.io.IOException;

@Named
public class RSA256PublicJWTAccessToken extends JWTAccessToken {
    public RSA256PublicJWTAccessToken(UserDetailsService userDetailsService) throws IOException {
        super(userDetailsService);
        Resource resource = new ClassPathResource("public.cert");
        String publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
        setVerifierKey(publicKey);
    }
}
