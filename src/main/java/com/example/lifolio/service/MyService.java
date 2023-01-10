package com.example.lifolio.service;

import com.example.lifolio.dto.user.UserRes;
import com.example.lifolio.entity.User;
import com.example.lifolio.repository.ArchiveRepository;
import com.example.lifolio.repository.MyFolioRepository;
import com.example.lifolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyService {
    private final UserRepository userRepository;
    private final MyFolioRepository myFolioRepository;
    private final ArchiveRepository archiveRepository;

    public UserRes.GetMyRes getMyLifolio(Long userId) {
        User user=userRepository.getOne(userId);
        int lifolioCnt=myFolioRepository.countByUserId(user.getId());
        // 베스트 카테고리 TOP 5
        List<MyFolioRepository.BestCategory> bestCategoryResult=myFolioRepository.getBestCategories(user.getId());

        List<UserRes.BestCategory> bestCategory=new ArrayList<>();
        bestCategoryResult.forEach(
                result->{
                    bestCategory.add(new UserRes.BestCategory(
                            result.getColor(),
                            result.getCategory(),
                            result.getUrl(),
                            result.getTitle(),
                            result.getStar()
                    ));
                }
        );

        List<ArchiveRepository.ArchiveList> archiveList=archiveRepository.getArchiveList(user.getId());
        List<UserRes.Archive> archive=new ArrayList<>();
        archiveList.forEach(
                result->{
                    archive.add(
                            new UserRes.Archive(
                                    result.getFolioId(),
                                    result.getColor(),
                                    result.getCategory()
                            )
                    );
                }
        );


        return new UserRes.GetMyRes(user.getNickname(),lifolioCnt,bestCategory,archive);
    }

    public List<UserRes.Calender> getCalender(Long userId, String date) {
        List<MyFolioRepository.Calender> calenderList=myFolioRepository.getCalenderList(userId,date);
        List<UserRes.Calender> calender=new ArrayList<>();

        calenderList.forEach(
                result->{
                    calender.add(
                            new UserRes.Calender(
                                    result.getDate(),
                                    result.getColor(),
                                    result.getFolioId(),
                                    result.getUrl(),
                                    result.getTitle()
                            )
                    );
                }
        );
        return calender;
    }


    public UserRes.Profile getMyProfileSimple(Long userId) {
        User user = userRepository.getOne(userId);

        UserRes.Profile profile = new UserRes.Profile(user.getName());

        return profile;
    }
}
