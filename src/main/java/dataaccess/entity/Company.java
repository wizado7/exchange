package dataaccess.entity;


public class Company {
    private Long id;
    private String name;
    private String contactDetails;
    private String description;
    private String address;
    private Integer vacancyCount;

    public Company() {
    }

    public Company(Long id, String name, String contactDetails, String description, String address) {
        this.id = id;
        this.name = name;
        this.contactDetails = contactDetails;
        this.description = description;
        this.address = address;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contactDetails='" + contactDetails + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public void setVacancyCount(int vacancyCount) {
        this.vacancyCount = vacancyCount;
    }
    public Integer getVacancyCount() {
        return vacancyCount;
    }
}