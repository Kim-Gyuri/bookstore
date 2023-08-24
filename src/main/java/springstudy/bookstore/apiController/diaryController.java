package springstudy.bookstore.apiController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
@Controller
@RequiredArgsConstructor
public class diaryController {

    private final DiaryService diaryService;

    @ResponseBody   // Long 타입을 리턴하고 싶은 경우 붙여야 함 (Long - 객체)
    @PostMapping(value="/diary/new",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Long saveDiary(HttpServletRequest request, @RequestParam(value="image") MultipartFile image, Diary diary) throws IOException {
        System.out.println("DiaryController.saveDiary");
        System.out.println(image);
        System.out.println(diary);
        System.out.println("------------------------------------------------------");
        Long diaryId = diaryService.keepDiary(image, diary);
        return diaryId;
    }
}
