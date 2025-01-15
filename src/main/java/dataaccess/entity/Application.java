package dataaccess.entity;

import java.time.LocalDateTime;


public class Application {

    private Long id;

    private User user;

    private Vacancy vacancy;

    private String status;

    private LocalDateTime submissionDate;
    private long userId;
    private long vacancyId;

    public Application() {
    }

    public Application(Long id, User user, Vacancy vacancy, String status, LocalDateTime submissionDate) {
        this.id = id;
        this.user = user;
        this.vacancy = vacancy;
        this.status = status;
        this.submissionDate = submissionDate;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", user=" + user +
                ", vacancy=" + vacancy +
                ", status='" + status + '\'' +
                ", submissionDate=" + submissionDate +
                '}';
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(long vacancyId) {
        this.vacancyId = vacancyId;
    }
}