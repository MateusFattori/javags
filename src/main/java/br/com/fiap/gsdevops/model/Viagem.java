package br.com.fiap.gsdevops.model;

import jakarta.persistence.*;
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
 
    @Column(name = "DATA_VIAGEM", nullable = false)
    private LocalDate dataViagem; 

    @Column(name = "LOCAL_ORIGEM", nullable = false)
    private String localOrigem;

    @Column(name = "LOCAL_DESTINO", nullable = false)
    private String localDestino;

    @Column(name = "DISTANCIA_KM", nullable = false)
    private Double distanciaKm;

    @ManyToOne
    @JoinColumn(name = "ID_VEICULO", nullable = false)
    private Veiculo veiculo;
}
