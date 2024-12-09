package com.example.make_a_square_gui;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.*;

public class HelloController implements Initializable {
    PiecesModel piecesModel;

    //start definition
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button button9;
    @FXML
    private Button button10;
    @FXML
    private Button button11;
    @FXML
    private Button button12;
    @FXML
    private Button button13;
    @FXML
    private Button button14;
    @FXML
    private Button button15;
    @FXML
    private Button button16;

    @FXML
    private TextField textI;
    @FXML
    private TextField textJ;
    @FXML
    private TextField textL;
    @FXML
    private TextField textO;
    @FXML
    private TextField textS;
    @FXML
    private TextField textT;
    @FXML
    private TextField textZ;

    Button[][] array2DButton;
    ArrayList<Button> buttonArrayList;

    Map<Character,Integer> hashMap = new HashMap<>();

    //end definition

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        piecesModel = new PiecesModel();
        initButtons();
        //solveProblem();
    }
//    char[] arr = new char[]{'L','I','T','O','Z','J','S'};
    // void setHashMap()
    // {
    //     hashMap.put('L',Integer.parseInt(textL.getText()));
    //     hashMap.put('Z',Integer.parseInt(textZ.getText()));
    //     hashMap.put('I',Integer.parseInt(textI.getText()));
    //     hashMap.put('J',Integer.parseInt(textJ.getText()));
    //     hashMap.put('T',Integer.parseInt(textT.getText()));
    //     hashMap.put('S',Integer.parseInt(textS.getText()));
    //     hashMap.put('O',Integer.parseInt(textO.getText()));
    // }


    void setHashMap() {
        try {
            // Check and parse each input
            int l = Integer.parseInt(textL.getText());
            int z = Integer.parseInt(textZ.getText());
            int i = Integer.parseInt(textI.getText());
            int j = Integer.parseInt(textJ.getText());
            int t = Integer.parseInt(textT.getText());
            int s = Integer.parseInt(textS.getText());
            int o = Integer.parseInt(textO.getText());
    
            // Validate the inputs
            if (l < 0 || z < 0 || i < 0 || j < 0 || t < 0 || s < 0 || o < 0) {
                showAlert("Input Error", "All values must be positive integers.");
                return;
            }
            if (l > 4 || z > 4 || i > 4 || j > 4 || t > 4 || s > 4 || o > 4) {
                showAlert("Input Error", "All values must not be larger than 4.");
                return;
            }
    
            // If all checks pass, put values in the hashmap
            hashMap.put('L', l);
            hashMap.put('Z', z);
            hashMap.put('I', i);
            hashMap.put('J', j);
            hashMap.put('T', t);
            hashMap.put('S', s);
            hashMap.put('O', o);
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid integers only.");
        }
    }
    
    // Helper method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //initialize buttons and put them in 2D Grid
    void initButtons()
    {
        buttonArrayList = new ArrayList<>();
        buttonArrayList.add(button1);
        buttonArrayList.add(button2);
        buttonArrayList.add(button3);
        buttonArrayList.add(button4);
        buttonArrayList.add(button5);
        buttonArrayList.add(button6);
        buttonArrayList.add(button7);
        buttonArrayList.add(button8);
        buttonArrayList.add(button9);
        buttonArrayList.add(button10);
        buttonArrayList.add(button11);
        buttonArrayList.add(button12);
        buttonArrayList.add(button13);
        buttonArrayList.add(button14);
        buttonArrayList.add(button15);
        buttonArrayList.add(button16);
        array2DButton = new Button[4][4];
        for(int i=0;i<4;i++) for(int j=0;j<4;j++) array2DButton[i][j]=buttonArrayList.get(i*4+j);
    }

    List<int [][]> Boards;
    public void solveProblem()
    {
        setHashMap();
        HashMap<Integer, int[][]> sendPieces = new HashMap<>();
        int id=0;
        for (Map.Entry<Character, Integer> set : hashMap.entrySet()) {
            int cnt=set.getValue();
            while (cnt > 0)
            {
                sendPieces.put(id++,piecesModel.retrievePiece(set.getKey()));
                cnt--;
            }
        }
        try {

                Boards = MakeASquare.Square(sendPieces);



            if(Boards.isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message Here...");
                alert.setHeaderText("Make A Square");
                alert.setContentText("No Solution Founded");
                alert.showAndWait().ifPresent(rs -> {

                    if (rs == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                        clearboard();
                    }
                });
            }else
            {
                paintButton(Boards);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //painting
    void paintButton(List<int [][]> board)
    {
        ArrayList<String> arrColor = new ArrayList<>();
        arrColor.add("-fx-background-color: green;");
        arrColor.add("-fx-background-color: red;");
        arrColor.add("-fx-background-color: aqua;");
        arrColor.add("-fx-background-color: yellow;");
        arrColor.add("-fx-background-color: purple;");
        
        for(int k=0;k<Boards.size();k++){
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                array2DButton[i][j].setStyle(arrColor.get(Boards.get(k)[i][j]));
            }
        }
             }
    }

    public void clearboard(){

        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                array2DButton[i][j].setStyle("-fx-background-color: white;");
            }
        }
        textI.setText("0");
        textJ.setText("0");
        textL.setText("0");
        textO.setText("0");
        textS.setText("0");
        textT.setText("0");
        textZ.setText("0");
        
    }

}