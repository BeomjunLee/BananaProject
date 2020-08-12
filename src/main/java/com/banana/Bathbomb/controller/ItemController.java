package com.banana.Bathbomb.controller;

import com.banana.Bathbomb.domain.Category;
import com.banana.Bathbomb.domain.Item;
import com.banana.Bathbomb.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/shop")
    public String shop(Model model, Item item){
        List<Item> result = itemService.findAll();

        model.addAttribute("itemList", result);
        return "/shop/shop";
    }

    @GetMapping("/shop/addItem")
    public String addShop(){
        return "/shop/addItem";
    }

    @PostMapping("/shop/addItemChk")
    public String addShopChk(Item item, Category category, @RequestPart("file") MultipartFile file, Model model, HttpServletRequest request){
        //카테고리 등록
        itemService.insertCategory(category);

        int uid = itemService.findCategoryUid();

        //업로드파일
        String uploadPath = request.getSession().getServletContext().getRealPath("/file/");
        //String uploadPath = "/file/";
        System.out.println("업로드 Path: " + uploadPath);
        String fileName = System.currentTimeMillis() + file.getOriginalFilename(); //동일한 파일명 처리를 위해
        try{
            file.transferTo(new File(uploadPath + fileName));
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("파일 이름명 : "+ fileName);

        item.setCategoryUid(uid);
        item.setItemImage(fileName);

        int resultCode = itemService.insertItem(item);

        model.addAttribute("resultCode", resultCode);
        return "/shop/addItemChk";
    }
}
