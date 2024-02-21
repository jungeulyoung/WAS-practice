package org.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.example.calculate.Calculator;
import org.example.calculate.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Step1 - 사용자 요청을 메인 Thread가 처리하도록 한다.
 * <p>
 * step2 - 사용자 요청이 들어올 때마다 Thread를 새로 생성해서 사용자 요청을 처리하도록 한다.
 */

public class CustomWebApplicationServer {
    private final int port;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServer.class);

    public CustomWebApplicationServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServer] started {} port.", port);

            Socket clientSocket;
            logger.info("[CustomWebApplicationServer] waiting for client");

            while ((clientSocket = serverSocket.accept()) != null) {
                logger.info("[CustomWebApplicationServer] client connected");


                /**
                 *step2 - 사용자 요청이 들어올 때마다 Thread를 새로 생성해서 사용자 요청을 처리하도록 한다.
                 *
                 * Thread는 생성 될때 마다 독립적인 스택 메모리 공간을 할당 받는다.
                 *
                 * 요청이 많아지면 성능이 상당히 떨어진다. cpu와 메모리 사용량이 증가한다
                 * 최악의 상황에는 서버가 다운 될 수 있다.
                 */
                // 스레드 생성
//                new Thread(new ClientRequestHandler(clientSocket)).start();


                /**
                 *
                 * Step3 - Thread Pool을 적용해 안정적인 서비스가 가능하도록 한다.
                 * */
                executorService.execute(new ClientRequestHandler(clientSocket));

            }

        }
    }
}
