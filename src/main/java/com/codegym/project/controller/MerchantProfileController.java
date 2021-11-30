package com.codegym.project.controller;

import com.codegym.project.users.merchantProfile.MerchantProfile;
import com.codegym.project.users.merchantProfile.service.IMerchantProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/merchant")
@CrossOrigin("*")
public class MerchantProfileController {
    @Autowired
    private IMerchantProfileService merchantProfileService;

    @GetMapping("/list")
    public ResponseEntity<Page<MerchantProfile>> getAllMerchant(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort
    ){
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("id").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("id").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        Page<MerchantProfile> merchantProfilePage = merchantProfileService.findAll(pageable);
        return new ResponseEntity<>(merchantProfilePage, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<MerchantProfile>> getMerchantById(@PathVariable Long id){
        Optional<MerchantProfile> merchantProfileOptional = merchantProfileService.findById(id);
        return new ResponseEntity<>(merchantProfileOptional, HttpStatus.OK);
    }

}
