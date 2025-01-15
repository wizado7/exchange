package dataaccess.entity;



public class Vacancy {
    private Long id;
    private Long companyId;
    private String title;
    private Company company;
    private String description;
    private String requiredSkills;
    private String status;
    private int applicationsCount;


    public Vacancy() {
    }

    public Vacancy(Long id, String title, Company company, String description, String requiredSkills, String status) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.description = description;
        this.requiredSkills = requiredSkills;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", company=" + company +
                ", description='" + description + '\'' +
                ", requiredSkills='" + requiredSkills + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getCompanyId() {
        return companyId;
    }
    public int getApplicationsCount() {
        return applicationsCount;
    }
    public void setApplicationsCount(int applicationsCount) {
        this.applicationsCount = applicationsCount;
    }
}