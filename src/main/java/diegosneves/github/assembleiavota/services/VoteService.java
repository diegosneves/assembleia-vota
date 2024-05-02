package diegosneves.github.assembleiavota.services;

import diegosneves.github.assembleiavota.adapter.SendUserAdapter;
import diegosneves.github.assembleiavota.dto.TopicVotedDTO;
import diegosneves.github.assembleiavota.enums.UserStatus;
import diegosneves.github.assembleiavota.enums.VoteOption;
import diegosneves.github.assembleiavota.exceptions.ValidationUserCpfException;
import diegosneves.github.assembleiavota.exceptions.VoteDuplicateException;
import diegosneves.github.assembleiavota.exceptions.VoteRequestValidationException;
import diegosneves.github.assembleiavota.exceptions.NoVotesFoundForTopicException;
import diegosneves.github.assembleiavota.factory.VoteEntityFactory;
import diegosneves.github.assembleiavota.mapper.BuilderMapper;
import diegosneves.github.assembleiavota.models.SessionEntity;
import diegosneves.github.assembleiavota.models.TopicEntity;
import diegosneves.github.assembleiavota.models.VoteEntity;
import diegosneves.github.assembleiavota.repositories.VoteEntityRepository;
import diegosneves.github.assembleiavota.requests.CastVoteRequest;
import diegosneves.github.assembleiavota.responses.CastVoteResponse;
import diegosneves.github.assembleiavota.responses.CountVotesResponse;
import diegosneves.github.assembleiavota.responses.UserResponse;
import diegosneves.github.assembleiavota.services.contract.SessionServiceContract;
import diegosneves.github.assembleiavota.services.contract.TopicServiceContract;
import diegosneves.github.assembleiavota.services.contract.VoteServiceContract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * A classe {@link VoteService} é responsável por realizar ações de votação e contagem de votos.
 *
 * @author diegoneves
 */
@Service
@Slf4j
public class VoteService implements VoteServiceContract {

    public static final String VOTE_CHOICE_FIELD = "chosenVoteOption";
    public static final String VOTE_SESSION_ID_FIELD = "sessionId";
    public static final String VOTE_USER_CPF_FIELD = "userCpf";

    private final VoteEntityRepository repository;
    private final SessionServiceContract sessionService;
    private final TopicServiceContract topicService;
    private final SendUserAdapter userAdapter;

    @Autowired
    public VoteService(VoteEntityRepository repository, SessionServiceContract sessionService, TopicServiceContract topicService, SendUserAdapter userAdapter) {
        this.repository = repository;
        this.sessionService = sessionService;
        this.topicService = topicService;
        this.userAdapter = userAdapter;
    }

    @Override
    public CastVoteResponse performVotingAction(CastVoteRequest request) {
        this.voteRequestValidate(request);
        UserStatus userStatus = validateCpf(request.getUserCpf());
        SessionEntity currentSession = this.sessionService.getSession(request.getSessionId());
        TopicVotedDTO topicVotedDTO = this.getMappedTopic(currentSession.getTopic());
        boolean isSubmit = submitVote(request, currentSession, userStatus);
        return new CastVoteResponse(request.getUserCpf(), userStatus, currentSession.getSessionId(), currentSession.getVotes().size(), topicVotedDTO, request.getChosenVoteOption(), isSubmit);
    }

    @Override
    public CountVotesResponse performVotingSum(String topicId) {
        TopicVotedDTO topicVotedDTO = this.getMappedTopic(topicId);
        List<VoteEntity> votes = this.getVoteEntityList(topicId);
        int yesVotes = sumVotes(votes, VoteOption.YES);
        int noVotes = sumVotes(votes, VoteOption.NO);
        return new CountVotesResponse(topicVotedDTO, votes.size(), yesVotes, noVotes);
    }

    /**
     * Retorna uma lista de entidades de voto associadas a um tópico específico.
     *
     * @param topicId a identificação do tópico para o qual se deseja obter os votos.
     * @return uma lista de {@link VoteEntity} associada ao tópico.
     * @throws NoVotesFoundForTopicException se não existem votos para o tópico especificado.
     */
    private List<VoteEntity> getVoteEntityList(String topicId) throws NoVotesFoundForTopicException {
        List<VoteEntity> votes = this.repository.findByTopicId(topicId);
        if (votes.isEmpty()) {
            log.warn(NoVotesFoundForTopicException.ERROR.getMessage(topicId));
            throw new NoVotesFoundForTopicException(topicId);
        }
        return votes;
    }

