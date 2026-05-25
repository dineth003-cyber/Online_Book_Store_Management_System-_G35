package org.example.customer_app.controller;

import org.example.customer_app.service.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    private StaffService staffService = new StaffService();

    // Dashboard
    @GetMapping("/admin")
    public String dashboard() {
        return "admin";
    }

    // Form
    @GetMapping("/staff-form")
    public String form() {
        return "staff-form";
    }

    // CREATE
    @PostMapping("/add-staff")
    public String addStaff(@RequestParam String id,
                           @RequestParam String name,
                           @RequestParam String role,
                           @RequestParam double salary) {

        staffService.addStaff(id, name, role, salary);
        return "redirect:/staff-list";
    }

    // READ
    @GetMapping("/staff-list")
    public String list(Model model) {
        model.addAttribute("staffList", staffService.getAllStaff());
        return "staff-list";
    }

    // DELETE
    @GetMapping("/delete-staff")
    public String delete(@RequestParam String id) {
        staffService.deleteStaff(id);
        return "redirect:/staff-list";
    }
    // 👉 1. Staff කෙනෙක්ව Delete කිරීම
    @GetMapping("/delete-staff/{id}")
    public String deleteStaff(@PathVariable String id) {
        // staffService ලෙස නිවැරදි නම පාවිච්චි කළා
        staffService.deleteStaff(id);
        return "redirect:/staff-list";
    }

    // 👉 2. Staff කෙනෙක්ව Edit කරන Form එක පෙන්වීම
    @GetMapping("/edit-staff/{id}")
    public String editStaffForm(@PathVariable String id, Model model) {
        org.example.customer_app.model.Staff staff = staffService.findById(id);
        model.addAttribute("staff", staff);
        return "edit-staff-form";
    }

    // 👉 3. Edit කරපු විස්තර සේව් කිරීම (Update)
    @PostMapping("/update-staff")
    public String updateStaff(@ModelAttribute org.example.customer_app.model.Staff staff) {
        staffService.updateStaff(staff);
        return "redirect:/staff-list";
    }
}