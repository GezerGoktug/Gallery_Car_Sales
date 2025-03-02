package com.goktug.controllers;

import com.goktug.dto.DtoGallerist;
import com.goktug.dto.DtoGalleristIU;

public interface IRestGalleristController {

    RootEntity<DtoGallerist> saveGallerist(DtoGalleristIU gallerist);
}
