package command.developers;

import command.settings.Command;
import connection.Storage;
import db.developer.DeveloperDaoService;
import db.developer.Sex;
import db.skill.Industry;
import db.skill.Level;
import org.thymeleaf.TemplateEngine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class UpdateDeveloperCommand implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException, SQLException {

        PrintWriter out = resp.getWriter();
        Storage storage = Storage.getInstance();
        String fullName = "";
        Date birthDate = null;

        try {
            fullName = req.getParameter("developerFullName");
            birthDate = Date.valueOf(LocalDate.parse(req.getParameter("developerBirthDate")));
        } catch (Exception e) {
            resp.getWriter().write("<h1>You have entered incorrect data, please try again</h1>");
            out.println("<p >" +
                    "<form action=/hw_servlets/developers method=GET>" +
                    "<input type=submit value='Developer menu' />" +
                    "</p>");
            resp.getWriter().close();
        }
        String email = req.getParameter("developerEmail");
        String sex = req.getParameter("developerSex");
        Sex sexName = null;
        if (sex.equals(Sex.MALE.getSexName())) {
            sexName = Sex.MALE;
        } else if (sex.equals(Sex.FEMALE.getSexName())) {
            sexName = Sex.FEMALE;
        } else if (sex.equals(Sex.UNKNOWN.getSexName())) {
            sexName = Sex.UNKNOWN;
        }
        String skype = req.getParameter("developerSkype");
        String project = req.getParameter("developerProject");
        float salary = Float.parseFloat(req.getParameter("developerSalary"));
        String industry = req.getParameter("developerLanguage");
        Industry industryName = null;
        if (industry.equals(Industry.C_PLUS_PLUS.getIndustryName())) {
            industryName = Industry.C_PLUS_PLUS;
        } else if (industry.equals(Industry.C_SHARP.getIndustryName())) {
            industryName = Industry.C_SHARP;
        } else if (industry.equals(Industry.JS.getIndustryName())) {
            industryName = Industry.JS;
        } else if (industry.equals(Industry.JAVA.getIndustryName())) {
            industryName = Industry.JAVA;
        }
        String languageLevel = req.getParameter("developerLanguageLevel");
        Level levelName = null;
        if (languageLevel.equals(Level.JUNIOR.getLevelName())) {
            levelName = Level.JUNIOR;
        } else if (languageLevel.equals(Level.MIDDLE.getLevelName())) {
            levelName = Level.MIDDLE;
        } else if (languageLevel.equals(Level.SENIOR.getLevelName())) {
            levelName = Level.SENIOR;
        }

        DeveloperDaoService developerDaoService = new DeveloperDaoService(storage.getConnection());
        long idToDelete = 0;

        try {
            idToDelete = developerDaoService.getIdByFullName(fullName, birthDate);
        } catch (SQLException e) {
            resp.getWriter().write("<h1>" + "There is no such developer in the database. Enter the correct data.");
        }
        developerDaoService.editDeveloperVod(idToDelete);

        String editDeveloper = developerDaoService.editDeveloper(fullName, birthDate, sexName, email, skype, salary,project,
                                                                    industryName, levelName);
        resp.getWriter().write("<h1>" + editDeveloper + "</h1>");
        resp.getWriter().write("<h3>" + fullName + " - Birth date: " + birthDate + "</h3>");
        resp.getWriter().write("<h3>" + "This developer will be a participant in the project - " + project + "</h3>");
        resp.getWriter().write("<h3>" + "Developer salary - " + salary + "</h3>");
        resp.getWriter().write("<h3>" + "Has a programming language - " + industryName + "</h3>");
        resp.getWriter().write("<h3>" + "Has a level of knowledge of the programming language - " + levelName + "</h3>");
        out.println("<p >" +
                "<form action=/hw_servlets/developers method=GET>" +
                "<input type=submit value='Developer menu' />" +
                "</p>");
        resp.getWriter().close();
    }
}
