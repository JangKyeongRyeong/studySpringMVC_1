package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// urlPatterns = "/front-controller/v1/*" : front-controller/v1 를 포함한 하위 모든 요청은 이 서블릿에서 받아들인다.
@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    //key: 매핑 URL
    //value: 호출될 컨트롤러
    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    // 생성자
    public FrontControllerServletV1() {
        // 생성할때 매핑 정보를 담아 놓는다.
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /**
         *  URI 가져오기
         *  ex ) /front-controller/v1/members
         */
        String requestURI = request.getRequestURI();

        /**
         * key와 일치하는 value를 ControllerV1 controller에 반환
         *  ex )  ControllerV1 controller = new MemberListControllerV1();
         */
        ControllerV1 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);   // 404 에러
            return;
        }

        /**
         * 컨트롤러를 찾고 해당 controller의 override된 process(request, response); 을 호출해서 해당 컨트롤러를 실행한다.
         *  ex )  MemberListControllerV1.process(request, response)
         */
        controller.process(request, response);
    }
}
