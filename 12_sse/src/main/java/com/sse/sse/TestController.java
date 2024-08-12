package com.sse.sse;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
public class TestController {

//    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public void connect(HttpServletResponse response) throws IOException, InterruptedException {
//        response.setContentType(MediaType.TEXT_EVENT_STREAM_VALUE);
//        response.setCharacterEncoding(Encoding.DEFAULT_CHARSET.toString());
//        PrintWriter writer = response.getWriter();
//
//
//        for (int i = 0; i < 5; i++){
//            writer.write("data: " + System.currentTimeMillis() + "\n\n");
//            writer.flush();
//            Thread.sleep(1000);
//        }
//        writer.close();
//    }


    SseEmitter emitter;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter emitterConnect() throws IOException, InterruptedException {
        emitter = new SseEmitter();
        return emitter;
    }

    @GetMapping("/go")
    public void go() throws InterruptedException, IOException {

        Data data = new Data();
        for (int i = 0; i < 10; i++){
            Thread.sleep(1000);
            emitter.send(SseEmitter.event()
                    .id("1")
                    .name("이름")
                    .data("데이터"));
        }
        emitter = null;
    }

    class Data{
        String name = "권하준";
        int age = 25;
        String bd = "2000.12.09";
    }

}
