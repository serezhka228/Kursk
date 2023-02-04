package com.example.mavishop;

import java.sql.*;


//Класс для взаимодействия с БД
public class ConnectDB {

    Connection dbConnect;

    //IP адресс сервера
    private final String host = "127.0.0.1";

    //Порт\
    private final String port ="3306";

    //Название схемы БД
    public static String nameDB = "kursk";

    //Имя пользователя в БД
    private  final String userDB = "root";

    //Пароль пользователя от БД
    private  final String passDB = "";

    //Метод подключения к базе
    public Connection getDbConnection() throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://" + host + ":"
                + port + "/" + nameDB;
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnect = DriverManager.getConnection(connectionString,
                userDB , passDB);
        return dbConnect;
    }

    //Метод Авторизации(проверка наличия логина и пароля в БД)
    public ResultSet getUser(String login, String password){
        ResultSet resSet = null;

        //Формирование запроса
        String select = "SELECT * FROM "+nameDB+".Employee WHERE login = ? and password = ?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, login);
            prSt.setString(2, password);
            resSet = prSt.executeQuery();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return resSet;
    }
    public void signUpUser(String fio, String age, String adress, String phone, String login, String password) {
        //SQL запрос
        String insert = "INSERT INTO employee(fio, age, adress, phone, login,password, idRole) VALUES ('"+fio+"','"+age+"','"+adress+"','"+phone+"','"+login+"','"+password+"','1')";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.executeUpdate(insert);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public ResultSet getProduct(String select){
        ResultSet resSet = null;

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return resSet;

    }






}
