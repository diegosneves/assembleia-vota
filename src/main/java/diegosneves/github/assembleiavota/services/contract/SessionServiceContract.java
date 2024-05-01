package diegosneves.github.assembleiavota.services.contract;

import diegosneves.github.assembleiavota.exceptions.IllegalSessionArgumentException;
import diegosneves.github.assembleiavota.exceptions.InvalidIdException;
import diegosneves.github.assembleiavota.exceptions.SessionCreateFailureException;
import diegosneves.github.assembleiavota.exceptions.SessionNotFound;
import diegosneves.github.assembleiavota.models.SessionEntity;
import diegosneves.github.assembleiavota.requests.StartSessionRequest;
import diegosneves.github.assembleiavota.responses.SessionCreatedResponse;

/**
 * {@link SessionServiceContract} é uma interface que define o contrato para os serviços de sessão.
 * Contêm a definição do método para iniciar uma nova sessão.
 *
 * @author diegoneves
 */
public interface SessionServiceContract {

    /**
     * Esse método é responsável por iniciar uma nova sessão baseada no objeto de solicitação fornecido.
     *
     * @param request objeto do tipo {@link StartSessionRequest} que contém as informações necessárias para iniciar uma nova sessão.
     * @return retorna um objeto do tipo {@link SessionCreatedResponse} que indica a criação bem-sucedida da sessão e apresenta os detalhes da sessão recém-criada.
     * @throws SessionCreateFailureException é lançada quando ocorre algum erro durante a criação da sessão.
     */
    SessionCreatedResponse startSession(StartSessionRequest request) throws SessionCreateFailureException;


    /**
     * Este método é utilizado para recuperar uma sessão a partir de um ID de sessão fornecido.
     * Primeiramente, o método realiza a validação do ID da sessão e depois tenta recuperar a sessão do repositório.
     * Caso a sessão não seja encontrada, um erro é registrado no log e uma exceção é lançada.
     *
     * @param sessionId ID da sessão a ser recuperada
     * @return Retorna uma instância de {@link SessionEntity} que corresponde ao ID da sessão informado
     * @throws SessionNotFound    Exceção lançada caso a sessão com o ID fornecido não seja encontrada
     * @throws InvalidIdException Exceção lançada caso o ID da sessão fornecido não esteja em um formato válido
     */
    SessionEntity getSession(String sessionId) throws SessionNotFound, InvalidIdException;


    /**
     * Atualiza uma sessão existente na base de dados.
     * <p>
     * Este método primeiro valida a sessão fornecida utilizando o método `sessionValidate()`.
     * Em seguida, verifica se a data/hora atual é anterior à data/hora de término da sessão.
     * Se for, a sessão é considerada aberta e o estado da sessão é ajustado de acordo.
     * Depois, a sessão atualizada é salva no repositório e a entidade atualizada é retornada.
     *
     * @param session A entidade da sessão que deve ser atualizada.
     * @return A entidade da sessão atualizada.
     * @throws IllegalSessionArgumentException se a sessão fornecida é inválida.
     */
    SessionEntity updateSession(SessionEntity session) throws IllegalSessionArgumentException;

}
