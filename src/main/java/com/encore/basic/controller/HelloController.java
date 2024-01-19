package com.encore.basic.controller;

import com.encore.basic.domain.Hello;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

//@RestController - 모든 요청에 @ResponseBody 붙여주는것// 보통 프론트 따로 하는 view쓰는 거에
@Controller
//클래스 차원에서 url경로를 지정하고 싶다면 @RequestMapping을 클래스 위에 선언하면서 경로 지정.
@RequestMapping("hello") // url쓸때 앞에 다 붙이는거

public class HelloController {

//    responseBody가 없고,return타입이 String이면, templates밑에 (retun에 지정되어있는 이름이 hello string인)html파일 리턴

//    데이터만을 return할때는 @ResponseBody를 붙인다.
    @GetMapping("hello/string") // local:8080/가 앞에 붙어있는겨, 밑에 메소드를 실행한다는 의미
//    @ResponseBody //없으면 화면 찾으러 감, csr형식으로 되어서 밑의 메소드의 return이 front가 되는것이다.....
    public String helloString1(){
        return "hello_string";
    }
    @GetMapping("string")
//   @GetMapping("string") == @RequestMapping(value = "string", method = RequestMethod.GET)
    @ResponseBody
    public String helloString2(){
        return "string";
    }

    @GetMapping("json")
    @ResponseBody
    public Hello helloJson(){
        Hello hello = new Hello();
        hello.setEmail("enuji@naver.com");
        hello.setName("귀여운 은지");
        hello.setPassword("1234");
        System.out.println(hello);
        return hello;
    } // get요청, hell/json, jsonReturn{name, email...}

    @GetMapping("screen")
    public String helloScreen(){
        return "screen";
    }

    @GetMapping("screen-modle-param")
//    파라미터 호출방식 - ?name=hongildong의 방식으로 호출 -> url뒤에 붙이는데로 화면에 name이 바뀌면서 뜬다.
                                                // key                    value
    public String helloScreenModelParam(@RequestParam(value = "name")String inputName, Model model){ //스프링에서 매개변수 자동으로 넣어줌
//        화면에 데이터를 넘기고 싶을때는 model객체를 사용
//        model의 key:value형식으로 전달
        model.addAttribute("myData",inputName);
        return "screen";
    }// get요청 /hello/screen-modle-param?name / return: screen.html


//    pathvariable방식은 url을 통해 자원의 구조를 명확하게 표현할수 있어, 좀더 Restful API (현대적)디자인에 적합
    @GetMapping("screen-modle-path/{id}")
//     id - 위의 사용자가 집어 넣은 id
    public String helloScreenModelPath(@PathVariable int id, Model model){
        model.addAttribute("myData",id);
        return "screen";
    } //get요청: /hello/screen-modle-path/{id} - 변수화, return: screen.html

//    Form 태그로 x-www데이터 처리
    @GetMapping("form-screen")
    public String formScreen(){
        return "hello-form-screen";
    }

    @PostMapping("form-post-handle")
//    form태그를 통한 body데이터 형태가 key1=value1&key2=value2랑 같다
    @ResponseBody
    public String formPostHandle(@RequestParam(value = "name")String name,
                                 @RequestParam(value = "email")String email,
                                 @RequestParam(value = "password")String password){
        System.out.println("name = " + name);
        System.out.println("email = " + email);
        System.out.println("password = " + password);
        return "정상처리";
    }// form-tag입력을 위한 화면전달 후 ->form-post-handle 1)RequestParam 2)데이터바인딩(Dto객체를 통해)
    @PostMapping("form-post-handle2")
    @ResponseBody
//    Spring에서 Hello 클래스의 인스턴스를 자동 매핑하여 생성
//    form-data형식, 즉, x-www-url인코딩 형식의 경우 사용
//    이를 데이터 바인딩 이라 부른다. (Hello클래스에 setter 필수)
    public String formPostHandle2(Hello hello){
        System.out.println(hello);
        return "정상처리";
    }


//    json데이터 퍼리
    @GetMapping("json-screen")
    public String jsonScreen(){
        return "hello-json-screen";
    }
    @PostMapping("/json-post-handle1")
    @ResponseBody
//  1번 방법 - hello-json-screen 에서 뒤의 url에 1번 붙이기
//  @ResponseBody: json으로 post요청이 들어왔을때 body에서 data를 꺼내기 위해 사용
    public String jsonPostHandle1(@RequestBody Map<String, String>body){
        System.out.println(body.get("name"));
        System.out.println(body.get("email"));
        System.out.println(body.get("password"));
        Hello hello = new Hello();
        hello.setName(body.get("name"));
        hello.setEmail(body.get("email"));
        hello.setPassword(body.get("password"));
        return "ok";
    }

    @PostMapping("/json-post-handle2")
    @ResponseBody
    public String jsonPostHandle2(@RequestBody JsonNode body){
        System.out.println(body.get("name").asText());
        System.out.println(body.get("email").asText());
        System.out.println(body.get("password").asText());
        return "ok";
    }

    @PostMapping("/json-post-handle3")
    @ResponseBody
    public String jsonPostHandle3(@RequestBody Hello hello){
        System.out.println(hello);
        return "ok";
    }
    @PostMapping("httpservlet")//이런거 잘 안써유
    @ResponseBody
    public String httpServletTest(HttpServletRequest req){
//        HttpServletRequest 객체에 header정보 추출
        System.out.println(req.getContentType());
        System.out.println(req.getMethod());
//        Session: 로그인(auth) 정보에서 필요한 정보값을 추출할때 많이 쓴다.
        System.out.println(req.getSession());
        System.out.println(req.getHeader("Accept"));// postman의 header에 hidden에 숨겨져 있음
//        HttpServletRequest객체에서 body정보 추출
        System.out.println(req.getParameter("test1"));  // postman에서 body에 key에 test1값 넣으면 됨
        System.out.println(req.getParameter("test2"));
//        req.getRequest()를 통해 BufferReader로 받아 직접 파싱
        return "OK";
    }
    @GetMapping("/hello-servlet-jsp-get")
    public String helloServletJspGet(Model model) {
        model.addAttribute("myData", "jsp test data");
        return "hello-jsp";
    }
}
