package com.learning.repertoire_manager.works.service;

import com.learning.repertoire_manager.exception.AccessDeniedException;
import com.learning.repertoire_manager.security.UserContext;
import com.learning.repertoire_manager.works.dto.SheetPageResponseDto;
import com.learning.repertoire_manager.works.model.Work;
import com.learning.repertoire_manager.works.model.Sheet;
import com.learning.repertoire_manager.works.model.SheetPage;
import com.learning.repertoire_manager.works.model.SheetType;
import com.learning.repertoire_manager.works.repository.WorkRepository;
import com.learning.repertoire_manager.works.repository.SheetPageRepository;
import com.learning.repertoire_manager.works.repository.SheetRepository;
import com.learning.repertoire_manager.works.service.storage.StorageService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SheetService {

    private final WorkRepository workRepository;
    private final SheetRepository sheetRepository;
    private final SheetPageRepository sheetPageRepository;
    private final StorageService storageService;
    private final UserContext userContext;

    public void uploadPdf(UUID workId, MultipartFile file) {

        UUID userId = userContext.getCurrentUserId();

        Work work = workRepository
                .findByIdAndUser_Id(workId, userId)
                .orElseThrow(() -> new AccessDeniedException("Not your work"));

        if (!"application/pdf".equals(file.getContentType())) {
            throw new IllegalArgumentException("Only PDF files are allowed");
        }

        sheetRepository.deleteByWork_Id(workId);

        String path = storageService.store(workId, file);

        Sheet sheet = Sheet.builder()
                .work(work)
                .type(SheetType.PDF)
                .pdfPath(path)
                .pdfFilename(file.getOriginalFilename())
                .pdfContentType(file.getContentType())
                .pages(new ArrayList<>())
                .build();

        sheetRepository.save(sheet);
    }

    public void uploadImages(UUID workId, List<MultipartFile> files) {

        UUID userId = userContext.getCurrentUserId();

        Work work = workRepository
                .findByIdAndUser_Id(workId, userId)
                .orElseThrow(() -> new AccessDeniedException("Not your work"));

        if (files.isEmpty()) {
            throw new IllegalArgumentException("No images uploaded");
        }

        for (MultipartFile file : files) {
            if (!file.getContentType().startsWith("image/")) {
                throw new IllegalArgumentException("All files must be images");
            }
        }

        sheetRepository.deleteByWork_Id(workId);

        Sheet sheet = Sheet.builder()
                .work(work)
                .type(SheetType.IMAGES)
                .pages(new ArrayList<>())
                .build();

        int order = 1;
        for (MultipartFile file : files) {
            String path = storageService.store(workId, file);

            SheetPage page = SheetPage.builder()
                    .sheet(sheet)
                    .pageOrder(order++)
                    .imagePath(path)
                    .originalFilename(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .build();

            sheet.getPages().add(page);
        }

        sheetRepository.save(sheet);
    }

    public Resource downloadPdf(UUID workId) {
        UUID userId = userContext.getCurrentUserId();

        Sheet sheet = sheetRepository
                .findByWork_IdAndWork_User_Id(workId, userId)
                .orElseThrow(() -> new AccessDeniedException("Not your work"));

        if (sheet.getType() != SheetType.PDF) {
            throw new IllegalStateException("Work does not have a PDF sheet");
        }

        return storageService.load(sheet.getPdfPath());
    }

    public Resource downloadImagePage(UUID pageId) {
        UUID userId = userContext.getCurrentUserId();

        SheetPage page = sheetPageRepository
                .findByIdAndSheet_Work_User_Id(pageId, userId)
                .orElseThrow(() -> new AccessDeniedException("Not your page"));

        return storageService.load(page.getImagePath());
    }

    public List<SheetPageResponseDto> listImagePages(UUID workId) {
        UUID userId = userContext.getCurrentUserId();

        Sheet sheet = sheetRepository
                .findByWork_IdAndWork_User_Id(workId, userId)
                .orElseThrow(() -> new AccessDeniedException("Not your work"));

        if (sheet.getType() != SheetType.IMAGES) {
            throw new IllegalStateException("Work does not have image sheets");
        }

        return sheetPageRepository
                .findBySheet_IdOrderByPageOrderAsc(sheet.getId())
                .stream()
                .map(p -> new SheetPageResponseDto(p.getId(), p.getPageOrder()))
                .toList();
    }
}
