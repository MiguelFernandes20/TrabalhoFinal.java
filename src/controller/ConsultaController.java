package controller;

import exception.AppException;
import model.Consulta;
import util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaController {
    public List<Consulta> listarConsultas() throws AppException {
        try (Connection connection = DBUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM consultas")) {

            List<Consulta> consultas = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                LocalDate data = resultSet.getDate("data").toLocalDate();
                LocalTime hora = resultSet.getTime("hora").toLocalTime();
                String paciente = resultSet.getString("paciente");

                Consulta consulta = new Consulta(id, data, hora, paciente);
                consultas.add(consulta);
            }

            return consultas;
        } catch (SQLException e) {
            throw new AppException("Erro ao listar consultas: " + e.getMessage());
        }
    }


    public void agendarConsulta(Consulta consulta) throws AppException {
        String paciente = consulta.getPaciente();

        if (!isNomeValido(paciente)) {
            throw new AppException("O nome do paciente deve começar com uma letra maiúscula");
        }

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO consultas (data, hora, paciente) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setDate(1, Date.valueOf(consulta.getData()));
            preparedStatement.setTime(2, Time.valueOf(consulta.getHora()));
            preparedStatement.setString(3, paciente);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                consulta.setId(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            throw new AppException("Erro ao agendar consulta: " + e.getMessage());
        }
    }

    private boolean isNomeValido(String nome) {
        String[] partes = nome.split(" ");

        for (String parte : partes) {
            if (parte.isEmpty() || !Character.isUpperCase(parte.charAt(0))) {
                return false;
            }
        }

        return true;
    }


// Outros métodos de controle (edição, exclusão, etc.)
}
