package com.example.lifolio.controller;

import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponse;
import com.example.lifolio.dto.category.CategoryDTO;
import com.example.lifolio.dto.category.CategoryReq;
import com.example.lifolio.dto.category.CategoryRes;
import com.example.lifolio.dto.category.SubCategoryReq;
import com.example.lifolio.entity.User;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.service.CategoryService;
import com.example.lifolio.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.example.lifolio.base.BaseResponseStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final TokenProvider tokenProvider;
    private final UserService userService;


    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<CategoryRes.Category>> getCategoryList(@AuthenticationPrincipal User user) {
            Long userId = user.getId();
            List<CategoryRes.Category> categoryList = categoryService.getCategoryList(userId);
            return new BaseResponse<>(categoryList);

    }


    @GetMapping("/view/{categoryId}")
    public BaseResponse<CategoryRes.CategoryUpdateView> getCategoryUpdateView(@AuthenticationPrincipal User user,@PathVariable("categoryId") Long categoryId){
        try {
            CategoryRes.CategoryUpdateView categoryUpdateView = categoryService.getCategoryUpdateView(categoryId);
            return new BaseResponse<>(categoryUpdateView);
        }catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/view/sub/{categoryId}")
    public BaseResponse<CategoryRes.SubCategoryUpdateView> getSubCategoryUpdateView(@AuthenticationPrincipal User user,@PathVariable("categoryId") Long categoryId){
        try {
            CategoryRes.SubCategoryUpdateView subCategoryUpdateView = categoryService.getSubCategoryUpdateView(categoryId);
            return new BaseResponse<>(subCategoryUpdateView);
        }catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    //?????? ???????????? ????????? ??????
    @ResponseBody
    @GetMapping("/{userId}")
    public BaseResponse<List<CategoryRes.CategoryIdTitle>> getCategoryTitleList(@AuthenticationPrincipal User user,@PathVariable("userId") Long userId) {
            if(userService.findNowLoginUser().getId() != userId){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<CategoryRes.CategoryIdTitle> categoryTitleList = categoryService.getCategoryIdTitleList(user.getId());
            return new BaseResponse<>(categoryTitleList);
    }

    //sub ???????????? ????????? ??????
    @ResponseBody
    @GetMapping("/sub")
    public BaseResponse<List<CategoryRes.CategoryIdTitle>> getSubCategoryTitleList(@AuthenticationPrincipal User user) {
        List<CategoryRes.CategoryIdTitle> categoryTitleList = categoryService.getSubCategoryIdTitleList(user.getId());
        return new BaseResponse<>(categoryTitleList);
    }

    @ResponseBody
    @PatchMapping("/{userId}/{id}")
    public BaseResponse<String> updateCategoryList(@AuthenticationPrincipal User user,@PathVariable("userId") Long userId, @PathVariable("id") Long id, @RequestBody CategoryReq.UpdateCategoryReq updateCategoryReq) {
        if(userService.findNowLoginUser().getId() != userId){
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        if(updateCategoryReq.getColorId()==null){
            return new BaseResponse<>(NOT_POST_COLOR);
        }
        if(updateCategoryReq.getTitle()==null){
            return new BaseResponse<>(NOT_POST_TITLE);
        }
        categoryService.setCategoryList(id, updateCategoryReq);
        return new BaseResponse<>("?????? ??????.");
    } //????????? ???????????? ?????? ??????

    @ResponseBody
    @PatchMapping("/list/{userId}/{id}")
    public BaseResponse<String> updateCategorySubCategoryList(@AuthenticationPrincipal User user,@PathVariable("userId") Long userId, @PathVariable("id") Long id, @RequestBody CategoryReq.UpdateCategoryAddSubCategoryReq updateCategoryAddSubCategoryReq) {
        if(userService.findNowLoginUser().getId() != userId){
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        if(updateCategoryAddSubCategoryReq.getColorId()==null){
            return new BaseResponse<>(NOT_POST_COLOR);
        }
        if(updateCategoryAddSubCategoryReq.getTitle()==null){
            return new BaseResponse<>(NOT_POST_TITLE);
        }
        if(updateCategoryAddSubCategoryReq.getSubtitle()==null){
            return new BaseResponse<>(NOT_POST_SUBTITLE);
        }
        categoryService.setCategoryAddSubCategoryList(id, updateCategoryAddSubCategoryReq);
        return new BaseResponse<>("?????? ??????.");
    } //????????? ???????????? ?????? ??????+????????? ???????????? ??????


    @ResponseBody
    @PatchMapping("/sub/{userId}/{id}")
    public BaseResponse<String> updateSubCategoryList(@AuthenticationPrincipal User user,@PathVariable("userId") Long userId, @PathVariable("id") Long id, @RequestBody SubCategoryReq.UpdateSubCategoryReq updateSubCategoryReq) {
        if(userService.findNowLoginUser().getId() != userId){
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        if(updateSubCategoryReq.getCategoryId()==null){
            return new BaseResponse<>(NOT_POST_CATEGORY);
        }
        if(updateSubCategoryReq.getTitle()==null){
            return new BaseResponse<>(NOT_POST_TITLE);
        }
        categoryService.setSubCategoryList(id, updateSubCategoryReq);
        return new BaseResponse<>("?????? ??????.");
    } //????????? ???????????? ??????

    @ResponseBody
    @PatchMapping("/sub/move/{userId}/{id}")
    public BaseResponse<String> updateSubCategoryToCategoryList(@AuthenticationPrincipal User user,@PathVariable("userId") Long userId, @PathVariable("id") Long id, @RequestBody SubCategoryReq.MoveSubCategoryReq moveSubCategoryReq) {
        if(userService.findNowLoginUser().getId() != userId){
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        if(moveSubCategoryReq.getColorId()==null){
            return new BaseResponse<>(NOT_POST_COLOR);
        }
        if(moveSubCategoryReq.getTitle()==null){
            return new BaseResponse<>(NOT_POST_TITLE);
        }
        categoryService.setSubCategoryToCategoryList(id, moveSubCategoryReq);
        return new BaseResponse<>("?????? ??????.");
    } //????????? ????????????->????????? ??????????????? ??????

    @ResponseBody
    @DeleteMapping("/{id}")
    public BaseResponse<String> deleteCategoryList(@AuthenticationPrincipal User user,@PathVariable("id") Long id) {
        try {
            Long userId = user.getId();
            categoryService.deleteCategoryList(id);
            return new BaseResponse<>("?????? ??????.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    } //????????? ????????????(????????? ????????? ??????????????? ??????) ??????

    @ResponseBody
    @DeleteMapping("/sub/{id}")
    public BaseResponse<String> deleteSubCategoryList(@AuthenticationPrincipal User user,@PathVariable("id") Long id) {
        try {
            Long userId = user.getId();
            categoryService.deleteSubCategoryList(id);
            return new BaseResponse<>("?????? ??????.");
        } catch (BaseException e) {
        return new BaseResponse<>(e.getStatus());
    }
    } //????????? ???????????? ??????


    @ResponseBody
    @PostMapping("/{userId}")
    public BaseResponse<String> addCategoryList(@AuthenticationPrincipal User user, @PathVariable("userId") Long userId, @RequestBody CategoryReq.AddCategoryReq addCategoryReq) {

        if(userService.findNowLoginUser().getId() != userId){
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        if(addCategoryReq.getColorId()==null){
            return new BaseResponse<>(NOT_POST_COLOR);
        }
        if(addCategoryReq.getTitle()==null){
            return new BaseResponse<>(NOT_POST_TITLE);
        }
        categoryService.addCategoryList(addCategoryReq);
        return new BaseResponse<>("?????? ??????.");
    } //????????? ???????????? ??????


    @ResponseBody
    @PostMapping("/sub/{userId}")
    public BaseResponse<String> addSubCategoryList(@AuthenticationPrincipal User user, @PathVariable("userId") Long userId, @RequestBody SubCategoryReq.AddSubCategoryReq addSubCategoryReq) {
        if(userService.findNowLoginUser().getId() != userId){
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        if(addSubCategoryReq.getCategoryId()==null){
            return new BaseResponse<>(NOT_POST_CATEGORY);
        }
        if(addSubCategoryReq.getTitle()==null){
            return new BaseResponse<>(NOT_POST_TITLE);
        }
        categoryService.addSubCategoryList(addSubCategoryReq);
        return new BaseResponse<>("?????? ??????.");
    } //????????? ???????????? ??????

    @ResponseBody
    @PostMapping("/list/{userId}")
    public BaseResponse<String> addCategorySubCategoryList(@AuthenticationPrincipal User user, @PathVariable("userId") Long userId, @RequestBody CategoryReq.AddCategorySubCategoryReq addCategorySubCategoryReq) {
        if(userService.findNowLoginUser().getId() != userId){
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        if(addCategorySubCategoryReq.getColorId()==null){
            return new BaseResponse<>(NOT_POST_COLOR);
        }
        if(addCategorySubCategoryReq.getTitle()==null){
            return new BaseResponse<>(NOT_POST_TITLE);
        }
        if(addCategorySubCategoryReq.getSubtitle()==null){
            return new BaseResponse<>(NOT_POST_SUBTITLE);
        }
        categoryService.addCategorySubCategoryList(addCategorySubCategoryReq);
        return new BaseResponse<>("?????? ??????.");
    } //?????????+????????? ???????????? ??????


}


