package com.kakaologin.demo.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

public interface JwtService {
     String extractJwtFromCookie(HttpServletRequest request);
}
