package servisDB.developer;

import connection.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import servisDB.project.Project;
import servisDB.project.ProjectDaoService;
import servisDB.project.ProjectService;
import servisDB.skill.Industry;
import servisDB.skill.Level;
import servisDB.skill.Skills;

import java.sql.Date;
import java.util.*;
import java.util.List;

public class DeveloperDaoService implements DeveloperService {
    private static final DeveloperDaoService INSTANCE;

    static {
        INSTANCE = new DeveloperDaoService();
    }

    public static DeveloperDaoService getInstance() {
        return INSTANCE;
    }

    @Override
    public Developer getDeveloperById(long id) {
        Session session = openSession();
        Developer developer = session.get(Developer.class, id);
        session.close();
        return developer;
    }

    @Override
    public List<Developer> getListDeveloper() {
        Session session = openSession();
        List<Developer> developers = session.createQuery("FROM Developer", Developer.class).list();
        session.close();
        return developers;
    }

    @Override
    public Developer createDeveloper(Developer developer) {
        Session session = openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(developer);
        transaction.commit();
        session.close();
        return developer;
    }

    @Override
    public List<Developer> getDeveloperByNameAndDate(String fullName, Date birthDate) {
        Session session = openSession();
        Transaction transaction = session.beginTransaction();
        List <Developer> developer = session.createQuery("SELECT * FROM developers " +
                        "WHERE full_name = :paramFullName, birth_date = :paramBirthDate",
                Developer.class).
                setParameter("paramFullName", fullName).
                setParameter("paramBirthDate",birthDate).
                list();
        transaction.commit();
        session.close();
        return developer;
    }

    @Override
    public List<Developer> getListMiddleDevelopers() {

        return null;
    }

    @Override
    public boolean updateDeveloper(Developer developer, long[] skillIds) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(developer);
            transaction.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteDeveloperById(long id) {
        Session session = openSession();
        Transaction transaction = session.beginTransaction();
        Developer developerToDelete = getDeveloperById(id);
        if (developerToDelete != null) {
            session.remove(developerToDelete);
            transaction.commit();
            session.close();
            return true;
        } else {
            transaction.commit();
            session.close();
            return false;
        }
    }

    @Override
    public List<Developer> getDevelopersByDepartment(String department) {
        return null;
    }

    @Override
    public List<Developer> getDevelopersBySkillLevel(String skillLevel) {
        return null;
    }
    private Session openSession() {
        return HibernateUtil.getInstance().getSessionFactory().openSession();
    }

}