package pl.edu.pw.onlinestore.app.api.service;

import pl.edu.pw.onlinestore.app.api.dto.AddOpinion;
import pl.edu.pw.onlinestore.app.api.dto.EditOpinion;
import pl.edu.pw.onlinestore.app.api.dto.OpinionTypeDTO;
import pl.edu.pw.onlinestore.app.api.dto.ProfileOpinion;

import java.util.List;

public interface OpinionService {
    void addOpinion(AddOpinion addOpinion);
    List<ProfileOpinion> getProfileOpinions(String username);
    List<ProfileOpinion> getProfileGivenTypeOpinions(String opinionType, String username);
    List<OpinionTypeDTO> getOpinionTypes();
    ProfileOpinion getOpinionById(Long id);
    String updateOpinion(EditOpinion editOpinion);
    String removeOpinion(Long id);
}
