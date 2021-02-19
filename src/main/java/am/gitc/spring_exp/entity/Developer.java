package am.gitc.spring_exp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Developer {
    private Integer id;
    private String firstName;
    private String lastName;
}
