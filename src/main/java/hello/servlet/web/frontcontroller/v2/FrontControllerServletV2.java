package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// urlPatterns = "/front-controller/v2/*" : front-controller/v2 를 포함한 하위 모든 요청은 이 서블릿에서 받아들인다.
@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    //key: 매핑 URL
    //value: 호출될 컨트롤러
    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    // 생성자
    public FrontControllerServletV2() {
        // 생성할때 매핑 정보를 담아 놓는다.
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println("FrontControllerServletV1.service");

        /**
         *  URI 가져오기
         *  ex ) /front-controller/v2/members
         */
        String requestURI = request.getRequestURI();

        /**
         * key와 일치하는 value를 ControllerV2 controller에 반환
         *  ex )  ControllerV2 controller = new MemberListControllerV1();
         */
        ControllerV2 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);   // 404 에러
            return;
        }

        /**
         * 컨트롤러를 찾고 해당 controller의 override된 process(request, response); 을 호출해서 해당 컨트롤러를 실행한다.
         *  ex )  MemberListControllerV2.process(request, response) 호출
         *        new MyView("/WEB-INF/views/members.jsp"); 을 반환
         */
        MyView view = controller.process(request, response);
        view.render(request,response);
    }
}
