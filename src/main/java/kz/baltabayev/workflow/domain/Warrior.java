package kz.baltabayev.workflow.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Warrior implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String gender;
    private Integer hp;
    private Boolean isAlive;

}
