package Bankamat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Foydalanuvchilar {
    private Double plastik_raqam;
    private Integer pin_kod;
    private Integer summa;
}
