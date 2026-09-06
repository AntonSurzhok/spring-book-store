package mate.academy.bookstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryDto {

    @NotBlank(message = "Category name can't be blank")
    private String name;

    private String description;
}
