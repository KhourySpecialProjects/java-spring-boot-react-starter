package com.example.app.middleware;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** Logs one line per request once its response is ready, e.g. "GET /api/v1/messages 200 36ms". */
@Slf4j
@Component
public class RequestLogger extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    long start = System.nanoTime();
    chain.doFilter(request, response);

    int status = response.getStatus();
    String path = request.getRequestURI();

    // no logs for healthchecks to reduce unnecessary noise
    if (path.equals("/api/v1/health") && status < 400) {
      return;
    }

    long durationMs = (System.nanoTime() - start) / 1_000_000;
    log.atLevel(levelFor(status))
        .log("{} {} {} {}ms", request.getMethod(), path, status, durationMs);
  }

  private static Level levelFor(int status) {
    if (status >= 500) {
      return Level.ERROR;
    }
    if (status >= 400) {
      return Level.WARN;
    }
    return Level.INFO;
  }
}
