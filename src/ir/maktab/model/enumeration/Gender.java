package ir.maktab.model.enumeration;

public enum Gender {
    FEMALE(1,"Male"),
    MALE(2,"Female"),
    NOT_SET(3,"NOT_SET");
    private Integer id;
    private String name;

    Gender(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Gender getVal(String name){
        for (Gender gender:values()){
            if (gender.name.equalsIgnoreCase(name.trim())){
                return gender;
            }
        }
        return NOT_SET;
    }
}
