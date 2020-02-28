package ca.mcgill.ecse321.petadoption.dto;

import ca.mcgill.ecse321.petadoption.model.Advertisement;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

public class ImageDto {

    private String name;
    private String link;
    private String imageId;
    private String advertisementId;

    public ImageDto(String name, String link, String imageId, String advertisementId){
        this.name = name;
        this.link = link;
        this.imageId = imageId;
        this.advertisementId = advertisementId;
    }
    public String getName() {
        return this.name;
    }

    public String getLink() {
        return this.link;
    }

    public String getImageId() {
        return this.imageId;
    }

    public String getAdvertisementId() {
        return this.advertisementId;
    }


}
