package br.com.fiap.gsdevops.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "VEICULO")
@NoArgsConstructor
@AllArgsConstructor
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VEICULO")
    @SequenceGenerator(name = "SEQ_VEICULO", sequenceName = "SEQ_VEICULO", allocationSize = 1)
    private Integer idVeiculo;

    @Column(name = "MARCA", nullable = false)
    private String marca;
 
    @Column(name = "MODELO", nullable = false)
    private String modelo;

    @Column(name = "ANO_FABRICACAO", nullable = false)
    private Integer anoFabricacao;

    @Column(name = "CONSUMO_POR_KM", nullable = false)
    private Double consumoPorKm;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_COMBUSTIVEL", nullable = false)
    private Combustivel combustivel;
}
