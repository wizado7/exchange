package dataaccess.entity;




public class User {
    private Long id;
    private String name;
    private String contactDetails;
    private String skills;
    private String desiredPosition;
    private String employmentStatus;

    public User() {
    }

    public User(Long id, String name, String contactDetails, String skills, String desiredPosition, String employmentStatus) {
        this.id = id;
        this.name = name;
        this.contactDetails = contactDetails;
        this.skills = skills;
        this.desiredPosition = desiredPosition;
        this.employmentStatus = employmentStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getDesiredPosition() {
        return desiredPosition;
    }

    public void setDesiredPosition(String desiredPosition) {
        this.desiredPosition = desiredPosition;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contactDetails='" + contactDetails + '\'' +
                ", skills='" + skills + '\'' +
                ", desiredPosition='" + desiredPosition + '\'' +
                ", employmentStatus='" + employmentStatus + '\'' +
                '}';
    }
}