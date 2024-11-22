package br.com.fiap.gsdevops.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "{veiculo.marca.notblank}")
    @Column(name = "MARCA", nullable = false)
    private String marca;

    @NotBlank(message = "{veiculo.modelo.notblank}")
    @Column(name = "MODELO", nullable = false)
    private String modelo;

    @NotNull(message = "{veiculo.ano.notnull}")
    @Column(name = "ANO_FABRICACAO", nullable = false)
    private Integer anoFabricacao;

    @NotNull(message = "{veiculo.consumo.notnull}")
    @Column(name = "CONSUMO_POR_KM", nullable = false)
    private Double consumoPorKm;

    @NotNull(message = "{veiculo.usuario.notnull}")
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @NotNull(message = "{veiculo.combustivel.notnull}")
    @ManyToOne
    @JoinColumn(name = "ID_COMBUSTIVEL", nullable = false)
    private Combustivel combustivel;
}
