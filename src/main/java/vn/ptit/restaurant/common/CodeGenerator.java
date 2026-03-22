package vn.ptit.restaurant.common;

import java.security.SecureRandom;

public final class CodeGenerator {

    private static final String DIGITS = "0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private CodeGenerator() {
    }

    public static String generateCode(String prefix, int totalLength) {
        if (prefix == null) {
            prefix = "";
        }
        if (totalLength <= prefix.length()) {
            return prefix.substring(0, totalLength);
        }
        int suffixLength = totalLength - prefix.length();
        StringBuilder sb = new StringBuilder(totalLength);
        sb.append(prefix);
        for (int i = 0; i < suffixLength; i++) {
            int idx = RANDOM.nextInt(DIGITS.length());
            sb.append(DIGITS.charAt(idx));
        }
        return sb.toString();
    }
}
