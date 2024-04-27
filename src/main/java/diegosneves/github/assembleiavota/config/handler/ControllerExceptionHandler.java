package diegosneves.github.assembleiavota.config.handler;

import diegosneves.github.assembleiavota.dto.ExceptionDTO;
import diegosneves.github.assembleiavota.exceptions.ConstructorDefaultUndefinedException;
import diegosneves.github.assembleiavota.exceptions.InvalidTopicIntegerException;
import diegosneves.github.assembleiavota.exceptions.InvalidTopicStringAttributeException;
import diegosneves.github.assembleiavota.exceptions.MapperFailureException;
import diegosneves.github.assembleiavota.exceptions.UuidUtilsException;
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

}
