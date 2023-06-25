package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consulta {
    private int id;
    private LocalDate data;
    private LocalTime hora;
    private String paciente;

    public Consulta(int id, LocalDate data, LocalTime hora, String paciente) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.paciente = paciente;
    }

    // Getters e setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }
}
