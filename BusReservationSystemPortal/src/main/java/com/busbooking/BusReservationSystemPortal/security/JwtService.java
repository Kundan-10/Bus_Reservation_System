package com.busbooking.BusReservationSystemPortal.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;


@Service
public class JwtService {


//    private static final String SECRET_KEY = "mnbvcxzasdfghjklqwertyuiopmandaqeruaoepkdhalkghlaghlakdfhxnczvhzdjhduaidfaudfaddfku";
//    private static final long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000; // 15 min
//    private static final long REFRESH_TOKEN_EXPIRATION = 48 * 60 * 60 * 1000; // 48 hours

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${jwt.access-token.expiration}")
    private Long ACCESS_TOKEN_EXPIRATION;

    @Value("${jwt.refresh-token.expiration}")
    private Long REFRESH_TOKEN_EXPIRATION;

    public String generateToken(String username, boolean isAccessToken) {

        long expiration = isAccessToken ? ACCESS_TOKEN_EXPIRATION : REFRESH_TOKEN_EXPIRATION;
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
   public String getUserNameFromToken(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
   }


    public boolean validateToken(String token) {
        try{
            Jwts.parser().setSigningKey(SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (JwtException e){
            return false;
        }
    }

}
