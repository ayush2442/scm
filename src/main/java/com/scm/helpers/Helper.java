package com.scm.helpers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class Helper {

    @Value("${server.baseUrl}")
    private String baseUrl;

    public static String getEmailOfLoggedInUser(Authentication authentication) {

        // method logic for email password login, then how to extract email
        if (authentication instanceof OAuth2AuthenticationToken) {

            var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
            var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User = (OAuth2User)authentication.getPrincipal();
            String username = "";
            
            if (clientId.equalsIgnoreCase("google")) {

            // if done with google
                System.out.println("Getting email from Google...");

            } 
            else if (clientId.equalsIgnoreCase("github")) {

                // if done with github
                username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString() : oauth2User.getAttribute("login").toString() + "@gmail.com";
                System.out.println("Getting email from Github...");

            }

            return username;
        }
        else {
            System.out.println("Getting data from Local Database...");
            
            return authentication.getName();
        }
    }

    public String getLinkForEmailVerification(String emailToken) {

        return this.baseUrl + "/auth/verify-email?token=" + emailToken;

    }
}
