package ca.mcgill.ecse321.petadoption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PetAdoptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetAdoptionApplication.class, args);
	}
@RequestMapping("/")
  public String greeting(){
    return "Hello world! from PetAdoption Backend!";
  }

}
