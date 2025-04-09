package com.example.clone1.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.clone1.DAO.UserDAO;
import com.example.clone1.Model.Users;

@Service
public class UserRegisterDTO {
    @Autowired
    private UserDAO userDAO;

    public void saveUser(Users user) {
        String id = userDAO.getLastUserID();
        int idtemp = Integer.parseInt(id);
        idtemp++;
        user.setId(String.valueOf(idtemp));
        int status = userDAO.createUser(user);
        if (status != 1) {
            System.out.println("TTTTTTTTT_TTTTTTTTT\n\n\n");
        } else {
            System.out.println("Tạo thành công rồi đấy \n\n\n\n\n\n\n\n <<<<<<<<<333>>>>>>>");
        }
    }

    public boolean checkEmail(String email) {
        return userDAO.checkEmail(email);
    }

    public boolean checkPhone(String phone) {
        return userDAO.checkPhone(phone);
    }
}
