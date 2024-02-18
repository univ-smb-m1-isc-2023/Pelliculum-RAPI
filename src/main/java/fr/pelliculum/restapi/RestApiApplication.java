package fr.pelliculum.restapi;

import fr.pelliculum.restapi.entities.Role;
import fr.pelliculum.restapi.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }


    @Bean
    public CommandLineRunner demo(RoleRepository roleRepo) {
        return (args) -> {
            Role role=new Role();
            role.setName("ROLE_ADMIN");
            roleRepo.save(role);
        };
    }
}
