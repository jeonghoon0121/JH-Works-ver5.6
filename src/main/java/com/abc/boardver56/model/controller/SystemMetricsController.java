package com.abc.boardver56.model.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/system")
@CrossOrigin(origins = "*") // 다른 포트/도메인에서 접근 허용
public class SystemMetricsController {

    @GetMapping("/metrics")
    public Map<String, Object> getMetrics() {
        Map<String, Object> metrics = new HashMap<>();

        // 1. CPU 사용률 (%) - 안전한 형변환 방식 적용
        java.lang.management.OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            double cpuLoad = ((com.sun.management.OperatingSystemMXBean) osBean).getSystemCpuLoad() * 100;
            metrics.put("cpuUsage", String.format("%.1f", cpuLoad < 0 ? 0 : cpuLoad));

            // 2. 메모리 잔량 (GB) - osBean이 com.sun.management일 때만 상세 메모리 확인 가능
            long totalMemory = ((com.sun.management.OperatingSystemMXBean) osBean).getTotalPhysicalMemorySize();
            long freeMemory = ((com.sun.management.OperatingSystemMXBean) osBean).getFreePhysicalMemorySize();
            double usedMemoryGB = (double) (totalMemory - freeMemory) / (1024 * 1024 * 1024);
            metrics.put("memoryUsed", String.format("%.2f", usedMemoryGB));
        } else {
            metrics.put("cpuUsage", "0.0");
            metrics.put("memoryUsed", "0.0");
        }

        // 3. 디스크 남은 용량 (GB)
        File root = new File("/");
        double freeSpaceGB = (double) root.getFreeSpace() / (1024 * 1024 * 1024);
        metrics.put("diskFree", String.format("%.2f", freeSpaceGB));

        // 4. 라즈베리파이 CPU 온도
        metrics.put("serverTemp", getRaspberryPiTemperature());

        return metrics;
    }
    private String getRaspberryPiTemperature() {
        try {
            // 라즈베리파이의 온도 센서 파일 읽기
            Process process = Runtime.getRuntime().exec("cat /sys/class/thermal/thermal_zone0/temp");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String tempRaw = reader.readLine();
            if (tempRaw != null) {
                return String.format("%.1f", Double.parseDouble(tempRaw) / 1000);
            }
        } catch (Exception e) {
            return "N/A"; // 윈도우 등 다른 환경일 경우
        }
        return "0.0";
    }
}