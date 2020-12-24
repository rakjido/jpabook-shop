package io.rooftop.jpashop.domain;

import org.junit.Test;
import org.springframework.util.StringUtils;

public class JavaStringNullTest {

    @Test
    public void NullorWhitespace() throws Exception {
        // Given
        String str = "";
        // When
        boolean isHasText = StringUtils.hasText(str);
        // Then
        System.out.println("isHasText = " + isHasText);
    }

}
