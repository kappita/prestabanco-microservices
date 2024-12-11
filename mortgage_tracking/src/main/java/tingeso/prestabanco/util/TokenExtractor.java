package tingeso.prestabanco.util;

public class TokenExtractor {

    /**
     * Extracts the token from the Authorization header.
     *
     * @param authorizationHeader The Authorization header value.
     * @return The extracted token, or null if the header is invalid.
     */
    public static String extractToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null; // Invalid header
        }
        return authorizationHeader.substring(7); // Remove "Bearer " prefix
    }
}