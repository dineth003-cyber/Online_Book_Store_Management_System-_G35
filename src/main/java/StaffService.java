package org.example.customer_app.service;

import org.example.customer_app.model.Staff;
import java.io.*;
import java.util.*;

public class StaffService {

    private static final String FILE_NAME = "staff.txt";

    // ✅ CREATE
    public void addStaff(String id, String name, String role, double salary) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(id + "," + name + "," + role + "," + salary + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ✅ READ ALL (String List එකක් විදිහට පරණ ක්‍රමය)
    public List<String> getAllStaff() {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            // ෆයිල් එක තවම හැදිලා නැත්නම් හිස් ලිස්ට් එකක් යවන්න
            return list;
        }
        return list;
    }

    // ✅ FIXED: 1. ID එකෙන් Staff Object එකක් Text File එකෙන් කියවලා හොයාගැනීම
    public Staff findById(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(id + ",")) {
                    String[] parts = line.split(",");
                    // staff.txt එකෙන් විස්තර අරන් Staff Object එකක් හදනවා
                    return new Staff(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // සේවකයෙක් හමු නොවුණොත්
    }

    // ✅ FIXED: 2. Staff Object එකක් හරහා Text File එක ඇතුළේ දත්ත Update කිරීම
    public void updateStaff(Staff updatedStaff) {
        List<String> updatedList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(updatedStaff.getId() + ",")) {
                    // අලුත් විස්තර ටික ලයින් එකට ආදේශ කරනවා
                    line = updatedStaff.getId() + "," + updatedStaff.getName() + "," + updatedStaff.getRole() + "," + updatedStaff.getSalary();
                }
                updatedList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ආපහු text file එකට ලියනවා
        try (FileWriter fw = new FileWriter(FILE_NAME)) {
            for (String s : updatedList) {
                fw.write(s + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ✅ FIXED: 3. ID එකෙන් Text File එකේ ඉන්න Staff කෙනෙක්ව සම්පූර්ණයෙන්ම අයින් කිරීම (Duplicate අයින් කළා)
    public void deleteStaff(String id) {
        List<String> updatedList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith(id + ",")) {
                    updatedList.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter fw = new FileWriter(FILE_NAME)) {
            for (String s : updatedList) {
                fw.write(s + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}