package br.com.fiap.gsdevops.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "VIAGEM")
@NoArgsConstructor
@AllArgsConstructor
public class Viagem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VIAGEM")
    @SequenceGenerator(name = "SEQ_VIAGEM", sequenceName = "SEQ_VIAGEM", allocationSize = 1)
    private Integer idViagem;

    @NotNull(message = "{viagem.dataViagem.notnull}")
    @Column(name = "DATA_VIAGEM", nullable = false)
    private LocalDate dataViagem;

    @NotBlank(message = "{viagem.origem.notblank}")
    @Column(name = "LOCAL_ORIGEM", nullable = false)
    private String localOrigem;

    @NotBlank(message = "{viagem.destino.notblank}")
    @Column(name = "LOCAL_DESTINO", nullable = false)
    private String localDestino;

    @NotNull(message = "{viagem.distancia.notnull}")
    @Positive(message = "{viagem.distancia.positive}")
    @Column(name = "DISTANCIA_KM", nullable = false)
    private Double distanciaKm;

    @NotNull(message = "{viagem.veiculo.notnull}")
    @ManyToOne
    @JoinColumn(name = "ID_VEICULO", nullable = false)
    private Veiculo veiculo;
}
