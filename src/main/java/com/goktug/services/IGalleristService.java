package com.goktug.services;

import com.goktug.dto.DtoGallerist;
import com.goktug.dto.DtoGalleristIU;

public interface IGalleristService {

    DtoGallerist saveGallerist(DtoGalleristIU gallerist);
}
