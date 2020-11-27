package springbootproject.springboot.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private int id;
    private String name, description;
    private LocalDate deadLineDate;
    private boolean isCompleted;

    public String isCompletedStr() {
        return isCompleted ? "Yes" : "No";
    }
}
