package pl.edu.pw.onlinestore.app.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.onlinestore.app.api.dto.AddOpinion;
import pl.edu.pw.onlinestore.app.api.dto.EditOpinion;
import pl.edu.pw.onlinestore.app.api.dto.OpinionTypeDTO;
import pl.edu.pw.onlinestore.app.api.dto.ProfileOpinion;
import pl.edu.pw.onlinestore.app.domain.Opinion;
import pl.edu.pw.onlinestore.app.domain.OpinionType;
import pl.edu.pw.onlinestore.app.domain.User;
import pl.edu.pw.onlinestore.app.repository.OpinionRepository;
import pl.edu.pw.onlinestore.app.repository.UserRepository;
import pl.edu.pw.onlinestore.security.SecurityUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OpinionServiceImpl implements OpinionService {

    private UserRepository userRepository;
    private OpinionRepository opinionRepository;

    public OpinionServiceImpl(UserRepository userRepository, OpinionRepository opinionRepository) {
        this.userRepository = userRepository;
        this.opinionRepository = opinionRepository;
    }

    @Override
    public void addOpinion(AddOpinion addOpinion) {
        User receiver = userRepository.findByUsername(addOpinion.getReceiverUsername()).orElseThrow();
        User sender = userRepository.findByUsername(SecurityUtils.getLoggedUser().getUsername()).orElseThrow();
        opinionRepository.save(map(addOpinion, receiver, sender));
    }

    @Override
    public List<ProfileOpinion> getProfileOpinions(String username) {
        return opinionRepository.findAllByReceiverUsername(username).stream().map(this::map).toList();
    }

    @Override
    public List<ProfileOpinion> getProfileGivenTypeOpinions(String opinionTypeString, String username) {
        List<ProfileOpinion> profileOpinions = null;
        OpinionType opinionType = OpinionType.findByName(opinionTypeString);
        if (opinionType == null || opinionType.equals(OpinionType.ALL)) {
            return getProfileOpinions(username);
        }
        if (opinionType.equals(OpinionType.POSITIVE)) {
            profileOpinions = opinionRepository.findAllByReceiverUsername(username).stream().filter(opinion -> opinion.getRating() >= 3).map(this::map).toList();
        } else if (opinionType.equals(OpinionType.NEGATIVE)) {
            profileOpinions = opinionRepository.findAllByReceiverUsername(username).stream().filter(opinion -> opinion.getRating() < 3).map(this::map).toList();
        }
        return profileOpinions;
    }

    @Override
    public List<OpinionTypeDTO> getOpinionTypes() {
        List<OpinionTypeDTO> opinionTypeDTOList = new ArrayList<>();
        for (OpinionType opinionType : OpinionType.values()) {
            opinionTypeDTOList.add(map(opinionType));
        }
        return opinionTypeDTOList;
    }

    @Override
    public ProfileOpinion getOpinionById(Long id) {
        return opinionRepository.findById(id).map(this::map).orElseThrow();
    }

    @Override
    public String updateOpinion(EditOpinion editOpinion) {
        Opinion opinion = opinionRepository.findById(editOpinion.getId()).orElseThrow();
        opinion.setRating(editOpinion.getRating());
        opinion.setDescription(editOpinion.getDescription());
        return opinion.getReceiver().getUsername();
    }

    @Override
    public String removeOpinion(Long id) {
        String username = opinionRepository.findById(id).orElseThrow().getReceiver().getUsername();
        opinionRepository.deleteById(id);
        return username;
    }

    private OpinionTypeDTO map(OpinionType opinionType) {
        return new OpinionTypeDTO(opinionType.getTitle(), opinionType.name());
    }

    private Opinion map(AddOpinion addOpinion, User receiver, User sender) {
        return new Opinion(
            receiver,
            sender,
            addOpinion.getRating(),
            addOpinion.getDescription(),
            LocalDateTime.now()
        );
    }

    private ProfileOpinion map(Opinion opinion) {
        String dateAdded = opinion.getDateAdded().toString().replaceAll("T", ", ");
        if (dateAdded.length() > 17) {
            dateAdded = dateAdded.substring(0, dateAdded.length() - 3);
        }
        return new ProfileOpinion(
                opinion.getId(),
                opinion.getSender().getUsername(),
                opinion.getRating(),
                dateAdded,
                opinion.getDescription()
        );
    }
}
