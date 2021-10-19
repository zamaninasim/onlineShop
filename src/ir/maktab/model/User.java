package ir.maktab.model;

import ir.maktab.model.enumeration.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
@AllArgsConstructor
@Data
public class User {
    private Integer id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private Gender gender;
    private Date birthDate;
    private String nationalId;

    public User(String fullName, String phoneNumber, String email, Gender gender, Date birthDate, String nationalId) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.birthDate = birthDate;
        this.nationalId = nationalId;
    }
}
