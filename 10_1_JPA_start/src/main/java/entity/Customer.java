package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// @Entity: jpa를 쓰기 위한 객체임을 명시
@Entity
@Table(name="customer_tb")  // 어떤 테이블에 데이터가 들어갈지 명시
public class Customer {
    @Id // pk 변수 명시
    private String id;
    private String name;
    private long registerDate;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
        this.registerDate = System.currentTimeMillis();
    }

    public static Customer sample(){
        return new Customer("ID0001", "Kim");
    }
}
