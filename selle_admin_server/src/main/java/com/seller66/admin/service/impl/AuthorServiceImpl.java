package com.seller66.admin.service.impl;

import com.seller66.admin.mapper.AuthorMapper;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.seller66.admin.service.AuthorService;
@Service
public class AuthorServiceImpl implements AuthorService{

    @Autowired
    private AuthorMapper authorMapper;

}
