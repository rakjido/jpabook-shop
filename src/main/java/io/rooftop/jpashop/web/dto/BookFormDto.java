package io.rooftop.jpashop.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookFormDto {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;
}
