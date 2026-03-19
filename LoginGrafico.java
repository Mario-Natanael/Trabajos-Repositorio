import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.animation.FadeTransition;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginGrafico extends Application {

    @Override
    public void start(Stage stage) {

        Label titulo = new Label("Iniciar Sesión");

        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Usuario");

        PasswordField txtContrasena = new PasswordField();
        txtContrasena.setPromptText("Contraseña");

        Button btnLogin = new Button("Ingresar");
        Label mensaje = new Label();

        // Evento botón
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
                    mensaje.setText("✔ Bienvenido 😎");
                    mensaje.setStyle(
                            "-fx-text-fill: #00ff88;" +
                                    "-fx-font-size: 18px;" +
                                    "-fx-font-weight: bold;"
                    );
                } else {
                    mensaje.setText("❌ Datos incorrectos");
                    mensaje.setStyle(
                            "-fx-text-fill: #ff4d4d;" +
                                    "-fx-font-size: 14px;"
                    );
                }

                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        root.getChildren().addAll(titulo, txtUsuario, txtContrasena, btnLogin, mensaje);

        // 🎨 ESTILOS PRO
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #141e30, #243b55);");

        titulo.setStyle("-fx-text-fill: white; -fx-font-size: 30px; -fx-font-weight: bold;");

        txtUsuario.setStyle(
                "-fx-background-radius: 10;" +
                        "-fx-padding: 10;" +
                        "-fx-background-color: #ffffff;"
        );

        txtContrasena.setStyle(
                "-fx-background-radius: 10;" +
                        "-fx-padding: 10;" +
                        "-fx-background-color: #ffffff;"
        );

        btnLogin.setStyle(
                "-fx-background-color: #00c6ff;" +
                        "-fx-background-radius: 20;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 20;"
        );

        mensaje.setStyle("-fx-text-fill: white;");

        // Hover effect 🔥
        btnLogin.setOnMouseEntered(e -> btnLogin.setStyle(
                "-fx-background-color: #0072ff;" +
                        "-fx-background-radius: 20;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 20;"
        ));

        btnLogin.setOnMouseExited(e -> btnLogin.setStyle(
                "-fx-background-color: #00c6ff;" +
                        "-fx-background-radius: 20;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 20;"
        ));

        Scene scene = new Scene(root, 350, 300);

        stage.setTitle("Login App 💻");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}