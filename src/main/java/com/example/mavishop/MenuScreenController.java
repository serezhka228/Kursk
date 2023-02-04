package com.example.mavishop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuScreenController {

    ConnectDB dbHandler = new ConnectDB();

    @FXML
    private GridPane gridCat;

    @FXML
    private GridPane gridProduct;

    @FXML
    private TextField searchField;

    @FXML
    private Label fioLabel;


    private String nameDB = ConnectDB.nameDB;
    private List<Product> products = new ArrayList<>();
    int row = 1;

    @FXML
    void initialize() throws SQLException {
        fioLabel.setText(startScreenController.NameFooter);

        createProduct("SELECT name,price, discription, photo FROM "+nameDB+".product");
    }

    public void WC() throws SQLException, InterruptedException {
        clearProduct();
        products.clear();
       createProduct("SELECT name,price, discription, photo FROM "+nameDB+".Product where idCategory = 1;");

    }

    public void MC() throws SQLException, InterruptedException {
        clearProduct();
        products.clear();
        createProduct("SELECT name,price, discription, photo FROM "+nameDB+".Product where idCategory = 2;");

    }

    public void WF() throws SQLException, InterruptedException {
        clearProduct();
        products.clear();
        createProduct("SELECT name,price, discription, photo FROM "+nameDB+".Product where idCategory = 3;");
    }

    public void MF() throws SQLException, InterruptedException {
        clearProduct();
        products.clear();
        createProduct("SELECT name,price, discription, photo FROM "+nameDB+".Product where idCategory = 4;");

    }
    public void main() throws SQLException, InterruptedException {
        clearProduct();
        products.clear();
        createProduct("SELECT name,price, discription, photo FROM "+nameDB+".product");
    }


    public void createProduct(String select) throws SQLException {
        //Формирование продуктов
        products.addAll(getDataProduct(select));

        try {
            for (int i = 0; i < products.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("itemProduct.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemProductController itemProductController = fxmlLoader.getController();
                itemProductController.setData(products.get(i));


                gridProduct.add(anchorPane, 0, row++); //(child, column, row)

                //set item drid width
                gridProduct.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridProduct.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridProduct.setMaxWidth(Region.USE_PREF_SIZE);

                //set item drid height
                gridProduct.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridProduct.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridProduct.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(5));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void clearProduct(){
        int i = 1;
        while (i <= products.size()){
                gridProduct.getChildren().remove(0);
                    i++;

        }
    }
    public void search(KeyEvent even) throws SQLException, InterruptedException {
        if (even.getCode() == KeyCode.ENTER){
            clearProduct();
            products.clear();
            createProduct("SELECT name,price, discription, photo  FROM "+nameDB+".product WHERE name LIKE '%"+searchField.getText()+"%';");
        }
    }


    public List<Product> getDataProduct(String select) throws SQLException {
        List<Product> productos = new ArrayList<>();
        Product product;
        ResultSet result = dbHandler.getProduct(select);

        while (result.next()) {
            product = new Product();
            product.setName(result.getString(1));
            product.setPrice(result.getInt(2));
            product.setDiscription(result.getString(3));
            product.setPhoto(result.getString(4));
            productos.add(product);
        }
        return productos;
    }

    public void onclick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("startScreen.fxml"));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
