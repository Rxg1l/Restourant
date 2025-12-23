// package com.example.restourant.controller;

// import java.util.Map;

// import org.springframework.beans.propertyeditors.CustomNumberEditor;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.GetMapping;

// import com.example.restourant.dto.CustomResponse;

// @RestController
// @RequestMapping("/api/dashboard")
// @PreAuthorize("hasRole('ADMIN')")
// public class DashboardController {

// @GetMapping("/stats")
// public ResponseEntity<?> getDashboardStats() {
// Map<String, Object> stats = new HashMap<>();

// stats.put("totalUsers", userRepository.count());
// stats.put("activeUsers", userRepository.countByAktif(true));
// stats.put("todayOrders", orderRepository.countTodayOrders());
// stats.put("monthlyRevenue", orderRepository.getMonthlyRevenue());

// return ResponseEntity.ok(CustomResponse.success("Dashboard stats", stats));
// }

// }