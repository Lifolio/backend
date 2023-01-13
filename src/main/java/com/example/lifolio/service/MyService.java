package com.example.lifolio.service;

import com.example.lifolio.dto.category.CategoryRes;
import com.example.lifolio.dto.home.HomeRes;
import com.example.lifolio.dto.my.MyReq;
import com.example.lifolio.dto.my.MyRes;
import com.example.lifolio.dto.user.UserRes;
import com.example.lifolio.entity.MyFolio;
import com.example.lifolio.entity.MyFolioImg;
import com.example.lifolio.entity.MyFolioWith;
import com.example.lifolio.entity.User;
import com.example.lifolio.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyService {
    private final UserRepository userRepository;
    private final MyFolioRepository myFolioRepository;
    private final MyFolioImgRepository myFolioImgRepository;
    private  final MyFolioWithRepository myFolioWithRepository;
    private final ArchiveRepository archiveRepository;
    private final SubCategoryRepository subCategoryRepository;

    private final CategoryRepository categoryRepository;

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

    public List<UserRes.DailyCalender> getDailyCalender(Long userId, String date) throws ParseException {
        //String str = "2019-09-02 08:10:55";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date _date = format.parse(date);



        List<MyFolio> myFolioList = myFolioRepository.findAllByUserIdAndDate(userId,_date);
        List<UserRes.DailyCalender> dailyCalenderList = new ArrayList<>();


        for(MyFolio myFolio : myFolioList){
            UserRes.DailyCalender dailyCalender = new UserRes.DailyCalender(
                    myFolio.getDate(),
                    categoryRepository.findById(myFolio.getCategoryId()).get().getTitle(),
                    myFolio.getTitle(),
                    myFolio.getStar()
            );

            dailyCalenderList.add(dailyCalender);
        }

        return dailyCalenderList;
    }

    public List<HomeRes.GraphLifolio> getGraphLifolio(Long userId) {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        List<MyFolioRepository.GraphLifolio> mainLifolioResult = myFolioRepository.getMainFolio(userId, year);
        List<HomeRes.GraphLifolio> graphLifolio = new ArrayList<>();
        mainLifolioResult.forEach(
                myFolio -> {
                    graphLifolio.add(new HomeRes.GraphLifolio(
                            myFolio.getMonth(),
                            myFolio.getStar()
                    ));
                }
        );
        return graphLifolio;
    }

    public List<MyRes.ViewCategory> getViewCategory(Long userId, MyReq.FilterCategory filterCategory, int page) {

        List<MyRes.ViewCategory> viewCategory=new ArrayList<>();
        int startPage=(page-1)*10;
        int lastPage=page*10;//최신순
        List<MyFolioRepository.ViewCategory> viewCategoryResult =null;
        List<String> category=getCategoryList(userId);

        if(filterCategory.getOrder()==1){
            if(filterCategory.getCategoryList().size()==0){
                viewCategoryResult =myFolioRepository.getViewCategoryDateDesc(userId,  category,startPage,lastPage);
            }
            else {
                viewCategoryResult = myFolioRepository.getViewCategoryDateDesc(userId, filterCategory.getCategoryList(), startPage, lastPage);
            }

        }
        //오래된 순
        else if(filterCategory.getOrder()==2) {
            if(filterCategory.getCategoryList().size()==0){
                viewCategoryResult = myFolioRepository.getViewCategoryDateAsc(userId, category, startPage, lastPage);
            }else{
                viewCategoryResult = myFolioRepository.getViewCategoryDateAsc(userId, filterCategory.getCategoryList(), startPage, lastPage);

            }

        }
        else if(filterCategory.getOrder()==3) {
            if (filterCategory.getCategoryList().size() == 0) {
                viewCategoryResult = myFolioRepository.getViewCategoryStarDesc(userId, category, startPage, lastPage);
            }else{
                viewCategoryResult = myFolioRepository.getViewCategoryStarDesc(userId, filterCategory.getCategoryList(), startPage, lastPage);

            }
        }
        //낮은 중요도순
        else{
                if(filterCategory.getCategoryList().size()==0){
                    viewCategoryResult = myFolioRepository.getViewCategoryStarAsc(userId, category, startPage, lastPage);

                }else {
                    viewCategoryResult = myFolioRepository.getViewCategoryStarAsc(userId, filterCategory.getCategoryList(), startPage, lastPage);
                }

        }

        viewCategoryResult.forEach(
                result -> {
                    viewCategory.add(new MyRes.ViewCategory(
                            result.getFolioId(),
                            result.getUrl(),
                            result.getTitle(),
                            result.getArchiveCheck(),
                            result.getDate(),
                            result.getCategory(),
                            result.getColorName(),
                            result.getStar()
                    ));
                }
        );
        return viewCategory;
    }

    private List<String> getCategoryList(Long userId){
        List<SubCategoryRepository.CategoryList> categoryListResult=subCategoryRepository.getCategoryList(userId);
        List<CategoryRes.CategoryList> categoryList=new ArrayList<>();
        categoryListResult.forEach(
                result -> {
                    categoryList.add(new CategoryRes.CategoryList(
                            result.getCategory()
                    ));
                }
        );
        List<String> category=categoryList.stream().map(e-> e.getCategory()).collect(Collectors.toList());
        return category;
    }


    //마이폴리오 하나씩 보기
    public UserRes.GetMyFolioDetailRes getMyLifolioDetail(Long folioId) {
        MyFolio myFolio = myFolioRepository.getOne(folioId);

        UserRes.GetMyFolioDetailRes getMyFolioDetailRes = new UserRes.GetMyFolioDetailRes(
                myFolio.getTitle(),
                getMyLifolioDetailImg(folioId),
                categoryRepository.findById(myFolio.getCategoryId()).get().getTitle(),
                myFolio.getContent(),
                myFolio.getLatitude(),
                myFolio.getLongitude(),
                getMyFolioWithList(folioId)
        );


        if(getMyFolioDetailRes == null) return null;

        return getMyFolioDetailRes;
    }



    public List<UserRes.MyFolioImgList> getMyLifolioDetailImg(Long folioId){
        List<MyFolioImg> imgs = myFolioImgRepository.findAllByFolioId(folioId);
        List<UserRes.MyFolioImgList> myFolioImgList = new ArrayList<>();

        for(MyFolioImg img : imgs){
            UserRes.MyFolioImgList image = new UserRes.MyFolioImgList(img.getUrl());
            myFolioImgList.add(image);
        }

        return myFolioImgList;
    }


    public List<UserRes.MyFolioWithList> getMyFolioWithList(Long folioId){
        List<MyFolioWith> withs = myFolioWithRepository.findAllByFolioId(folioId);
        List<UserRes.MyFolioWithList> myFolioWithList = new ArrayList<>();


        for(MyFolioWith with : withs){
            UserRes.MyFolioWithList person = new UserRes.MyFolioWithList(
                    userRepository.findById(with.getUserId()).get().getName()
            );
            myFolioWithList.add(person);
        }

        return myFolioWithList;
    }



}
