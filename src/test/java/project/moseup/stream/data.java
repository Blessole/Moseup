package project.moseup.stream;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class data {

    private Long id;
    private String title;
    private int price;

    public String getName(){
        System.out.println("getName() 호출됨");
        return "정찬우";
    }
}
