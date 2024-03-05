package com.BKHOSTEL.BKHOSTEL.Entity;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("bkhostel")
    public class User2 {

        @Id
        private String id;

        private String name;
        private int quantity;
        private String category;

        public User2(String id, String name, int quantity, String category) {
            super();
            this.id = id;
            this.name = name;
            this.quantity = quantity;
            this.category = category;
        }

    public User2(String name, int quantity, String category) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }
}
