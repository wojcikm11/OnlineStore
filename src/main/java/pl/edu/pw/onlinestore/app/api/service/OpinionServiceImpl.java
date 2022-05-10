package pl.edu.pw.onlinestore.app.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.onlinestore.app.api.dto.AddOpinion;
import pl.edu.pw.onlinestore.app.domain.Opinion;
import pl.edu.pw.onlinestore.app.domain.User;
import pl.edu.pw.onlinestore.app.repository.OpinionRepository;
import pl.edu.pw.onlinestore.app.repository.UserRepository;
import pl.edu.pw.onlinestore.security.SecurityUtils;

import java.time.LocalDateTime;

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

    private Opinion map(AddOpinion addOpinion, User receiver, User sender) {
        return new Opinion(
            receiver,
            sender,
            addOpinion.getRating(),
            addOpinion.getDescription(),
            LocalDateTime.now()
        );
    }
}