    /**
     * Calcula a soma total de votos para uma determinada opção de voto.
     *
     * @param votes      A lista de votos que serão verificados.
     * @param voteOption A opção de voto para a qual a soma será feita.
     * @return A soma total de votos que corresponde à opção de voto fornecida.
     */
    private static int sumVotes(List<VoteEntity> votes, VoteOption voteOption) {
        int sum = 0;
        for (VoteEntity vote : votes) {
            if (voteOption.equals(vote.getVote())) {
                sum++;
            }
        }
        return sum;
    }

    /**
     * Recupera um tópico mapeado com base no ID do tópico fornecido.
     *
     * @param topicId o ID do tópico a ser recuperado
     * @return um objeto {@link TopicVotedDTO} que representa o tópico mapeado
     */
    private TopicVotedDTO getMappedTopic(String topicId) {
        TopicEntity topic = this.topicService.getTopic(topicId);
        return this.getMappedTopic(topic);
    }

    /**
     * Método privado para mapear um objeto {@link TopicEntity} para um objeto {@link TopicVotedDTO}.
     *
     * @param topic Objeto {@link TopicEntity} que precisa ser mapeado para {@link TopicVotedDTO}.
     * @return Objeto {@link TopicVotedDTO} que resulta do mapeamento do objeto {@link TopicEntity} fornecido.
     */
    private TopicVotedDTO getMappedTopic(TopicEntity topic) {
        return BuilderMapper.mapTo(TopicVotedDTO.class, topic);
    }

    /**
     * Este método valida o pedido de voto fornecido como argumento.
     * <p>
     * Realiza verificações para garantir que o CPF do usuário, o ID da sessão e a opção de
     * voto escolhida estão presentes no pedido. Se algum desses campos estiver ausente,
     * uma exceção {@link VoteRequestValidationException} será lançada.
     *
     * @param request O objeto CastVoteRequest que contém os detalhes do voto a ser validado.
     * @throws VoteRequestValidationException se algum dos campos requeridos estiver ausente no pedido de voto.
     */
    private void voteRequestValidate(CastVoteRequest request) throws VoteRequestValidationException {
        if (isNull(request.getUserCpf()) || request.getUserCpf().isEmpty() || request.getUserCpf().isBlank()) {
            throw new VoteRequestValidationException(VOTE_USER_CPF_FIELD);
        }
        if (isNull(request.getSessionId()) || request.getSessionId().isEmpty() || request.getSessionId().isBlank()) {
            throw new VoteRequestValidationException(VOTE_SESSION_ID_FIELD);
        }
        if (isNull(request.getChosenVoteOption())) {
            throw new VoteRequestValidationException(VOTE_CHOICE_FIELD);
        }
    }

    /**
     * Submete um voto durante uma sessão em andamento.
     *
     * @param request        a solicitação contendo as informações do voto a serem submetidas.
     * @param currentSession a sessão em andamento na qual o voto será submetido.
     * @param userStatus     o status do usuário tentando votar.
     * @return verdadeiro se o voto foi submetido com sucesso e a sessão está aberta e o usuário tem permissão para votar;
     * falso caso contrário.
     */
    private boolean submitVote(CastVoteRequest request, SessionEntity currentSession, UserStatus userStatus) {
        if (currentSession.isOpen() && UserStatus.ABLE_TO_VOTE.equals(userStatus)) {
            String sessionTopicId = currentSession.getTopic().getTopicId();
            VoteEntity vote = VoteEntityFactory.create(request.getUserCpf(), currentSession, sessionTopicId, LocalDateTime.now(), request.getChosenVoteOption());
            validateVote(vote);
            currentSession.getVotes().add(vote);
            this.sessionService.updateSession(currentSession);
            return true;
        }
        return false;
    }

    /**
     * Valida um voto ao salvá-lo no repositório. Se uma {@link DataIntegrityViolationException} ocorrer,
     * isso indica que o voto é um duplicado e o método lançará uma VoteDuplicateException.
     *
     * @param vote o voto a ser validado
     * @throws VoteDuplicateException se o voto for um duplicado
     */
    private void validateVote(VoteEntity vote) throws VoteDuplicateException {
        try {
            this.repository.save(vote);
        } catch (DataIntegrityViolationException e) {
            throw new VoteDuplicateException(vote.getVoterCpf(), e);
        }
    }

    /**
     * Valida o CPF do usuário através do envio para um serviço externo.
     *
     * @param cpf Corresponde ao CPF do usuário que será validado.
     * @return UserStatus Devolve o status do usuário após a validação.
     * @throws diegosneves.github.assembleiavota.exceptions.ValidationUserCpfException o CPF informado for inválido ou ocorrer algum problema na requisição.
     */
    private UserStatus validateCpf(String cpf) throws ValidationUserCpfException {
        UserResponse userResponse = this.userAdapter.sendUser(cpf);
        return userResponse.getStatus();
    }

}
