package springstudy.bookstore.apiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiaryService {
    // 의존성 주입
    private final DiaryRepository diaryRepository;

    @Autowired
    private S3Uploader s3Uploader;


    @Transactional
    public Long keepDiary(MultipartFile image, Diary diary) throws IOException {
        System.out.println("Diary service saveDiary");
        if(!image.isEmpty()) {
            String storedFileName = s3Uploader.upload(image,"images");
            diary.setImageUrl(storedFileName);
            log.info("imageURL={}", diary.getImageUrl());
        }
        Diary savedDiary = diaryRepository.save(diary);
        return savedDiary.getDiary_id();
    }
}