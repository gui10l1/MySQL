package mysql;

import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class MySQL {

    static Scanner sc = new Scanner(System.in);
    static int linhas;

    public static void main(String[] args) {
        //Variáveis
        String email;
        String senha;
        String op;
        String keep;

        int id;
        boolean running = true;

        while (running) {
            System.out.println("");
            System.out.println("* * * * * * * *  *");
            System.out.println("* MENU PRINCIPAL *");
            System.out.println("* * * * * * * *  *");
            System.out.println("[1] Adicionar novo usuário");
            System.out.println("[2] Listar todos os usuários");
            System.out.println("[3] Procurar usuário por email");
            System.out.println("[4] Remover usuário por email");
            System.out.println("[5] Finalizar o programa");
            System.out.println("Selecione uma opção: ");
            op = sc.nextLine();

            switch (op) {
                case "1":
                    System.out.println("");
                    System.out.println("* * * * * * * * * * * * * * * * * * * * *");
                    System.out.println("* MENU DE CADASTRO PARA NOVOS USUÁRIOS  *");
                    System.out.println("* * * * * * * * * * * * * * * * * * * * *");
                    System.out.print("Insira o seu novo email: ");
                    email = sc.nextLine();
                    System.out.print("Insira sua nova senha: ");
                    senha = sc.nextLine();

                    if (pesquisarUsuario(email)) {
                        inserirUsuario(email, senha);
                    } else {
                        System.out.println("Este email está sendo usado!");
                    }

                    break;

                case "2":
                    System.out.println("");
                    System.out.println("* * * * * * * * * * * * *");
                    System.out.println("* LISTAGEM DE USUÁRIOS  *");
                    System.out.println("* * * * * * * * * * * * *");
                    listarUsuario();
                    break;

                case "3":
                    System.out.println("");
                    System.out.println("* * * * * * * * * * * * * * * *");
                    System.out.println("* PROCURAR USUÁRIO POR EMAIL  *");
                    System.out.println("* * * * * * * * * * * * * * * *");
                    System.out.print("Insira o email do usuário que deseja procurar: ");
                    email = sc.nextLine();
                    pegarUsuario(email);
                    break;

                case "4":
                    System.out.println("");
                    System.out.println("* * * * * * * * * * * * * * *");
                    System.out.println("* REMOVER USUÁRIO POR EMAIL *");
                    System.out.println("* * * * * * * * * * * * * * *");
                    System.out.print("Insira o email do usuário que deseja remover: ");
                    email = sc.nextLine();
                    removerUsuario(email);
                    break;

                case "5":
                    running = false;

                    break;
            }

            System.out.println("Aperte enter para continuar");
            keep = sc.nextLine();
        }

    }

    static void conectar() {
        try {
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            System.out.println("Conexão efetuada!");

        } catch (Exception e) {

            System.out.println("Conexão não efetuada!");
        }
    }

    static void inserirUsuario(String email, String senha) {
        try {
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            PreparedStatement stmnt = conn.prepareStatement("insert into cadastro_de_usuarios.tb_usuarios (des_email, enc_senha) values (?, ?);");

            stmnt.setString(1, email);
            stmnt.setString(2, senha);

            linhas = stmnt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Usuário cadastrado com sucesso!");
            } else {
                System.out.println("Usuário não foi cadastrado, algum erro aconteceu... Tente novamente!");
            }

        } catch (Exception e) {

        }
    }

    static void pegarUsuario(String email) {
        try {
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            PreparedStatement stmnt = conn.prepareStatement("select * from cadastro_de_usuarios.tb_usuarios where des_email = ?");

            stmnt.setString(1, email);

            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                String senha = rs.getString("enc_senha");

                System.out.println("Usuário: " + id);
                System.out.println("Email: " + email);
                System.out.println("Senha: " + senha);
            }

        } catch (Exception e) {

        }

    }

    static void removerUsuario(String email) {
        try {
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            PreparedStatement stmnt = conn.prepareStatement("delete from cadastro_de_usuarios.tb_usuarios where des_email = ?");

            stmnt.setString(1, email);

            linhas = stmnt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Usuário removido com sucesso!");
            } else {
                System.out.println("Usuário não foi removido, algum erro aconteceu... Tente novamente!");
            }

        } catch (Exception e) {

        }

    }

    static void listarUsuario() {
        try {
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            PreparedStatement stmnt = conn.prepareStatement("select * from cadastro_de_usuarios.tb_usuarios");

            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                String email = rs.getString("des_email");
                String senha = rs.getString("enc_senha");

                System.out.println("Usuário: " + id);
                System.out.println("Email: " + email);
                System.out.println("Senha: " + senha);
            }

        } catch (Exception e) {

        }
    }

    static boolean pesquisarUsuario(String email) {
        try {
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            PreparedStatement stmnt = conn.prepareStatement("select * from cadastro_de_usuarios.tb_usuarios where des_email = ?");

            stmnt.setString(1, email);

            linhas = stmnt.executeUpdate();

        } catch (Exception e) {

        }

        if (linhas > 0) {
            return false;
        }

        return true;
    }

}
