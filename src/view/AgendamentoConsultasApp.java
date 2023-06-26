package view;

import controller.ConsultaController;
import exception.AppException;
import model.Consulta;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Collections;
import java.util.List;

public class AgendamentoConsultasApp {
    private final ConsultaController consultaController;

    private JFrame frame;
    private JTextField txtData;
    private JTextField txtHora;
    private JTextField txtPaciente;
    private JTextArea txtAreaConsultas;

    public AgendamentoConsultasApp() {
        consultaController = new ConsultaController();
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblData = new JLabel("Data:");
        lblData.setBounds(10, 11, 46, 14);
        frame.getContentPane().add(lblData);

        txtData = new JTextField();
        txtData.setBounds(66, 8, 86, 20);
        frame.getContentPane().add(txtData);
        txtData.setColumns(10);

        JLabel lblHora = new JLabel("Hora:");
        lblHora.setBounds(10, 36, 46, 14);
        frame.getContentPane().add(lblHora);

        txtHora = new JTextField();
        txtHora.setBounds(66, 33, 86, 20);
        frame.getContentPane().add(txtHora);
        txtHora.setColumns(10);

        JLabel lblPaciente = new JLabel("Paciente:");
        lblPaciente.setBounds(10, 61, 57, 14);
        frame.getContentPane().add(lblPaciente);

        txtPaciente = new JTextField();
        txtPaciente.setBounds(66, 58, 134, 20);
        frame.getContentPane().add(txtPaciente);
        txtPaciente.setColumns(10);

        JButton btnAgendar = new JButton("Agendar");
        btnAgendar.addActionListener(e -> agendarConsulta());
        btnAgendar.setBounds(10, 86, 89, 23);
        frame.getContentPane().add(btnAgendar);

        txtAreaConsultas = new JTextArea();
        txtAreaConsultas.setEditable(false);
        txtAreaConsultas.setBounds(210, 50, 370, 240);
        frame.getContentPane().add(txtAreaConsultas);

        JLabel lblConsultas = new JLabel("Consultas:");
        lblConsultas.setBounds(210, 20, 214, 14);
        frame.getContentPane().add(lblConsultas);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> listarConsultas());
        btnAtualizar.setBounds(10, 262, 89, 23);
        frame.getContentPane().add(btnAtualizar);

        JButton btnEncerrar = new JButton("Encerrar");
        btnEncerrar.addActionListener(e -> encerrar());
        btnEncerrar.setBounds(110, 262, 89, 23);
        frame.getContentPane().add(btnEncerrar);
    }

       public void show() {
        frame.setVisible(true);
    }

    private void agendarConsulta() {
        String data = txtData.getText();
        String hora = txtHora.getText();
        String paciente = txtPaciente.getText();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(data, formatter);
            LocalTime localTime = LocalTime.parse(hora);

            Consulta consulta = new Consulta(0, localDate, localTime, paciente);
            consultaController.agendarConsulta(consulta);

            JOptionPane.showMessageDialog(frame, "Consulta agendada com sucesso!");

            txtData.setText("");
            txtHora.setText("");
            txtPaciente.setText("");
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao agendar consulta: formato de data ou hora inválido!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao agendar consulta: " + ex.getMessage());
        }
    }


    private void listarConsultas() {
        try {
            List<Consulta> consultas = consultaController.listarConsultas();

            StringBuilder sb = new StringBuilder();
            for (Consulta consulta : consultas) {
                DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
                sb.append("ID: ").append(consulta.getId())
                        .append(", Data: ").append(consulta.getData().format(formatoBrasileiro))
                        .append(", Hora: ").append(consulta.getHora())
                        .append(", Paciente: ").append(consulta.getPaciente())
                        .append("\n");
            }

            txtAreaConsultas.setText(sb.toString());
        } catch (AppException ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao listar consultas: " + ex.getMessage());
        }
    }

    private void encerrar() {
        frame.dispose();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AgendamentoConsultasApp window = new AgendamentoConsultasApp();
                window.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
