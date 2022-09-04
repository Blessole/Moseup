package project.moseup.dto;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Log4j2
@NoArgsConstructor
public class FileSave {

    public File save(MultipartFile file){
        if(file == null){
            throw new NullPointerException("넘어온 파일이 없습니다.");
        }

        String fileRoot;
        String originalFileName;
        String extension;
        String savedFileName;
        File targetFile;
        InputStream fileStream;

        fileRoot = "D:\\spring\\Moseup_image\\";	//저장될 외부 파일 경로

        originalFileName = file.getOriginalFilename();	//오리지날 파일명

        extension = extensionSave(originalFileName);	//파일 확장자

        savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명

        targetFile = new File(fileRoot + savedFileName); // 최종 경로

        try {
            fileStream = file.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장

        } catch (
                IOException e) {
            FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
            e.printStackTrace();
        }
        return targetFile;
    }

    public String extensionSave(String originalFileName){
        String extension;

        if(originalFileName == null || originalFileName.equals("")){
            throw new NullPointerException("오리지널 파일 이름이 없습니다.");
        }
        extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return extension;
    }


}
