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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class TestController {

//    @GetMapping("/sse")
//    public void handleMultiEventSse(HttpServletResponse response) throws IOException, InterruptedException {
//        response.setContentType("text/event-stream");
//        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setHeader("Connection", "keep-alive");
//
//        PrintWriter writer = response.getWriter();
//
//        for (int i = 0; i < 3; i++) {
//            // 일반 메시지 이벤트
//            writer.write("id: 1\n");
//            writer.write("event: 1번\n");
//            writer.write("data: 1번 케이스입니다.\n\n");
//            writer.flush();
//            Thread.sleep(1000);
//
//            // 경고 이벤트
//            writer.write("id: 2\n");
//            writer.write("event: 2번\n");
//            writer.write("data: 2번 케이스 입니다.\n\n");
//            writer.flush();
//            Thread.sleep(1000);
//
//            // JSON 데이터 이벤트
//            writer.write("id: 3\n");
//            writer.write("event: 3번\n");
//            writer.write("data: 3번 케이스 입니다.\n\n");
//            writer.flush();
//            Thread.sleep(1000);
//        }
//
//        writer.close();
//    }


    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamSseMvc() {
        SseEmitter emitter = new SseEmitter();

        executorService.execute(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    SseEmitter.SseEventBuilder event = SseEmitter.event()
                            .data("테스트 " + i)
                            .id(String.valueOf(i))
                            .name("test");
                    emitter.send(event);
                    Thread.sleep(1000);
                }
                emitter.complete();
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });

        return emitter;
    }
}
