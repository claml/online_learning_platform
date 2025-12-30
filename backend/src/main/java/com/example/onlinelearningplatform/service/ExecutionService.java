package com.example.onlinelearningplatform.service;

import com.example.onlinelearningplatform.dto.ExecutionRequest;
import com.example.onlinelearningplatform.dto.ExecutionResponse;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.*;

@Service
public class ExecutionService {
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    private static final long PY_TIMEOUT = 2000;
    private static final long COMPILE_TIMEOUT = 5000;
    private static final int MAX_OUTPUT = 8 * 1024;

    public ExecutionResponse execute(ExecutionRequest request) throws Exception {
        return executorService.submit(() -> runInDocker(request)).get();
    }

    private ExecutionResponse runInDocker(ExecutionRequest request) throws IOException, InterruptedException {
        String lang = request.language().toLowerCase();
        String filename;
        String image;
        List<String> command;
        switch (lang) {
            case "python" -> {
                filename = "main.py";
                image = "python:3.11-slim";
                command = List.of("python", "/workspace/main.py");
            }
            case "java" -> {
                filename = "Main.java";
                image = "eclipse-temurin:17-jdk";
                command = List.of("/bin/sh", "-c", "javac /workspace/Main.java && java -cp /workspace Main");
            }
            case "c" -> {
                filename = "main.c";
                image = "gcc:13";
                command = List.of("/bin/sh", "-c", "gcc -std=c11 /workspace/main.c -o /workspace/a.out && /workspace/a.out");
            }
            case "cpp", "c++" -> {
                filename = "main.cpp";
                image = "gcc:13";
                command = List.of("/bin/sh", "-c", "g++ -std=c++17 /workspace/main.cpp -o /workspace/a.out && /workspace/a.out");
            }
            default -> throw new IllegalArgumentException("Unsupported language");
        }
        Path tempDir = Files.createTempDirectory("exec-");
        Path codeFile = tempDir.resolve(filename);
        try (FileWriter fw = new FileWriter(codeFile.toFile(), StandardCharsets.UTF_8)) {
            fw.write(request.code());
        }
        if (request.stdin() != null) {
            Files.writeString(tempDir.resolve("input.txt"), request.stdin(), StandardCharsets.UTF_8);
        }
        String runCmd = String.join(" ", command);
        List<String> dockerCommand = List.of(
                "docker", "run", "--rm", "--network", "none",
                "--cpus", "0.5", "--memory", "256m",
                "-v", tempDir.toAbsolutePath() + ":/workspace",
                "-w", "/workspace",
                image,
                "/bin/sh", "-c",
                buildExecScript(request.stdin() != null, runCmd)
        );
        ProcessBuilder builder = new ProcessBuilder(dockerCommand);
        builder.redirectErrorStream(true);
        long timeout = lang.equals("python") ? PY_TIMEOUT : COMPILE_TIMEOUT;
        long start = System.currentTimeMillis();
        Process process = builder.start();
        boolean finished = process.waitFor(timeout, TimeUnit.MILLISECONDS);
        int exitCode;
        String output;
        boolean truncated = false;
        if (!finished) {
            process.destroyForcibly();
            exitCode = -1;
            output = "Time limit exceeded";
        } else {
            exitCode = process.exitValue();
            byte[] data = process.getInputStream().readAllBytes();
            truncated = data.length > MAX_OUTPUT;
            output = new String(truncated ? java.util.Arrays.copyOf(data, MAX_OUTPUT) : data, StandardCharsets.UTF_8);
        }
        long duration = System.currentTimeMillis() - start;
        deleteDirectory(tempDir.toFile());
        return new ExecutionResponse(output, "", exitCode, duration, truncated);
    }

    private String buildExecScript(boolean hasInput, String runCmd) {
        String inputPart = hasInput ? "< /workspace/input.txt" : "";
        return runCmd + " " + inputPart;
    }

    private void deleteDirectory(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDirectory(f);
            }
        }
        file.delete();
    }
}
