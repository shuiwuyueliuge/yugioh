package cn.mayu.yugioh.common.security.application.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;

@RestController
@SpringBootApplication
public class TestApp {

    public static void main(String[] args) {
        SpringApplication.run(TestApp.class, args);
    }

    @RequestMapping("/test")
    public String test() {
        return "test controller";
    }

   // @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("-----BEGIN RSA PRIVATE KEY-----\n" +
                "            MIIEowIBAAKCAQEAm4irSNcR7CSSfXconxL4g4M4j34wTWdTv93ocMn4VmdB7rCB\n" +
                "            U/BlxXtBUf/cgLIgQhQrAPszSZSmxiEXCOkGPr4aQBQuPgmNIR95Dhbzw/ZN0Bne\n" +
                "            cAt3ZfkkDBHv8kH3kR/jYGTdwrxKeDgXGljNsTRhbjuASxPG/Z6gU1yRPCsgc2r8\n" +
                "            NYnztWGcDWqaobqjG3/yzFmusoAboyV7asIpo4yk378LmonDNwxnOOTb2Peg5Pee\n" +
                "            lwfOwJPbftK1VOOt18zA0cchw6dHUzq9NlB8clps/VdBap9BxU3/0YoFXRIc18ny\n" +
                "            zrWo2BcY2KQqX//AJC3OAfrfDmo+BGK8E0mp8wIDAQABAoIBAENp64P45GXMPEpx\n" +
                "            eYPpfxnRqJRZh6olHSHOl087243n16YTjxrI2fPMxrU6B2Mo0d6SS0lzl/lOmzLJ\n" +
                "            aOiNyA0t7MbVeG2fSjKPJ7M5s5K+kV+fttAtyCTE5iDtLWl9ukaG4dEIJy6e2lBd\n" +
                "            T3Y2A4HJSGm1FJh2DAwl0ywOtUy0X6ki9DgXVAaCGDuoU25Rhun64dh802DZbEEJ\n" +
                "            LdorIyeJ0ovCZyNvhlZRYkAOPy3k88smYl2jE/AbZ7pCKz/XggDcjNsERm2llaa3\n" +
                "            pNTAZQUlHu0BQrCn6J9BxtMPyduiyrE+JYqTwnYhWQ5QRe/2J8O3t0eIK9TfUQpJ\n" +
                "            DrZf00ECgYEAy/sLX8UCmERwMuaQSwoM0BHTZIc0iAsgiXbVOLua9I3Tu/mXOVdH\n" +
                "            TikjdoWLqM62bA9dN/oqzHDwvqCy6zwamjFVSmJUejf5v+52Qj64leOmDX/RC4ne\n" +
                "            L08N1nP/Y4X24Y/5zq18qvVlhOMDdydzayJFrGhkQKhJg58pRUIdenECgYEAwzLC\n" +
                "            Awr3LeUlHa+d2O6siJVmljTc8lT+qX4TvqTDH8rAC/EyKMNaTjaX6mWosZZ7qYXv\n" +
                "            EMxvQzTEzUHRXrCGlhbX8xiBlWnvpghF2GJEvP9WaU/+OCr0gItRSLPDuZ6ctzKb\n" +
                "            3QkBEiC8ODyPRKzlA67D23S3KJB067IUV81h9KMCgYBXUqmT3is2NFYz9DBhb3P8\n" +
                "            vyTYLGl4tArBznWJTAcSGoVCO59ZlNuZwlLEMnePVK8To6AsjpQz4UWu1ezCd4CL\n" +
                "            8gKpTV8M01m/qL5HrcInqMU1kjpTzjmn1xf9brsuR/NgrNoseGieZ1+GfAjHwcPP\n" +
                "            YWSiYi5I38JY7pIkbCFigQKBgAnVtty8YrPXRcV3IbbaX6sKC/8pbrBvA926Unha\n" +
                "            iNJDPuXbIzHWleg26/SNZrB76oMiEmeARWLXd8r3s/rXXhCV2g+PfofurHprFEnQ\n" +
                "            ubHkE5B+zUo7L9KCMng9RnFFwpOgYyYB3CHzsEgNFRLauzcySP/3o3rRvHJbqJa7\n" +
                "            7GGNAoGBAKSBn4zq0iNWI2BUBb90icMsHEneiydGtFcEl3/Sz8vmjFZn0sjRbGoY\n" +
                "            gmP9LlQ+o7xRiJ/LTesi5BA6zCGrcdp0aeyJzCRbFc3WqjGeyLbfx1sJVVB6PnvS\n" +
                "            iKvvCOJq6kl3/opO+ybqJ8dzkEyoj8K4+fcX1+U6eW2w+vSpOosG\n" +
                "            -----END RSA PRIVATE KEY-----");
        converter.setVerifierKey("-----BEGIN PUBLIC KEY-----\n" +
                "            MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm4irSNcR7CSSfXconxL4\n" +
                "            g4M4j34wTWdTv93ocMn4VmdB7rCBU/BlxXtBUf/cgLIgQhQrAPszSZSmxiEXCOkG\n" +
                "            Pr4aQBQuPgmNIR95Dhbzw/ZN0BnecAt3ZfkkDBHv8kH3kR/jYGTdwrxKeDgXGljN\n" +
                "            sTRhbjuASxPG/Z6gU1yRPCsgc2r8NYnztWGcDWqaobqjG3/yzFmusoAboyV7asIp\n" +
                "            o4yk378LmonDNwxnOOTb2Peg5PeelwfOwJPbftK1VOOt18zA0cchw6dHUzq9NlB8\n" +
                "            clps/VdBap9BxU3/0YoFXRIc18nyzrWo2BcY2KQqX//AJC3OAfrfDmo+BGK8E0mp\n" +
                "            8wIDAQAB\n" +
                "            -----END PUBLIC KEY-----");
        return converter;
    }

    @Bean
    UserDetailsService a() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return new User("user", new BCryptPasswordEncoder().encode("user"), Collections.emptyList());
            }
        };
    }
}
