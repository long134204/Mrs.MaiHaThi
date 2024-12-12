package com.example.library_management.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookType {
    private int id;
    private int typeID;
    private int bookID;

    public BookType(int typeID, int bookID) {
        this.typeID = typeID;
        this.bookID = bookID;
    }
}
