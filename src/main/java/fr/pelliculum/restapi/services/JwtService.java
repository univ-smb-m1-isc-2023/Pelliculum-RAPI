package fr.pelliculum.restapi.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "YiPIT+3hcoe6TLId3jJ0np0oGzi0RENjtR6xkWrQ1+nJSa9NsU+r1e41Y442dZLnOa9Nu+j/sdF7DgyckbpbSuOw1inMU4UDjiSoSmFVVoiP1MoLFQ35AlttBpxJF25uIYN4dlBA/pOlUhFrv0YtDLJ2qWGAhjkm2ValkpnEQntLiqP48Ompm0lLSynIrhh9i6hhKQDFDQ3cJiCA3IvNSQLmPWoLNflrXC8a2ap5lIpidod7ChY9wOnj6ADq7rRE8CRdXWrat76f1tt5SzIGjcuYdv0VbIXzFLk37s9YQdK8fNt3vHUxRDq434lmf5vT8ckyg23cAy+0+0e+f0mMivAEZhFO7SX4tFJYHBTxLos=";

    /**
     * Extracts a claim from a token, a claim is a piece of information about the user
     * @param token {@link String} the token from which to extract the claim
     * @param claimsResolver {@link Function<Claims, T>} the function to apply to the claims
     * @return {@link T} the claim
     * @param <T> the type of the claim
     */
    public<T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts the username from a token
     * @param token {@link String} the token from which to extract the username
     * @return {@link String} the username
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Generates a token for a user
     * @param userDetails {@link UserDetails} the user for which to generate the token
     * @return {@link String} the generated token
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a token for a user with extra claims
     * @param extraClaims {@link Map<String, Object>} the extra claims to add to the token
     * @param userDetails {@link UserDetails} the user for which to generate the token
     * @return {@link String} the generated token
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts all claims from a token
     * @param token {@link String} the token from which to extract the claims
     * @return {@link Claims} the claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if a token is valid for a user
     * @param jwt {@link String} the token to check
     * @param userDetails {@link UserDetails} the user to check
     * @return {@link Boolean} true if the token is valid for the user, false otherwise
     */
    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        final String username = extractUsername(jwt);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
    }

    /**
     * Checks if a token is expired
     * @param token {@link String} the token to check
     * @return {@link Boolean} true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from a token
     * @param token {@link String} the token from which to extract the expiration date
     * @return {@link Date} the expiration date
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Gets the signing key
     * @return {@link Key} the signing key
     */
    private Key getSigningKey() {
        byte[] secretBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secretBytes);
    }

}
