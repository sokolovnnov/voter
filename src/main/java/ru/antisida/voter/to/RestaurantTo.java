package ru.antisida.voter.to;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

public class RestaurantTo extends BaseTo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    @NotBlank
    private String name;

    public RestaurantTo(){}

    public RestaurantTo(@NotNull Integer id, @NotEmpty String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
