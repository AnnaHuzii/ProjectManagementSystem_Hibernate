package web.command.developers;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import servisDB.developer.Developer;
import servisDB.developer.DeveloperDaoService;
import servisDB.developer.DeveloperService;
import web.settings.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ListMiddleDevelopersCommand implements Command {
    DeveloperService developerDaoService = DeveloperDaoService.getInstance();
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException, SQLException {
        resp.setContentType("text/html; charset=utf-8");
        List<Developer> ListMiddleDevelopers = developerDaoService.getListMiddleDevelopers();
        ArrayList list = new ArrayList();
        Collections.addAll(list, ListMiddleDevelopers);
        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("list", list)
        );


        engine.process("developers_list_middle", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
