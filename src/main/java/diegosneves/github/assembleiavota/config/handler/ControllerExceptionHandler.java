package diegosneves.github.assembleiavota.config.handler;

import diegosneves.github.assembleiavota.dto.ExceptionDTO;
import diegosneves.github.assembleiavota.exceptions.ConstructorDefaultUndefinedException;
import diegosneves.github.assembleiavota.exceptions.IllegalSessionArgumentException;
import diegosneves.github.assembleiavota.exceptions.InvalidIdException;
import diegosneves.github.assembleiavota.exceptions.InvalidTopicIntegerException;
import diegosneves.github.assembleiavota.exceptions.InvalidTopicStringAttributeException;
import diegosneves.github.assembleiavota.exceptions.MapperFailureException;
import diegosneves.github.assembleiavota.exceptions.NoVotesFoundForTopicException;
import diegosneves.github.assembleiavota.exceptions.SessionCreateFailureException;
import diegosneves.github.assembleiavota.exceptions.SessionNotFound;
import diegosneves.github.assembleiavota.exceptions.TopicIdNotFoundException;
import diegosneves.github.assembleiavota.exceptions.UuidUtilsException;
import diegosneves.github.assembleiavota.exceptions.ValidationUserCpfException;
import diegosneves.github.assembleiavota.exceptions.VoteDuplicateException;
import diegosneves.github.assembleiavota.exceptions.VoteRequestValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * A classe {@link ControllerExceptionHandler} é um manipulador de exceções global para controladores.
 * Ela lida com as exceções lançadas durante o processamento de solicitações e gera respostas de erro apropriadas.
 * A classe é anotada com {@link RestControllerAdvice} para aplicar o tratamento de exceção globalmente
 * a todas as classes de controlador.
 *
 * @author diegosneves
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * Manipula exceções gerais e retorna uma resposta de erro apropriada.
     *
     * @param exception A exceção que ocorreu.
     * @return Uma {@link ResponseEntity} contendo um {@link ExceptionDTO} com a mensagem da exceção e um código de status HTTP
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> generalError(Exception exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
    }

    /**
     * Lida com as exceções {@link HttpMessageNotReadableException} relacionadas à deserialização mal sucedida do JSON de entrada e retorna uma resposta de erro adequada.
     *
     * @param exception A exceção que ocorreu.
     * @return Uma {@link ResponseEntity} contendo um {@link ExceptionDTO} com a mensagem da exceção e um código de status HTTP.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }

    /**
     * Manipulador de exceção para tratar erros relacionados ao construtor padrão não definido.
     *
     * @param exception Uma instância da exceção {@link ConstructorDefaultUndefinedException} que foi lançada.
     * @return Um objeto ResponseEntity que contém a descrição da exceção e o código de estado HTTP relacionado.
     */
    @ExceptionHandler(ConstructorDefaultUndefinedException.class)
    public ResponseEntity<ExceptionDTO> mapperRelatedFaileures(ConstructorDefaultUndefinedException exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), ConstructorDefaultUndefinedException.ERROR.getStatusCodeValue());
        return ResponseEntity.status(ConstructorDefaultUndefinedException.ERROR.getHttpStatusCode()).body(dto);
    }

    /**
     * Trata a exceção {@link MapperFailureException} e retorna uma resposta de erro apropriada.
     *
     * @param exception A exceção que ocorreu.
     * @return Um {@link ResponseEntity} contendo um {@link ExceptionDTO} com a mensagem da exceção e um código de status HTTP.
     */
    @ExceptionHandler(MapperFailureException.class)
    public ResponseEntity<ExceptionDTO> mapperRelatedFaileures(MapperFailureException exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), MapperFailureException.ERROR.getStatusCodeValue());
        return ResponseEntity.status(MapperFailureException.ERROR.getHttpStatusCode()).body(dto);
    }

    /**
     * Lida com a exceção {@link InvalidTopicStringAttributeException} e retorna uma resposta de erro apropriada.
     *
     * @param exception A exceção que ocorreu.
     * @return Uma {@link ResponseEntity} contendo um {@link ExceptionDTO} com a mensagem da exceção e um código de status HTTP.
     */
    @ExceptionHandler(InvalidTopicStringAttributeException.class)
    public ResponseEntity<ExceptionDTO> topicRelatedFaileures(InvalidTopicStringAttributeException exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), InvalidTopicStringAttributeException.ERROR.getStatusCodeValue());
        return ResponseEntity.status(InvalidTopicStringAttributeException.ERROR.getHttpStatusCode()).body(dto);
    }

    /**
     * Manipula a exceção {@link InvalidTopicIntegerException} e retorna uma resposta de erro apropriada.
     *
     * @param exception A exceção que ocorreu.
     * @return Uma {@link ResponseEntity} contendo um {@link ExceptionDTO} com a mensagem da exceção e um código de status HTTP.
     */
    @ExceptionHandler(InvalidTopicIntegerException.class)
    public ResponseEntity<ExceptionDTO> topicRelatedFaileures(InvalidTopicIntegerException exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), InvalidTopicIntegerException.ERROR.getStatusCodeValue());
        return ResponseEntity.status(InvalidTopicIntegerException.ERROR.getHttpStatusCode()).body(dto);
    }

    /**
     * Trata as exceções {@link UuidUtilsException} relacionadas às utilidades do {@link java.util.UUID UUID} e retorna uma resposta de erro adequada.
     *
     * @param exception A exceção que ocorreu.
     * @return Uma {@link ResponseEntity} contendo um {@link ExceptionDTO} com a mensagem da exceção e um código de status HTTP.
     */
    @ExceptionHandler(UuidUtilsException.class)
    public ResponseEntity<ExceptionDTO> uuidUtilsRelatedFaileures(UuidUtilsException exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), UuidUtilsException.ERROR.getStatusCodeValue());
        return ResponseEntity.status(UuidUtilsException.ERROR.getHttpStatusCode()).body(dto);
    }

    /**
     * Manipulador de exceções para falhas relacionadas ao tópico.
     *
     * @param exception uma exceção do tipo {@link TopicIdNotFoundException}, representando a situação onde um ID de tópico não foi encontrado.
     * @return ResponseEntity, que encapsula tanto a resposta HTTP como o corpo do DTO de exceção ({@link ExceptionDTO}).
     * A resposta HTTP contém o status do erro obtido da exceção TopicIdNotFoundException e o corpo contém a mensagem da exceção e o código do status.
     */
    @ExceptionHandler(TopicIdNotFoundException.class)
    public ResponseEntity<ExceptionDTO> topicRelatedFaileures(TopicIdNotFoundException exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), TopicIdNotFoundException.ERROR.getStatusCodeValue());
        return ResponseEntity.status(TopicIdNotFoundException.ERROR.getHttpStatusCode()).body(dto);
    }

    /**
     * Manipulador de exceções para falhas relacionadas ao ID.
     * Esse método é chamado quando uma exceção do tipo {@link InvalidIdException} é lançada.
     *
     * @param exception A exceção lançada que contém informações sobre a falha no ID.
     * @return Uma resposta de entidade contendo o status HTTP correspondente à exceção e o dto contendo a mensagem da exceção.
     */
    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ExceptionDTO> idRelatedFaileures(InvalidIdException exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), InvalidIdException.ERROR.getStatusCodeValue());
        return ResponseEntity.status(InvalidIdException.ERROR.getHttpStatusCode()).body(dto);
    }

    /**
     * Manipula exceções de falha na criação de sessões.
     *
     * @param exception Exceção de falha na criação de sessão que foi lançada.
     * @return Um ResponseEntity que contém um DTO de exceção, com a mensagem de erro e o código de status da exceção.
     */
    @ExceptionHandler(SessionCreateFailureException.class)
    public ResponseEntity<ExceptionDTO> sessionRelatedFaileures(SessionCreateFailureException exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), SessionCreateFailureException.ERROR.getStatusCodeValue());
        return ResponseEntity.status(SessionCreateFailureException.ERROR.getHttpStatusCode()).body(dto);
    }

    /**
     * Manipulador de exceções para situações onde a sessão não é encontrada.
     *
     * @param exception A exceção de Sessão Não Encontrada que foi lançada.
     * @return Uma instância de ResponseEntity contendo os detalhes da exceção capturada,
     * que inclui a mensagem da exceção e o código de status da HTTP Response.
     */
    @ExceptionHandler(SessionNotFound.class)
    public ResponseEntity<ExceptionDTO> sessionRelatedFaileures(SessionNotFound exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), SessionNotFound.ERROR.getStatusCodeValue());
        return ResponseEntity.status(SessionNotFound.ERROR.getHttpStatusCode()).body(dto);
    }

    /**
     * Manipulador de exceções para lidar com argumentos de sessão inválidos.
     *
     * @param exception A exceção lançada por uma sessão ilegal ou argumentos inválidos.
     * @return Uma resposta de entidade contendo a mensagem da exceção original e o código de status HTTP correspondente.
     */
    @ExceptionHandler(IllegalSessionArgumentException.class)
    public ResponseEntity<ExceptionDTO> sessionRelatedFaileures(IllegalSessionArgumentException exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), IllegalSessionArgumentException.ERROR.getStatusCodeValue());
        return ResponseEntity.status(IllegalSessionArgumentException.ERROR.getHttpStatusCode()).body(dto);
    }

    /**
     * Manipulador de exceções para situações em que ocorre uma falha relacionada à validação do CPF do usuário.
     *
     * @param exception a exceção específica que ocorreu durante a validação do CPF do usuário.
     * @return ResponseEntity<ExceptionDTO> - uma resposta do servidor contendo um DTO de exceção,
     * que inclui a mensagem da exceção e o valor do status do código HTTP.
     */
    @ExceptionHandler(ValidationUserCpfException.class)
    public ResponseEntity<ExceptionDTO> fiscalRelatedFaileures(ValidationUserCpfException exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), ValidationUserCpfException.ERROR.getStatusCodeValue());
        return ResponseEntity.status(ValidationUserCpfException.ERROR.getHttpStatusCode()).body(dto);
    }

    /**
     * Lida com exceções de voto duplicado geradas ao tentar votar mais de uma vez em um determinado contexto.
     *
     * @param exception A exceção do tipo VoteDuplicatedException lançada ao registrar um voto duplicado.
     * @return Uma instância da classe ResponseEntity que contém o objeto ExceptionDTO com a mensagem da exceção e o código de status HTTP adequado que representa o erro do voto duplicado.
     */
    @ExceptionHandler(VoteDuplicateException.class)
    public ResponseEntity<ExceptionDTO> voteRelatedFaileures(VoteDuplicateException exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), VoteDuplicateException.ERROR.getStatusCodeValue());
        return ResponseEntity.status(VoteDuplicateException.ERROR.getHttpStatusCode()).body(dto);
    }

    /**
     * Método de manipulação de exceção para erros relacionados a requisições de votos.
     * Essa exceção é lançada quando a validação de uma requisição de voto falha.
     *
     * @param exception A exceção específica de falha na requisição de voto.
     * @return ResponseEntity contendo o DTO da exceção e o status HTTP correspondente ao erro.
     */
    @ExceptionHandler(VoteRequestValidationException.class)
    public ResponseEntity<ExceptionDTO> voteRelatedFaileures(VoteRequestValidationException exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), VoteRequestValidationException.ERROR.getStatusCodeValue());
        return ResponseEntity.status(VoteRequestValidationException.ERROR.getHttpStatusCode()).body(dto);
    }

    /**
     * Método de tratamento de exceções que atua quando nenhuma votação é encontrada para um determinado tópico.
     *
     * @param exception Exceção de quando não há votos para um determinado tópico.
     * @return ResponseEntity contendo as informações da exceção e o status HTTP correspondente
     */
    @ExceptionHandler(NoVotesFoundForTopicException.class)
    public ResponseEntity<ExceptionDTO> voteRelatedFaileures(NoVotesFoundForTopicException exception) {
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), NoVotesFoundForTopicException.ERROR.getStatusCodeValue());
        return ResponseEntity.status(NoVotesFoundForTopicException.ERROR.getHttpStatusCode()).body(dto);
    }

}
