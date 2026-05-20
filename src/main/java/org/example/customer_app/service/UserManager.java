package org.example.customer_app.service;

import org.example.customer_app.model.Customer;
import org.example.customer_app.model.RegisteredUser;
import org.jspecify.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    // Dattha sēv vana file ekē nama
    public static final String USERS_FILE = "customers.txt"; // පින්තූරයේ ඇති නමම ලබා දෙන්න

    // 1. Pariśīlakayā liyāpadin̄ci kirīma (File ekata livīma)
    public void registerUser(RegisteredUser user) {
        // 'true' yannen adahas karannē parana dattha makannē nätuva aluth dattha yatin ekathu karanavā (Append) yannayi
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {

            // Dattha komā (,) magin ven kara pēḷiyak lesa liyanavā
            String userData = user.getId() + "," +
                    user.getName() + "," +
                    user.getEmail() + "," +
                    user.getPassword() + "," +
                    user.getPhoneNumber() + "," +
                    user.getAddress();

            writer.write(userData);
            writer.newLine(); // Aluth pēḷiyakata yanavā

            System.out.println("User registered successfully to " + USERS_FILE);

        } catch (IOException e) {
            System.err.println("Error saving user data: " + e.getMessage());
        }
    }

    // 2. Siyalu pariśīlakayangē liyistuva labā gänīma (File eken kiyavīma)
    public List<RegisteredUser> getAllUsers() {
        List<RegisteredUser> users = new ArrayList<>();

        // File eka kiyavanna BufferedReader pēḷiya
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                // Pēḷiyē dattha 6ma thiyenavada balanavā
                if (data.length >= 6) {
                    users.add(new RegisteredUser(data[0], data[1], data[2], data[3], data[4], data[5]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading users: " + e.getMessage());
        }
        return users;
    }
    public RegisteredUser login(String email, String password) {
        List<RegisteredUser> allUsers = getAllUsers();
        for (RegisteredUser user : allUsers) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user; // සාර්ථක නම් යූසර්ව ලබා දෙනවා
            }
        }
        return null; // වැරදි නම් null ලබා දෙනවා
    }
    // 1. පාරිභෝගිකයෙකු මකා දැමීම
    public void deleteUser(String id) {
        List<RegisteredUser> users = getAllUsers();
        // අදාළ ID එක නැති අනිත් සියලුම යූසර්ලාව තබා ගන්නවා
        users.removeIf(user -> user.getId().equals(id));
        saveAllUsers(users);
    }

    // 2. පාරිභෝගිකයෙකුගේ විස්තර වෙනස් කිරීම
    public void updateUser(RegisteredUser updatedUser) {
        List<RegisteredUser> users = getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(updatedUser.getId())) {
                users.set(i, updatedUser); // පරණ දත්ත අලුත් ඒවායින් මාරු කරනවා
                break;
            }
        }
        saveAllUsers(users);
    }

    // 3. ලැයිස්තුවම ආපහු ෆයිල් එකට ලිවීමට (Helper method)
    private void saveAllUsers(List<RegisteredUser> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, false))) { // false යනු overwrite කිරීමයි
            for (RegisteredUser user : users) {
                String userData = user.getId() + "," + user.getName() + "," + user.getEmail() + "," +
                        user.getPassword() + "," + user.getPhoneNumber() + "," + user.getAddress();
                writer.write(userData);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }

    public void addCustomer(String id, String name, String email, String phone, String address) {
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = List.of();
        return customers;
    }
}