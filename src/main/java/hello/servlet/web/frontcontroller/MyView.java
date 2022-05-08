package hello.servlet.web.frontcontroller;

import lombok.Value;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class MyView {
    private String viewPath;    //"/WEB-INF/views/members.jsp"

    public MyView(String viewPath) {
        this.viewPath = viewPath;
    }

    /**
     * jsp를 forward해서 rendering하게 만들어줌
     * (view를 만들어주는 행위 자체를 rendering한다고 생각하자 forwarding할수도있고, 직접 그려서 보낼수도있고..)
     */
    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }

    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        modelToRequestAttribute(model, request);
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }

    private void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest request) {
        model.forEach((key, Value) -> request.setAttribute(key, Value));
    }
}

