package am.gitc.spring_exp.controllers.rest;

import am.gitc.spring_exp.entity.Developer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperController {

    private List<Developer> developers = Stream.of(
            new Developer(1,"arthur","martirosyan"),
            new Developer(2,"gago","martirosyan"),
            new Developer(3,"karen","galoyan")
    ).collect(Collectors.toList());

    @GetMapping
    @PreAuthorize("hasAuthority('developers:read')")
    public List<Developer> getDevelopers(){
        return this.developers;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    public Developer getDeveloperById(@PathVariable("id") int id){
       return this.developers.stream().filter(developer -> developer.getId().equals(id))
               .findFirst().orElse(null);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('developers:write')")
    public Developer createDeveloper(@RequestBody Developer developer){
        this.developers.add(developer);
        return developer;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public void deleteDeveloperById(@PathVariable("id") int id){
        this.developers.removeIf(developer -> developer.getId().equals(id));
    }
}
