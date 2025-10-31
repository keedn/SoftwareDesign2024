module com.example.quiztipcalc {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.quiztipcalc to javafx.fxml;
    exports com.example.quiztipcalc;
}