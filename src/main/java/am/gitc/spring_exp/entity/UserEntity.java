package am.gitc.spring_exp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("users")
public class UserEntity {

    @Id
    private int id;

    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    private String email;

    private String password;
}
