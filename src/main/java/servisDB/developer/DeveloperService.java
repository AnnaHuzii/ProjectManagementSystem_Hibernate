package servisDB.developer;

import java.sql.Date;
import java.util.List;

public interface DeveloperService {
    Developer createDeveloper(Developer developer);
    List<Developer> getDeveloperByNameAndDate(String fullName, Date birthDate);
    List <Developer> getListMiddleDevelopers();
    Developer getDeveloperById(long id);
    List<Developer> getListDeveloper() ;
    boolean updateDeveloper(Developer developer);
    boolean deleteDeveloperById(long id);
    int getDevelopersSkillLevel(String skillLevel);
}