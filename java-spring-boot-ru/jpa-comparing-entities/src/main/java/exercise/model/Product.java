package exercise.model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;



// BEGIN
@Entity
@Table(name = "products")
@Getter
@Setter
@EqualsAndHashCode(of = {"title", "price"})
public class Product {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    private String title;
    private int price;
}
// END
