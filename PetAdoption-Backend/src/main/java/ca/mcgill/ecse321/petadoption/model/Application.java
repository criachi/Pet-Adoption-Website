package ca.mcgill.ecse321.petadoption.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Date;
import java.util.UUID;

@Entity
public class Application{
    private Date dateOfSubmission;
    private String note;
    private Advertisement advertisement;
    private AppUser applicant;
    private String applicationId;

    @Enumerated(EnumType.STRING)
    private Status status;


    public void setDateOfSubmission(Date value) {
        this.dateOfSubmission = value;
    }
    public Date getDateOfSubmission() {
        return this.dateOfSubmission;
    }



    public void setStatus(Status value) {
        this.status = value;
    }
    public Status getStatus() {
        return this.status;
    }

    public void setNote(String value) {
        this.note = value;
    }
    public String getNote() {
        return this.note;
    }

    @ManyToOne(optional=false)
    public Advertisement getAdvertisement() {
       return this.advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
       this.advertisement = advertisement;
    }


    public void setApplicationId(String value) {
        this.applicationId = value;
    }

    public void setApplicationId() {
        this.applicationId = UUID.randomUUID().toString();
    }
    @Id
    public String getApplicationId() {
        return this.applicationId;
    }

    @ManyToOne(optional=false)
    public AppUser getApplicant() {
      return this.applicant;
    }

    public void setApplicant(AppUser applicant) {
      this.applicant = applicant;
    }



}
