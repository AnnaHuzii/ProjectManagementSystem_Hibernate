package web.command.customers;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import web.settings.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomersMenuCommand implements Command {
    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, TemplateEngine engine) throws IOException {
        resp.setContentType("text/html; charset=utf-8");

        Context context = new Context();
        context.setVariable("table", req.getParameter("table"));

        engine.process("customers", context, resp.getWriter());
        resp.getWriter().close();
    }
}