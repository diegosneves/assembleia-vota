package diegosneves.github.assembleiavota.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A enumeração que representa as possíveis opções de voto.
 * <p>
 * Esta enumeração oferece duas opções para emissão de votos: SIM e NÃO.
 * Cada opção de voto está associada a um valor de propriedade JSON: “Sim” e “Não”.
 *
 * @autor diegoneves
 */
public enum VoteOption {
    @JsonProperty(value = "Sim")
    YES,
    @JsonProperty(value = "Não")
    NO;
}
