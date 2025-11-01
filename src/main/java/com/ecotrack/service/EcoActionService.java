package com.ecotrack.service;

import com.ecotrack.model.EcoAction;
import com.ecotrack.model.User;
import com.ecotrack.repository.EcoActionRepository;
import com.ecotrack.repository.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EcoActionService {
    private final EcoActionRepository ecoActionRepository;
    private final BadgeRepository badgeRepository;
    private static final String UPLOAD_DIR = "uploads";

    public EcoAction addAction(EcoAction action, MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Files.copy(image.getInputStream(), uploadPath.resolve(fileName));
            action.setImagePath(fileName);
        }
        EcoAction savedAction = ecoActionRepository.save(action);
        updateUserBadge(action.getUser());
        return savedAction;
    }

    public Optional<EcoAction> findById(Long id) {
        return ecoActionRepository.findById(id);
    }

    public List<EcoAction> getUserActions(User user) {
        return ecoActionRepository.findByUser(user);
    }

    public void deleteAction(Long id) {
        ecoActionRepository.deleteById(id);
    }

    public List<Object[]> getLeaderboard() {
        return ecoActionRepository.findTopContributors();
    }

    private void updateUserBadge(User user) {
        int actionCount = ecoActionRepository.findByUser(user).size();
        badgeRepository.findByMinActionsLessThanEqualOrderByMinActionsDesc(actionCount)
                .ifPresent(badge -> {
                    user.setBadgeLevel(badge.getName());
                    // Update user badge
                });
    }
}