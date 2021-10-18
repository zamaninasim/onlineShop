package ir.maktab.model;

import ir.maktab.model.enumeration.Gender;
import lombok.Data;

import java.sql.Date;

@Data
public class User {
    private Integer id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private Gender gender;
    private Date birthDate;
    private String nationalId;
}
