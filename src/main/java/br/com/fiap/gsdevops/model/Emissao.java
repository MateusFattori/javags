package br.com.fiap.gsdevops.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "EMISSAO")
@NoArgsConstructor
@AllArgsConstructor
public class Emissao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EMISSAO")
    @SequenceGenerator(name = "SEQ_EMISSAO", sequenceName = "SEQ_EMISSAO", allocationSize = 1)
    private Integer idEmissao;

    @NotNull(message = "{emissao.viagem.notnull}")
    @OneToOne
    @JoinColumn(name = "ID_VIAGEM", nullable = false)
    private Viagem viagem;

    @NotNull(message = "{emissao.litros.notnull}")
    @Column(name = "LITROS_CONSUMIDOS", nullable = false)
    private Double litrosConsumidos;

    @NotNull(message = "{emissao.co2.notnull}")
    @Column(name = "EMISSAO_CO2_KG", nullable = false)
    private Double emissaoCo2Kg;
}
