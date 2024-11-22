package br.com.fiap.gsdevops.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@Entity
@Table(name = "COMBUSTIVEL")
@NoArgsConstructor
@AllArgsConstructor
public class Combustivel {

    private static final List<Double> FATORES_VALIDOS = Arrays.asList(2.31, 2.68, 1.86, 2.00, 0.0);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMBUSTIVEL")
    @SequenceGenerator(name = "SEQ_COMBUSTIVEL", sequenceName = "SEQ_COMBUSTIVEL", allocationSize = 1)
    private Integer idCombustivel;

    @NotBlank(message = "{combustivel.tipo.notblank}")
    @Column(name = "TIPO_COMBUSTIVEL", nullable = false)
    private String tipoCombustivel;

    @NotNull(message = "{combustivel.fator.notnull}")
    @Column(name = "FATOR_EMISSAO", nullable = false)
    private Double fatorEmissao;

    @AssertTrue(message = "{combustivel.fator.invalido}")
    public boolean isFatorEmissaoValido() {
        return FATORES_VALIDOS.contains(fatorEmissao);
    }
}
