import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginGrafico extends Application {

    @Override
    public void start(Stage stage) {

        // 🐒 MONO
        Pane monoPane = new Pane();
        monoPane.setPrefSize(200, 200);
        monoPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        // Orejas
        Circle orejaIzqExt = new Circle(50, 100, 30, Color.SADDLEBROWN);
        Circle orejaDerExt = new Circle(150, 100, 30, Color.SADDLEBROWN);

        Circle orejaIzqInt = new Circle(50, 100, 18, Color.BEIGE);
        Circle orejaDerInt = new Circle(150, 100, 18, Color.BEIGE);

        // Cabeza
        Circle cabeza = new Circle(100, 110, 65, Color.SADDLEBROWN);
        Circle cara = new Circle(100, 130, 45, Color.BEIGE);

        // 👀 OJOS
        Circle ojoIzq = new Circle(75, 110, 16);
        ojoIzq.setFill(Color.WHITE);
        ojoIzq.setStroke(Color.BLACK);
        ojoIzq.setStrokeWidth(1.2);

        Circle ojoDer = new Circle(125, 110, 16);
        ojoDer.setFill(Color.WHITE);
        ojoDer.setStroke(Color.BLACK);
        ojoDer.setStrokeWidth(1.2);

        // Pupilas
        Circle pupilaIzq = new Circle(75, 110, 7, Color.BLACK);
        Circle pupilaDer = new Circle(125, 110, 7, Color.BLACK);

        // Brillo
        Circle brilloIzq = new Circle(70, 105, 3, Color.WHITE);
        Circle brilloDer = new Circle(120, 105, 3, Color.WHITE);

        // Mejillas
        Circle mejillaIzq = new Circle(70, 140, 8, Color.PINK);
        Circle mejillaDer = new Circle(130, 140, 8, Color.PINK);

        // Nariz
        Circle nariz = new Circle(100, 130, 6, Color.BLACK);

        // Boca
        Arc boca = new Arc(100, 145, 20, 12, 180, 180);
        boca.setType(ArcType.OPEN);
        boca.setStroke(Color.BLACK);
        boca.setStrokeWidth(2);
        boca.setFill(null);

        monoPane.getChildren().addAll(
                orejaIzqExt, orejaDerExt,
                orejaIzqInt, orejaDerInt,
                cabeza, cara,
                ojoIzq, ojoDer,
                pupilaIzq, pupilaDer,
                brilloIzq, brilloDer,
                mejillaIzq, mejillaDer,
                nariz, boca
        );

        // 👀 Movimiento ojos
        monoPane.setOnMouseMoved(e -> {
            double x = e.getX();
            double y = e.getY();

            pupilaIzq.setCenterX(75 + (x - 100) * 0.03);
            pupilaIzq.setCenterY(110 + (y - 100) * 0.03);

            pupilaDer.setCenterX(125 + (x - 100) * 0.03);
            pupilaDer.setCenterY(110 + (y - 100) * 0.03);
        });

        // 🔥 CONTENEDOR CENTRADO REAL
        StackPane monoContainer = new StackPane(monoPane);
        monoContainer.setAlignment(Pos.CENTER);

        // 🧘 Título
        Label titulo = new Label("Bienvenido");
        titulo.setStyle("-fx-text-fill: #333; -fx-font-size: 32px; -fx-font-weight: bold;");

        // Inputs
        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Usuario");

        PasswordField txtContrasena = new PasswordField();
        txtContrasena.setPromptText("Contraseña");

        String estiloInput =
                "-fx-background-radius: 10;" +
                        "-fx-padding: 10;" +
                        "-fx-background-color: #f5f5f5;" +
                        "-fx-border-color: #ddd;" +
                        "-fx-border-radius: 10;";

        txtUsuario.setStyle(estiloInput);
        txtContrasena.setStyle(estiloInput);

        // Botón
        Button btnLogin = new Button("Entrar");
        btnLogin.setStyle(
                "-fx-background-color: #4a90e2;" +
                        "-fx-background-radius: 15;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 25;"
        );

        Label mensaje = new Label();

        // LOGIN
        btnLogin.setOnAction(e -> {
            String usuario = txtUsuario.getText();
            String contrasena = txtContrasena.getText();

            try {
                Connection con = conexion.conectar();

                if (con == null) {
                    mensaje.setText("Error de conexión ❌");
                    return;
                }

                String sql = "SELECT * FROM usuarios WHERE usuario=? AND contrasena=?";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, usuario);
                ps.setString(2, contrasena);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    Label exito = new Label("Inicio de sesión exitoso 🎉");
                    exito.setStyle("-fx-text-fill: #333; -fx-font-size: 26px; -fx-font-weight: bold;");

                    VBox nuevoRoot = new VBox(20, monoContainer, exito);
                    nuevoRoot.setAlignment(Pos.CENTER);
                    nuevoRoot.setStyle("-fx-background-color: white;");

                    stage.setScene(new Scene(nuevoRoot, 400, 500));

                } else {
                    mensaje.setText("Datos incorrectos ❌");
                    mensaje.setStyle("-fx-text-fill: red;");
                }

                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Layout
        VBox root = new VBox(20, monoContainer, titulo, txtUsuario, txtContrasena, btnLogin, mensaje);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: white;");

        Scene scene = new Scene(root, 400, 500);

        stage.setTitle("Login Relax 🐒");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}